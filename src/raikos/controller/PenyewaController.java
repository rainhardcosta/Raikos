package raikos.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import raikos.model.Penyewa;
import raikos.view.PenyewaForm;

public class PenyewaController {
    private final PenyewaForm view;
    private final Penyewa model;

    public PenyewaController(PenyewaForm view, Penyewa model) {
        this.view = view;
        this.model = model;

        view.getBtnSimpan().addActionListener(e -> simpanPenyewa());
        view.getBtnUbah().addActionListener(e -> ubahPenyewa());
        view.getBtnHapus().addActionListener(e -> hapusPenyewa());
        view.getBtnReset().addActionListener(e -> resetForm());
        view.getBtnCari().addActionListener(e -> loadTableData());

        view.getTblPenyewa().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTblPenyewa().getSelectedRow();
                if (row < 0) return;
                DefaultTableModel tbl = view.getTableModel();
                view.getIdInput().setText(tbl.getValueAt(row, 0).toString());
                view.getNamaInput().setText(tbl.getValueAt(row, 1).toString());
                view.getTelpInput().setText(tbl.getValueAt(row, 2).toString());
                view.getJkInput().setSelectedItem(tbl.getValueAt(row, 3).toString());
                view.getAsalInput().setText(tbl.getValueAt(row, 4).toString());
                view.getPekerjaanInput().setSelectedItem(tbl.getValueAt(row, 5).toString());
                setTombolMode(true);
            }
        });

        loadTableData();
        setTombolMode(false);
    }

    public void loadTableData() {
        DefaultTableModel tbl = view.getTableModel();
        tbl.setRowCount(0);
        List<Penyewa> list = model.getAllPenyewa(view.getCariInput().getText().trim());
        for (Penyewa p : list) {
            tbl.addRow(new Object[]{
                p.getId(), p.getNama(), p.getNoTelp(),
                p.getJenisKelamin(), p.getAsal(), p.getPekerjaan()
            });
        }
    }

    private boolean validasiInput() {
        String nama = view.getNamaInput().getText().trim();
        String telp = view.getTelpInput().getText().trim();

        if (nama.isEmpty() || telp.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama dan No. Telp wajib diisi!",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!telp.matches("08\\d{8,11}")) {
            JOptionPane.showMessageDialog(view, "No. Telp harus diawali 08 dan berjumlah 10-13 digit!",
                    "Format Tidak Valid", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void simpanPenyewa() {
        if (!validasiInput()) return;

        if (model.insertPenyewa(view.getNamaInput().getText().trim(),
                view.getTelpInput().getText().trim(),
                view.getJkInput().getSelectedItem().toString(),
                view.getAsalInput().getText().trim(),
                view.getPekerjaanInput().getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(view, "Data penyewa berhasil disimpan!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
            resetForm();
        }
    }

    private void ubahPenyewa() {
        if (!validasiInput()) return;

        int id = Integer.parseInt(view.getIdInput().getText());
        if (model.updatePenyewa(id, view.getNamaInput().getText().trim(),
                view.getTelpInput().getText().trim(),
                view.getJkInput().getSelectedItem().toString(),
                view.getAsalInput().getText().trim(),
                view.getPekerjaanInput().getSelectedItem().toString())) {
            JOptionPane.showMessageDialog(view, "Data penyewa berhasil diubah!",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            loadTableData();
            resetForm();
        }
    }

    private void hapusPenyewa() {
        int id = Integer.parseInt(view.getIdInput().getText());
        int pilih = JOptionPane.showConfirmDialog(view, "Yakin hapus penyewa "
                + view.getNamaInput().getText() + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilih != JOptionPane.YES_OPTION) return;

        if (model.deletePenyewa(id)) {
            loadTableData();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(view, "Penyewa tidak bisa dihapus karena memiliki riwayat sewa.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        view.getIdInput().setText("");
        view.getNamaInput().setText("");
        view.getTelpInput().setText("");
        view.getJkInput().setSelectedIndex(0);
        view.getAsalInput().setText("");
        view.getPekerjaanInput().setSelectedIndex(0);
        view.getTblPenyewa().clearSelection();
        setTombolMode(false);
    }

    private void setTombolMode(boolean modeEdit) {
        view.getBtnSimpan().setEnabled(!modeEdit);
        view.getBtnUbah().setEnabled(modeEdit);
        view.getBtnHapus().setEnabled(modeEdit);
    }
}
