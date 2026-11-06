package raikos.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raikos.model.Kamar;
import raikos.model.Penyewa;
import raikos.model.Sewa;
import raikos.view.SewaForm;

public class SewaController {
    private final SewaForm view;
    private final Sewa model;

    public SewaController(SewaForm view, Sewa model) {
        this.view = view;
        this.model = model;

        // Total dan jatuh tempo dihitung ulang setiap input berubah
        view.getKamarInput().addActionListener(e -> hitungTotal());
        view.getDurasiInput().addChangeListener(e -> hitungTotal());
        view.getTglMasukInput().addActionListener(e -> hitungTotal());

        view.getBtnProses().addActionListener(e -> prosesSewa());
        view.getBtnSelesai().addActionListener(e -> selesaikanSewa());
        view.getBtnReset().addActionListener(e -> resetForm());

        loadComboData();
        loadTableData();
        resetForm();
    }

    private void loadComboData() {
        List<Penyewa> penyewa = new Penyewa().getAllPenyewa("");
        view.getPenyewaInput().setModel(new DefaultComboBoxModel<>(penyewa.toArray(new Penyewa[0])));
        List<Kamar> kamar = new Kamar().getKamarTersedia();
        view.getKamarInput().setModel(new DefaultComboBoxModel<>(kamar.toArray(new Kamar[0])));
    }

    public void loadTableData() {
        DefaultTableModel tbl = view.getTableModel();
        tbl.setRowCount(0);
        for (Sewa s : model.getAllSewa()) {
            tbl.addRow(new Object[]{
                s.getId(), s.getNamaPenyewa(), s.getKodeKamar(), s.getTanggalMasuk(),
                s.getDurasi() + " bulan", s.getJatuhTempo(),
                Kamar.formatRupiah(s.getTotalBayar()), s.getStatus()
            });
        }
    }

    private void hitungTotal() {
        Kamar kamar = (Kamar) view.getKamarInput().getSelectedItem();
        int durasi = (Integer) view.getDurasiInput().getValue();
        if (kamar == null) {
            view.getTotalOutput().setText("");
            return;
        }
        view.getTotalOutput().setText(Kamar.formatRupiah((long) kamar.getHarga() * durasi));
        try {
            LocalDate tgl = LocalDate.parse(view.getTglMasukInput().getText().trim());
            view.getJatuhTempoOutput().setText(tgl.plusMonths(durasi).toString());
        } catch (DateTimeParseException ex) {
            view.getJatuhTempoOutput().setText("-");
        }
    }

    private void prosesSewa() {
        Penyewa penyewa = (Penyewa) view.getPenyewaInput().getSelectedItem();
        Kamar kamar = (Kamar) view.getKamarInput().getSelectedItem();
        if (penyewa == null || kamar == null) {
            JOptionPane.showMessageDialog(view, "Pilih penyewa dan kamar yang tersedia!",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate tglMasuk;
        try {
            tglMasuk = LocalDate.parse(view.getTglMasukInput().getText().trim());
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(view, "Format tanggal harus YYYY-MM-DD (contoh: 2026-11-06)",
                    "Format Tidak Valid", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int durasi = (Integer) view.getDurasiInput().getValue();
        int total = kamar.getHarga() * durasi;
        if (model.insertSewa(penyewa.getId(), kamar.getId(), tglMasuk, durasi, total)) {
            JOptionPane.showMessageDialog(view, "Sewa kamar " + kamar.getKodeKamar() + " atas nama "
                    + penyewa.getNama() + " berhasil!\nTotal bayar: " + Kamar.formatRupiah(total),
                    "Transaksi Berhasil", JOptionPane.INFORMATION_MESSAGE);
            loadComboData();
            loadTableData();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(view, "Transaksi gagal disimpan.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selesaikanSewa() {
        int row = view.getTblSewa().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Pilih transaksi pada tabel terlebih dahulu!",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (view.getTableModel().getValueAt(row, 7).equals("Selesai")) {
            JOptionPane.showMessageDialog(view, "Transaksi ini sudah selesai.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int idSewa = (Integer) view.getTableModel().getValueAt(row, 0);
        if (model.selesaikanSewa(idSewa)) {
            JOptionPane.showMessageDialog(view, "Sewa telah diselesaikan, kamar kembali tersedia.",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadComboData();
            loadTableData();
            resetForm();
        }
    }

    private void resetForm() {
        if (view.getPenyewaInput().getItemCount() > 0) view.getPenyewaInput().setSelectedIndex(0);
        if (view.getKamarInput().getItemCount() > 0) view.getKamarInput().setSelectedIndex(0);
        view.getTglMasukInput().setText(LocalDate.now().toString());
        view.getDurasiInput().setValue(1);
        view.getTblSewa().clearSelection();
        hitungTotal();
    }
}
