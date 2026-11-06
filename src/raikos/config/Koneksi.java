package raikos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static Connection koneksi;

    // Konfigurasi MySQL XAMPP untuk database db_raikos
    private static final String URL = "jdbc:mysql://localhost:3306/db_raikos";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        if (koneksi == null) {
            try {
                koneksi = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.err.println("Gagal terhubung ke MySQL: " + e.getMessage());
            }
        }
        return koneksi;
    }
}
