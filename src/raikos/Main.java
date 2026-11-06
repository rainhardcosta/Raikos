package raikos;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import raikos.controller.LoginController;
import raikos.model.User;
import raikos.view.LoginForm;

public class Main {
    public static void main(String[] args) {
        // Tampilan Nimbus bawaan template NetBeans
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Look and Feel gagal dimuat: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            LoginForm view = new LoginForm();
            new LoginController(view, new User());
            view.setVisible(true);
        });
    }
}
