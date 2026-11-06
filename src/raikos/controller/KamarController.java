package raikos.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raikos.model.Kamar;
import raikos.view.KamarForm;

public class KamarController {
    private final KamarForm view;
    private final Kamar model;

    public KamarController(KamarForm view, Kamar model) {
        this.view = view;
        this.model = model;

        view.getBtnSimpan().addActionListener(e -> simpanKamar());
        view.getBtnUbah().addActionListener(e -> ubahKamar());
        view.getBtnHapus().addActionListener(e -> hapusKamar());
        view.getBtnReset().addActionListener(e -> resetForm());
        view.getBtnCari().addActionListener(e -> loadTableData());

        // Klik baris tabel -> data dipindah ke form input
        view.getTblKamar().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTblKamar().getSelectedRow();
                if (row < 0) return;
                DefaultTableModel tbl = view.getTableModel();
                view.getIdInput().setText(tbl.getValueAt(row, 0).toString());
                view.getKodeInput().setText(tbl.getValueAt(row, 1).toString());
                view.getTipeInput().setSelectedItem(tbl.getValueAt(row, 2).toString());
                view.getHargaInput().setText(tbl.getValueAt(row, 3).toString()
                        .replace("Rp ", "").replace(".", ""));
                view.getFasilitasInput().setText(tbl.getValueAt(row, 4).toString());
                view.getStatusInput().setSelectedItem(tbl.getValueAt(row, 5).toString());
                setTombolMode(true);
            }
        });

        loadTableData();
        setTombolMode(false);
    }

    public void loadTableData() {
        DefaultTableModel tbl = view.getTableModel();
        tbl.setRowCount(0);
        List<Kamar> list = model.getAllKamar(view.getCariInput().getText().trim());
        for (Kamar k : list) {
            tbl.addRow(new Object[]{
                k.getId(), k.getKodeKamar(), k.getTipe(),
                Kamar.formatRupiah(k.getHarga()), k.getFasilitas(), k.getStatus()
            });
        }
    }

    // Validasi input, mengembalikan harga bila valid atau -1 bila tidak valid
    private int validasiInput() {
        String kode = view.getKodeInput().getText().trim();
        String harga = view.getHargaInput().getText().trim();

        if (kode.isEmpty() || harga.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Kode Kamar dan Harga wajib diisi!",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        if (!kode.matches("[A-Z]-\\d{2}")) {
            JOptionPane.showMessageDialog(view, "Format kode kamar harus seperti A-01!",
                    "Format Tidak Valid", JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        if (!harga.matches("\\d{5,8}")) {
            JOptionPane.showMessageDialog(view, "Harga harus berupa angka (tanpa titik/Rp)!",
                    "Format Tidak Valid", JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        return Integer.parseInt(harga);
    }

    private void simpanKamar() {
        int harga = validasiInput();
        if (harga < 0) return;

        if (model.insertKamar(view.getKodeInput().getText().trim(),
                view.getTipeInput().getSelectedItem().toString(), harga,
                view.getFasilitasInput().getText().trim(),
                view.getStatusInput().getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(view, "Data kamar berhasil disimpan!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(view, "Gagal menyimpan, kode kamar mungkin sudah ada.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ubahKamar() {
        int harga = validasiInput();
        if (harga < 0) return;

        int id = Integer.parseInt(view.getIdInput().getText());
        if (model.updateKamar(id, view.getKodeInput().getText().trim(),
                view.getTipeInput().getSelectedItem().toString(), harga,
                view.getFasilitasInput().getText().trim(),
                view.getStatusInput().getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(view, "Data kamar berhasil diubah!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
            resetForm();
        }
    }

    private void hapusKamar() {
        int id = Integer.parseInt(view.getIdInput().getText());
        int pilih = JOptionPane.showConfirmDialog(view, "Yakin hapus kamar "
                + view.getKodeInput().getText() + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilih != JOptionPane.YES_OPTION) return;

        if (model.deleteKamar(id)) {
            loadTableData();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(view, "Kamar tidak bisa dihapus karena memiliki riwayat sewa.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        view.getIdInput().setText("");
        view.getKodeInput().setText("");
        view.getTipeInput().setSelectedIndex(0);
        view.getHargaInput().setText("");
        view.getFasilitasInput().setText("");
        view.getStatusInput().setSelectedIndex(0);
        view.getTblKamar().clearSelection();
        setTombolMode(false);
    }

    private void setTombolMode(boolean modeEdit) {
        view.getBtnSimpan().setEnabled(!modeEdit);
        view.getBtnUbah().setEnabled(modeEdit);
        view.getBtnHapus().setEnabled(modeEdit);
    }
}
