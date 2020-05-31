package com.aa183.willian;

import java.util.Date;

public class Mahasiswa {

    private int idMahasiswa;
    private String namamahasiswa;
    private Date tanggal;
    private String gambar;
    private String namapanggilan;
    private String nim;
    private String jurusan;


    public Mahasiswa(int idMahasiswa, String namamahasiswa, Date tanggal, String gambar, String namapanggilan, String nim, String jurusan) {
        this.idMahasiswa = idMahasiswa;
        this.namamahasiswa = namamahasiswa;
        this.tanggal = tanggal;
        this.gambar = gambar;
        this.namapanggilan = namapanggilan;
        this.nim = nim;
        this.jurusan = jurusan;
    }

    public int getIdMahasiswa() {
        return idMahasiswa;
    }

    public void setIdMahasiswa(int idMahasiswa) {

        this.idMahasiswa = idMahasiswa;
    }

    public String getNamamahasiswa() {

        return namamahasiswa;
    }

    public void setNamamahasiswa(String namamahasiswa) {

        this.namamahasiswa = namamahasiswa;
    }

    public Date getTanggal() {

        return tanggal;
    }

    public void setTanggal(Date tanggal) {

        this.tanggal = tanggal;
    }

    public String getGambar() {

        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getNamapanggilan() {
        return namapanggilan;
    }

    public void setNamapanggilan(String namapanggilan) {

        this.namapanggilan = namapanggilan;
    }

    public String getNim() {

        return nim;
    }

    public void setNim(String nim) {

        this.nim = nim;
    }

    public String getJurusan() {

        return jurusan;
    }

    public void setJurusan(String jurusan)
    {
        this.jurusan = jurusan;
    }

    }

