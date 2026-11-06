package raikos.model;

import raikos.config.Koneksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Kamar {
    private int id;
    private String kodeKamar;
    private String tipe;
    private int harga;
    private String fasilitas;
    private String status = "Tersedia";

    public Kamar() {}

    public Kamar(int id, String kodeKamar, String tipe, int harga, String fasilitas, String status) {
        this.id = id;
        this.kodeKamar = kodeKamar;
        this.tipe = tipe;
        this.harga = harga;
        this.fasilitas = fasilitas;
        this.status = status;
    }

    // Format angka ke Rupiah, contoh: 1250000 -> Rp 1.250.000
    public static String formatRupiah(long nilai) {
        return "Rp " + String.format("%,d", nilai).replace(',', '.');
    }

    @Override
    public String toString() {
        return kodeKamar + " - " + tipe + " (" + formatRupiah(harga) + ")";
    }

    // =========================================================
    // OPERASI CRUD DATABASE MYSQL
    // =========================================================
    public List<Kamar> getAllKamar(String keyword) {
        List<Kamar> list = new ArrayList<>();
        String sql = "SELECT * FROM kamar WHERE kode_kamar LIKE ? OR tipe LIKE ? ORDER BY kode_kamar";
        Connection conn = Koneksi.getConnection();
        if (conn == null) return list;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Kamar(rs.getInt("id"), rs.getString("kode_kamar"),
                            rs.getString("tipe"), rs.getInt("harga"),
                            rs.getString("fasilitas"), rs.getString("status")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal baca data kamar: " + e.getMessage());
        }
        return list;
    }

    public List<Kamar> getKamarTersedia() {
        List<Kamar> list = new ArrayList<>();
        for (Kamar k : getAllKamar("")) {
            if (k.getStatus().equals("Tersedia")) {
                list.add(k);
            }
        }
        return list;
    }

    public boolean insertKamar(String kode, String tipe, int harga, String fasilitas, String status) {
        String sql = "INSERT INTO kamar (kode_kamar, tipe, harga, fasilitas, status) VALUES (?, ?, ?, ?, ?)";
        Connection conn = Koneksi.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kode);
            ps.setString(2, tipe);
            ps.setInt(3, harga);
            ps.setString(4, fasilitas);
            ps.setString(5, status);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal simpan kamar: " + e.getMessage());
            return false;
        }
    }

    public boolean updateKamar(int id, String kode, String tipe, int harga, String fasilitas, String status) {
        String sql = "UPDATE kamar SET kode_kamar=?, tipe=?, harga=?, fasilitas=?, status=? WHERE id=?";
        Connection conn = Koneksi.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kode);
            ps.setString(2, tipe);
            ps.setInt(3, harga);
            ps.setString(4, fasilitas);
            ps.setString(5, status);
            ps.setInt(6, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal ubah kamar: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteKamar(int id) {
        String sql = "DELETE FROM kamar WHERE id=?";
        Connection conn = Koneksi.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal hapus kamar: " + e.getMessage());
            return false;
        }
    }

    // Hitung jumlah kamar untuk dashboard
    public int hitungKamar(boolean hanyaTersedia) {
        String sql = hanyaTersedia
                ? "SELECT COUNT(*) FROM kamar WHERE status = 'Tersedia'"
                : "SELECT COUNT(*) FROM kamar";
        Connection conn = Koneksi.getConnection();
        if (conn == null) return 0;

        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Gagal hitung kamar: " + e.getMessage());
        }
        return 0;
    }

    public int getId() { return id; }
    public String getKodeKamar() { return kodeKamar; }
    public String getTipe() { return tipe; }
    public int getHarga() { return harga; }
    public String getFasilitas() { return fasilitas; }
    public String getStatus() { return status; }
}
