CREATE DATABASE IF NOT EXISTS db_raikos;
USE db_raikos;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'Admin'
);

CREATE TABLE kamar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    kode_kamar VARCHAR(10) NOT NULL UNIQUE,
    tipe VARCHAR(20) NOT NULL,
    harga INT NOT NULL,
    fasilitas VARCHAR(255),
    status VARCHAR(20) NOT NULL DEFAULT 'Tersedia'
);

CREATE TABLE penyewa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    no_telp VARCHAR(15) NOT NULL,
    jenis_kelamin VARCHAR(15) NOT NULL,
    asal VARCHAR(100),
    pekerjaan VARCHAR(30)
);

CREATE TABLE sewa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_penyewa INT NOT NULL,
    id_kamar INT NOT NULL,
    tanggal_masuk DATE NOT NULL,
    durasi_bulan INT NOT NULL,
    total_bayar INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Aktif',
    FOREIGN KEY (id_penyewa) REFERENCES penyewa(id),
    FOREIGN KEY (id_kamar) REFERENCES kamar(id)
);

INSERT INTO users (nama, username, password, role) VALUES
('Admin Raikos', 'admin', 'admin123', 'Admin');

INSERT INTO kamar (kode_kamar, tipe, harga, fasilitas, status) VALUES
('A-01', 'Standar', 850000, 'Kasur, Lemari, Meja Belajar', 'Terisi'),
('A-02', 'Standar', 850000, 'Kasur, Lemari, Meja Belajar', 'Tersedia'),
('B-01', 'AC', 1250000, 'Kasur, Lemari, AC, WiFi', 'Terisi'),
('B-02', 'AC', 1250000, 'Kasur, Lemari, AC, WiFi', 'Tersedia'),
('C-01', 'VIP', 1750000, 'Kasur, AC, KM Dalam, WiFi, TV', 'Tersedia'),
('C-02', 'VIP', 1750000, 'Kasur, AC, KM Dalam, WiFi, TV', 'Perbaikan');

INSERT INTO penyewa (nama, no_telp, jenis_kelamin, asal, pekerjaan) VALUES
('Rizky Ramadhan', '081234567890', 'Laki-laki', 'Bekasi', 'Mahasiswa'),
('Siti Nurhaliza', '085712345678', 'Perempuan', 'Bogor', 'Karyawan'),
('Dimas Prasetyo', '089612345670', 'Laki-laki', 'Depok', 'Mahasiswa');

INSERT INTO sewa (id_penyewa, id_kamar, tanggal_masuk, durasi_bulan, total_bayar, status) VALUES
(1, 1, '2026-09-01', 3, 2550000, 'Aktif'),
(2, 3, '2026-10-01', 6, 7500000, 'Aktif');
