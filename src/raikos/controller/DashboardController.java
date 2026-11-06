package raikos.controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import raikos.model.Kamar;
import raikos.model.Penyewa;
import raikos.model.Sewa;
import raikos.model.User;
import raikos.view.DashboardForm;
import raikos.view.KamarForm;
import raikos.view.LoginForm;
import raikos.view.PenyewaForm;
import raikos.view.SewaForm;

public class DashboardController {
    private final DashboardForm view;
    private final User user;
    private final Kamar kamarModel = new Kamar();
    private final Sewa sewaModel = new Sewa();

    public DashboardController(DashboardForm view, User user) {
        this.view = view;
        this.user = user;
        this.view.getLblUser().setText("Login sebagai: " + user.getNama() + " (" + user.getRole() + ")");
        refreshRingkasan();

        view.getBtnKamar().addActionListener(e -> {
            KamarForm form = new KamarForm();
            new KamarController(form, new Kamar());
            bukaForm(form);
        });
        view.getBtnPenyewa().addActionListener(e -> {
            PenyewaForm form = new PenyewaForm();
            new PenyewaController(form, new Penyewa());
            bukaForm(form);
        });
        view.getBtnSewa().addActionListener(e -> {
            SewaForm form = new SewaForm();
            new SewaController(form, new Sewa());
            bukaForm(form);
        });
        view.getBtnLogout().addActionListener(e -> logout());
    }

    // Statistik dashboard dihitung ulang setiap form anak ditutup
    private void bukaForm(JFrame form) {
        form.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                refreshRingkasan();
            }
        });
        form.setVisible(true);
    }

    public void refreshRingkasan() {
        view.setRingkasan(
                kamarModel.hitungKamar(false),
                kamarModel.hitungKamar(true),
                sewaModel.hitungSewaAktif(),
                Kamar.formatRupiah(sewaModel.hitungTotalPendapatan()));
    }

    private void logout() {
        int pilih = JOptionPane.showConfirmDialog(view, "Yakin ingin logout dari Raikos?",
                "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
        if (pilih == JOptionPane.YES_OPTION) {
            view.dispose();
            LoginForm login = new LoginForm();
            new LoginController(login, new User());
            login.setVisible(true);
        }
    }
}
