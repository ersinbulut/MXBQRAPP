package com.mxbqr.app.model;

public class Personel {
    private String id;

    private String adsoyad;
    private String tc;
    private String sicilno;
    private String birim;
    private String adres;
    private String telefon;
    private String lokasyon;

    private String kullanici_adi;
    private String kullanici_sifre;

    public Personel() {
    }

    public Personel(String id, String adsoyad, String tc, String sicilno, String birim, String adres, String telefon, String lokasyon, String kullanici_adi, String kullanici_sifre) {
        this.id = id;
        this.adsoyad = adsoyad;
        this.tc = tc;
        this.sicilno = sicilno;
        this.birim = birim;
        this.adres = adres;
        this.telefon = telefon;
        this.lokasyon = lokasyon;
        this.kullanici_adi = kullanici_adi;
        this.kullanici_sifre = kullanici_sifre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdsoyad() {
        return adsoyad;
    }

    public void setAdsoyad(String adsoyad) {
        this.adsoyad = adsoyad;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getSicilno() {
        return sicilno;
    }

    public void setSicilno(String sicilno) {
        this.sicilno = sicilno;
    }

    public String getBirim() {
        return birim;
    }

    public void setBirim(String birim) {
        this.birim = birim;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getLokasyon() {
        return lokasyon;
    }

    public void setLokasyon(String lokasyon) {
        this.lokasyon = lokasyon;
    }

    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }

    public String getKullanici_sifre() {
        return kullanici_sifre;
    }

    public void setKullanici_sifre(String kullanici_sifre) {
        this.kullanici_sifre = kullanici_sifre;
    }
}
