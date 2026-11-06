package raikos.model;

import raikos.config.Koneksi;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Sewa {
    private int id;
    private String namaPenyewa;
    private String kodeKamar;
    private LocalDate tanggalMasuk;
    private int durasi;
    private int totalBayar;
    private String status;

    public Sewa() {}

    public Sewa(int id, String namaPenyewa, String kodeKamar, LocalDate tanggalMasuk,
                int durasi, int totalBayar, String status) {
        this.id = id;
        this.namaPenyewa = namaPenyewa;
        this.kodeKamar = kodeKamar;
        this.tanggalMasuk = tanggalMasuk;
        this.durasi = durasi;
        this.totalBayar = totalBayar;
        this.status = status;
    }

    // Jatuh tempo = tanggal masuk + durasi sewa (bulan)
    public LocalDate getJatuhTempo() {
        return tanggalMasuk.plusMonths(durasi);
    }

    public List<Sewa> getAllSewa() {
        List<Sewa> list = new ArrayList<>();
        String sql = "SELECT s.*, p.nama, k.kode_kamar FROM sewa s "
                + "JOIN penyewa p ON s.id_penyewa = p.id "
                + "JOIN kamar k ON s.id_kamar = k.id ORDER BY s.id DESC";
        Connection conn = Koneksi.getConnection();
        if (conn == null) return list;

        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Sewa(rs.getInt("id"), rs.getString("nama"),
                        rs.getString("kode_kamar"), rs.getDate("tanggal_masuk").toLocalDate(),
                        rs.getInt("durasi_bulan"), rs.getInt("total_bayar"), rs.getString("status")));
            }
        } catch (SQLException e) {
            System.err.println("Gagal baca data sewa: " + e.getMessage());
        }
        return list;
    }

    // Simpan transaksi sewa dan ubah status kamar menjadi Terisi
    public boolean insertSewa(int idPenyewa, int idKamar, LocalDate tglMasuk, int durasi, int total) {
        String sqlSewa = "INSERT INTO sewa (id_penyewa, id_kamar, tanggal_masuk, durasi_bulan, total_bayar) VALUES (?, ?, ?, ?, ?)";
        String sqlKamar = "UPDATE kamar SET status = 'Terisi' WHERE id = ?";
        Connection conn = Koneksi.getConnection();
        if (conn == null) return false;

        try {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(sqlSewa);
                 PreparedStatement ps2 = conn.prepareStatement(sqlKamar)) {
                ps1.setInt(1, idPenyewa);
                ps1.setInt(2, idKamar);
                ps1.setDate(3, Date.valueOf(tglMasuk));
                ps1.setInt(4, durasi);
                ps1.setInt(5, total);
                ps1.executeUpdate();

                ps2.setInt(1, idKamar);
                ps2.executeUpdate();
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ignored) {}
            System.err.println("Gagal simpan transaksi: " + e.getMessage());
            return false;
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }

    // Akhiri sewa: status sewa Selesai, kamar kembali Tersedia
    public boolean selesaikanSewa(int idSewa) {
        String sql = "UPDATE sewa s JOIN kamar k ON s.id_kamar = k.id "
                + "SET s.status = 'Selesai', k.status = 'Tersedia' WHERE s.id = ?";
        Connection conn = Koneksi.getConnection();
        if (conn == null) return false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSewa);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal selesaikan sewa: " + e.getMessage());
            return false;
        }
    }

    public int hitungSewaAktif() {
        return hitungAgregat("SELECT COUNT(*) FROM sewa WHERE status = 'Aktif'");
    }

    public int hitungTotalPendapatan() {
        return hitungAgregat("SELECT COALESCE(SUM(total_bayar), 0) FROM sewa");
    }

    private int hitungAgregat(String sql) {
        Connection conn = Koneksi.getConnection();
        if (conn == null) return 0;

        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Gagal hitung agregasi sewa: " + e.getMessage());
        }
        return 0;
    }

    public int getId() { return id; }
    public String getNamaPenyewa() { return namaPenyewa; }
    public String getKodeKamar() { return kodeKamar; }
    public LocalDate getTanggalMasuk() { return tanggalMasuk; }
    public int getDurasi() { return durasi; }
    public int getTotalBayar() { return totalBayar; }
    public String getStatus() { return status; }
}
