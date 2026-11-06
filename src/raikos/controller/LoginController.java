package raikos.controller;

import javax.swing.JOptionPane;
import raikos.model.User;
import raikos.view.DashboardForm;
import raikos.view.LoginForm;

public class LoginController {
    private final LoginForm view;
    private final User model;

    public LoginController(LoginForm view, User model) {
        this.view = view;
        this.model = model;
        this.view.getBtnLogin().addActionListener(e -> prosesLogin());
    }

    private void prosesLogin() {
        String username = view.getUsernameInput().getText().trim();
        String password = new String(view.getPasswordInput().getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Username dan Password wajib diisi!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (model.login(username, password)) {
            JOptionPane.showMessageDialog(view, "Login berhasil! Selamat datang, " + model.getNama() + ".",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            DashboardForm dashboard = new DashboardForm();
            new DashboardController(dashboard, model);
            dashboard.setVisible(true);
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Username atau Password salah!",
                    "Autentikasi Gagal", JOptionPane.ERROR_MESSAGE);
            view.getPasswordInput().setText("");
        }
    }
}
