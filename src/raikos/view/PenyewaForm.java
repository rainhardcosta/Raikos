package raikos.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PenyewaForm extends JFrame {
    private final JTextField txtId = new JTextField(14);
    private final JTextField txtNama = new JTextField(14);
    private final JTextField txtTelp = new JTextField(14);
    private final JComboBox<String> cbJk = new JComboBox<>(new String[]{"Laki-laki", "Perempuan"});
    private final JTextField txtAsal = new JTextField(14);
    private final JComboBox<String> cbPekerjaan = new JComboBox<>(new String[]{"Mahasiswa", "Karyawan", "Lainnya"});

    private final JButton btnSimpan = new JButton("Simpan");
    private final JButton btnUbah = new JButton("Ubah");
    private final JButton btnHapus = new JButton("Hapus");
    private final JButton btnReset = new JButton("Reset");

    private final JTextField txtCari = new JTextField(16);
    private final JButton btnCari = new JButton("Cari");
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"ID", "Nama", "No. Telp", "Gender", "Asal", "Pekerjaan"}, 0) {
        @Override
        public boolean isCellEditable(int row, int col) { return false; }
    };
    private final JTable tblPenyewa = new JTable(tableModel);

    public PenyewaForm() {
        setTitle("Raikos - Data Penyewa");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setSize(1000, 470);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Panel kiri: form input data penyewa
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Form Data Penyewa"));
        txtId.setEditable(false);
        String[] label = {"ID:", "Nama Lengkap:", "No. Telp:", "Jenis Kelamin:", "Asal Daerah:", "Pekerjaan:"};
        JComponent[] input = {txtId, txtNama, txtTelp, cbJk, txtAsal, cbPekerjaan};
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

        // Panel kanan: pencarian dan tabel daftar penyewa
        JPanel cari = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cari.add(new JLabel("Cari Nama/Telp:"));
        cari.add(txtCari);
        cari.add(btnCari);

        tblPenyewa.setRowHeight(22);
        tblPenyewa.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblPenyewa.getColumnModel().getColumn(1).setPreferredWidth(140);

        JPanel kanan = new JPanel(new BorderLayout());
        kanan.setBorder(BorderFactory.createTitledBorder("Daftar Penyewa"));
        kanan.add(cari, BorderLayout.NORTH);
        kanan.add(new JScrollPane(tblPenyewa), BorderLayout.CENTER);

        JPanel root = new JPanel(new BorderLayout(10, 0));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.add(kiri, BorderLayout.WEST);
        root.add(kanan, BorderLayout.CENTER);
        setContentPane(root);
    }

    public JTextField getIdInput() { return txtId; }
    public JTextField getNamaInput() { return txtNama; }
    public JTextField getTelpInput() { return txtTelp; }
    public JComboBox<String> getJkInput() { return cbJk; }
    public JTextField getAsalInput() { return txtAsal; }
    public JComboBox<String> getPekerjaanInput() { return cbPekerjaan; }
    public JButton getBtnSimpan() { return btnSimpan; }
    public JButton getBtnUbah() { return btnUbah; }
    public JButton getBtnHapus() { return btnHapus; }
    public JButton getBtnReset() { return btnReset; }
    public JTextField getCariInput() { return txtCari; }
    public JButton getBtnCari() { return btnCari; }
    public JTable getTblPenyewa() { return tblPenyewa; }
    public DefaultTableModel getTableModel() { return tableModel; }
}
