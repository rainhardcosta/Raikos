package raikos.view;

import java.awt.*;
import javax.swing.*;

public class LoginForm extends JFrame {
    private final JTextField txtUsername = new JTextField(16);
    private final JPasswordField txtPassword = new JPasswordField(16);
    private final JButton btnLogin = new JButton("Login");

    public LoginForm() {
        setTitle("Raikos - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Header berwarna sebagai identitas aplikasi
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(new Color(0, 121, 107));
        header.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        JLabel lblJudul = new JLabel("RAIKOS", SwingConstants.CENTER);
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblJudul.setForeground(Color.WHITE);
        JLabel lblSub = new JLabel("Sistem Informasi Penyewaan Kamar Kos", SwingConstants.CENTER);
        lblSub.setForeground(new Color(224, 242, 241));
        header.add(lblJudul);
        header.add(lblSub);

        // Form input username dan password
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        form.add(txtUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        form.add(txtPassword, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        btnLogin.setFont(btnLogin.getFont().deriveFont(Font.BOLD));
        form.add(btnLogin, gbc);

        getRootPane().setDefaultButton(btnLogin);
        add(header, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
    }

    public JTextField getUsernameInput() { return txtUsername; }
    public JPasswordField getPasswordInput() { return txtPassword; }
    public JButton getBtnLogin() { return btnLogin; }
}
