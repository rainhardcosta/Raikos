package raikos.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class KamarForm extends JFrame {
    private final JTextField txtId = new JTextField(14);
    private final JTextField txtKode = new JTextField(14);
    private final JComboBox<String> cbTipe = new JComboBox<>(new String[]{"Standar", "AC", "VIP"});
    private final JTextField txtHarga = new JTextField(14);
    private final JTextField txtFasilitas = new JTextField(14);
    private final JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Tersedia", "Terisi", "Perbaikan"});

    private final JButton btnSimpan = new JButton("Simpan");
    private final JButton btnUbah = new JButton("Ubah");
    private final JButton btnHapus = new JButton("Hapus");
    private final JButton btnReset = new JButton("Reset");

    private final JTextField txtCari = new JTextField(16);
    private final JButton btnCari = new JButton("Cari");
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"ID", "Kode", "Tipe", "Harga/Bulan", "Fasilitas", "Status"}, 0) {
        @Override
        public boolean isCellEditable(int row, int col) { return false; }
    };
    private final JTable tblKamar = new JTable(tableModel);

    public KamarForm() {
        setTitle("Raikos - Data Kamar Kos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setSize(1040, 470);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Panel kiri: form input data kamar
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Form Data Kamar"));
        txtId.setEditable(false);
        String[] label = {"ID:", "Kode Kamar:", "Tipe Kamar:", "Harga/Bulan (Rp):", "Fasilitas:", "Status:"};
        JComponent[] input = {txtId, txtKode, cbTipe, txtHarga, txtFasilitas, cbStatus};
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        for (int i = 0; i < label.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            form.add(new JLabel(label[i]), gbc);
            gbc.gridx = 1;
            form.add(input[i], gbc);
        }

        JPanel tombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
        tombol.add(btnSimpan);
        tombol.add(btnUbah);
        tombol.add(btnHapus);
        tombol.add(btnReset);
        gbc.gridx = 0; gbc.gridy = label.length; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 4, 4, 4);
        form.add(tombol, gbc);

        JPanel kiri = new JPanel(new BorderLayout());
        kiri.add(form, BorderLayout.NORTH);

        // Panel kanan: pencarian dan tabel daftar kamar
        JPanel cari = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cari.add(new JLabel("Cari Kode/Tipe:"));
        cari.add(txtCari);
        cari.add(btnCari);

        tblKamar.setRowHeight(22);
        tblKamar.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblKamar.getColumnModel().getColumn(3).setPreferredWidth(95);
        tblKamar.getColumnModel().getColumn(4).setPreferredWidth(210);

        JPanel kanan = new JPanel(new BorderLayout());
        kanan.setBorder(BorderFactory.createTitledBorder("Daftar Kamar"));
        kanan.add(cari, BorderLayout.NORTH);
        kanan.add(new JScrollPane(tblKamar), BorderLayout.CENTER);

        JPanel root = new JPanel(new BorderLayout(10, 0));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.add(kiri, BorderLayout.WEST);
        root.add(kanan, BorderLayout.CENTER);
        setContentPane(root);
    }

    public JTextField getIdInput() { return txtId; }
    public JTextField getKodeInput() { return txtKode; }
    public JComboBox<String> getTipeInput() { return cbTipe; }
    public JTextField getHargaInput() { return txtHarga; }
    public JTextField getFasilitasInput() { return txtFasilitas; }
    public JComboBox<String> getStatusInput() { return cbStatus; }
    public JButton getBtnSimpan() { return btnSimpan; }
    public JButton getBtnUbah() { return btnUbah; }
    public JButton getBtnHapus() { return btnHapus; }
    public JButton getBtnReset() { return btnReset; }
    public JTextField getCariInput() { return txtCari; }
    public JButton getBtnCari() { return btnCari; }
    public JTable getTblKamar() { return tblKamar; }
    public DefaultTableModel getTableModel() { return tableModel; }
}
