package raikos.model;

import raikos.config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String nama;
    private String username;
    private String password;
    private String role;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Validasi login via JDBC MySQL (tabel users)
    public boolean login(String usernameInput, String passwordInput) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        Connection conn = Koneksi.getConnection();

        if (conn == null) {
            return false;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usernameInput);
            ps.setString(2, passwordInput);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    this.id = rs.getInt("id");
                    this.nama = rs.getString("nama");
                    this.username = rs.getString("username");
                    this.role = rs.getString("role");
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Query Error: " + e.getMessage());
        }
        return false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
