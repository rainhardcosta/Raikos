package raikos.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import raikos.model.Kamar;
import raikos.model.Penyewa;

public class SewaForm extends JFrame {
    private final JComboBox<Penyewa> cbPenyewa = new JComboBox<>();
    private final JComboBox<Kamar> cbKamar = new JComboBox<>();
    private final JTextField txtTglMasuk = new JTextField(16);
    private final JSpinner spDurasi = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
    private final JTextField txtJatuhTempo = new JTextField(16);
    private final JTextField txtTotal = new JTextField(16);

    private final JButton btnProses = new JButton("Proses Sewa");
    private final JButton btnSelesai = new JButton("Selesaikan Sewa");
    private final JButton btnReset = new JButton("Reset");

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"ID", "Penyewa", "Kamar", "Tgl Masuk", "Durasi", "Jatuh Tempo", "Total Bayar", "Status"}, 0) {
        @Override
        public boolean isCellEditable(int row, int col) { return false; }
    };
    private final JTable tblSewa = new JTable(tableModel);

    public SewaForm() {
        setTitle("Raikos - Transaksi Sewa Kamar");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setSize(1200, 470);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Panel kiri: form transaksi sewa
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Form Transaksi Sewa"));
        txtJatuhTempo.setEditable(false);
        txtTotal.setEditable(false);
        txtTotal.setFont(txtTotal.getFont().deriveFont(Font.BOLD));
        String[] label = {"Penyewa:", "Kamar Tersedia:", "Tgl Masuk (YYYY-MM-DD):",
                          "Durasi (bulan):", "Jatuh Tempo:", "Total Bayar:"};
        JComponent[] input = {cbPenyewa, cbKamar, txtTglMasuk, spDurasi, txtJatuhTempo, txtTotal};
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
        tombol.add(btnProses);
        tombol.add(btnSelesai);
        tombol.add(btnReset);
        gbc.gridx = 0; gbc.gridy = label.length; gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 4, 4, 4);
        form.add(tombol, gbc);

        JPanel kiri = new JPanel(new BorderLayout());
        kiri.add(form, BorderLayout.NORTH);

        // Panel kanan: riwayat transaksi sewa
        tblSewa.setRowHeight(22);
        tblSewa.getColumnModel().getColumn(0).setPreferredWidth(30);
        int[] lebar = {30, 110, 50, 85, 60, 85, 100, 60};
        for (int i = 0; i < lebar.length; i++) {
            tblSewa.getColumnModel().getColumn(i).setPreferredWidth(lebar[i]);
        }

        JPanel kanan = new JPanel(new BorderLayout());
        kanan.setBorder(BorderFactory.createTitledBorder("Riwayat Transaksi Sewa"));
        kanan.add(new JScrollPane(tblSewa), BorderLayout.CENTER);

        JPanel root = new JPanel(new BorderLayout(10, 0));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        root.add(kiri, BorderLayout.WEST);
        root.add(kanan, BorderLayout.CENTER);
        setContentPane(root);
    }

    public JComboBox<Penyewa> getPenyewaInput() { return cbPenyewa; }
    public JComboBox<Kamar> getKamarInput() { return cbKamar; }
    public JTextField getTglMasukInput() { return txtTglMasuk; }
    public JSpinner getDurasiInput() { return spDurasi; }
    public JTextField getJatuhTempoOutput() { return txtJatuhTempo; }
    public JTextField getTotalOutput() { return txtTotal; }
    public JButton getBtnProses() { return btnProses; }
    public JButton getBtnSelesai() { return btnSelesai; }
    public JButton getBtnReset() { return btnReset; }
    public JTable getTblSewa() { return tblSewa; }
    public DefaultTableModel getTableModel() { return tableModel; }
}
