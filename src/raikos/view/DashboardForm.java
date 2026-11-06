package raikos.view;

import java.awt.*;
import javax.swing.*;

public class DashboardForm extends JFrame {
    private final JLabel lblUser = new JLabel();
    private final JLabel lblTotalKamar = new JLabel();
    private final JLabel lblKamarTersedia = new JLabel();
    private final JLabel lblSewaAktif = new JLabel();
    private final JLabel lblPendapatan = new JLabel();

    private final JButton btnKamar = new JButton("Data Kamar Kos");
    private final JButton btnPenyewa = new JButton("Data Penyewa");
    private final JButton btnSewa = new JButton("Transaksi Sewa Kamar");
    private final JButton btnLogout = new JButton("Logout");

    public DashboardForm() {
        setTitle("Raikos - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setSize(470, 480);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(new Color(0, 121, 107));
        header.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        JLabel lblJudul = new JLabel("RAIKOS - MANAJEMEN KOS", SwingConstants.CENTER);
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblJudul.setForeground(Color.WHITE);
        lblUser.setHorizontalAlignment(SwingConstants.CENTER);
        lblUser.setForeground(new Color(224, 242, 241));
        header.add(lblJudul);
        header.add(lblUser);

        // Panel ringkasan statistik kos
        JPanel ringkasan = new JPanel(new GridLayout(4, 2, 4, 6));
        ringkasan.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Ringkasan Data Kos"),
                BorderFactory.createEmptyBorder(4, 10, 8, 10)));
        lblKamarTersedia.setForeground(new Color(46, 125, 50));
        lblSewaAktif.setForeground(new Color(230, 81, 0));
        lblPendapatan.setForeground(new Color(21, 101, 192));
        String[] judul = {"Total Kamar Kos", "Kamar Tersedia", "Penyewa Aktif", "Total Pendapatan Sewa"};
        JLabel[] nilai = {lblTotalKamar, lblKamarTersedia, lblSewaAktif, lblPendapatan};
        for (int i = 0; i < judul.length; i++) {
            JLabel lblJudulItem = new JLabel(judul[i]);
            lblJudulItem.setFont(new Font("SansSerif", Font.PLAIN, 14));
            nilai[i].setFont(new Font("SansSerif", Font.BOLD, 14));
            ringkasan.add(lblJudulItem);
            ringkasan.add(nilai[i]);
        }

        // Tombol navigasi menu
        JPanel menu = new JPanel(new GridLayout(4, 1, 8, 8));
        menu.setBorder(BorderFactory.createEmptyBorder(12, 30, 16, 30));
        for (JButton btn : new JButton[]{btnKamar, btnPenyewa, btnSewa, btnLogout}) {
            btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
            menu.add(btn);
        }

        JPanel isi = new JPanel(new BorderLayout());
        isi.setBorder(BorderFactory.createEmptyBorder(12, 14, 0, 14));
        isi.add(ringkasan, BorderLayout.NORTH);
        isi.add(menu, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
        add(isi, BorderLayout.CENTER);
    }

    public void setRingkasan(int totalKamar, int tersedia, int sewaAktif, String pendapatan) {
        lblTotalKamar.setText(": " + totalKamar + " kamar");
        lblKamarTersedia.setText(": " + tersedia + " kamar");
        lblSewaAktif.setText(": " + sewaAktif + " orang");
        lblPendapatan.setText(": " + pendapatan);
    }

    public JLabel getLblUser() { return lblUser; }
    public JButton getBtnKamar() { return btnKamar; }
    public JButton getBtnPenyewa() { return btnPenyewa; }
    public JButton getBtnSewa() { return btnSewa; }
    public JButton getBtnLogout() { return btnLogout; }
}
