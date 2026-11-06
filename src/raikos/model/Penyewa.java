package raikos.model;

import raikos.config.Koneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Penyewa {
    private int id;
    private String nama;
    private String noTelp;
    private String jenisKelamin;
    private String asal;
    private String pekerjaan;

    public Penyewa() {}

    public Penyewa(int id, String nama, String noTelp, String jenisKelamin, String asal, String pekerjaan) {
        this.id = id;
        this.nama = nama;
        this.noTelp = noTelp;
        this.jenisKelamin = jenisKelamin;
        this.asal = asal;
        this.pekerjaan = pekerjaan;
    }

    @Override
    public String toString() {
        return nama + " (" + noTelp + ")";
    }

    // =========================================================
    // OPERASI CRUD DATABASE MYSQL
    // =========================================================
    public List<Penyewa> getAllPenyewa(String keyword) {
        List<Penyewa> list = new ArrayList<>();
        String sql = "SELECT * FROM penyewa WHERE nama LIKE ? OR no_telp LIKE ? ORDER BY id DESC";
        Connection conn = Koneksi.getConnection();
        if (conn == null) return list;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Penyewa(rs.getInt("id"), rs.getString("nama"),
                            rs.getString("no_telp"), rs.getString("jenis_kelamin"),
                            rs.getString("asal"), rs.getString("pekerjaan")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal baca data penyewa: " + e.getMessage());
        }
        return list;
    }

    public boolean insertPenyewa(String nama, String telp, String jk, String asal, String pekerjaan) {
        String sql = "INSERT INTO penyewa (nama, no_telp, jenis_kelamin, asal, pekerjaan) VALUES (?, ?, ?, ?, ?)";
        Connection conn = Koneksi.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.setString(2, telp);
            ps.setString(3, jk);
            ps.setString(4, asal);
            ps.setString(5, pekerjaan);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal simpan penyewa: " + e.getMessage());
            return false;
        }
    }

    public boolean updatePenyewa(int id, String nama, String telp, String jk, String asal, String pekerjaan) {
        String sql = "UPDATE penyewa SET nama=?, no_telp=?, jenis_kelamin=?, asal=?, pekerjaan=? WHERE id=?";
        Connection conn = Koneksi.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama);
            ps.setString(2, telp);
            ps.setString(3, jk);
            ps.setString(4, asal);
            ps.setString(5, pekerjaan);
            ps.setInt(6, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal ubah penyewa: " + e.getMessage());
            return false;
        }
    }

    public boolean deletePenyewa(int id) {
        String sql = "DELETE FROM penyewa WHERE id=?";
        Connection conn = Koneksi.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal hapus penyewa: " + e.getMessage());
            return false;
        }
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public String getNoTelp() { return noTelp; }
    public String getJenisKelamin() { return jenisKelamin; }
    public String getAsal() { return asal; }
    public String getPekerjaan() { return pekerjaan; }
}
