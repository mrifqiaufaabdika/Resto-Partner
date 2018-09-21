package com.example.abdialam.restopatner.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Restoran implements Serializable {
    @SerializedName("id_restoran")
    @Expose
    private Integer idRestoran;
    @SerializedName("restoran_nama")
    @Expose
    private String restoranNama;
    @SerializedName("restoran_phone")
    @Expose
    private String restoranPhone;
    @SerializedName("restoran_email")
    @Expose
    private String restoranEmail;
    @SerializedName("restoran_alamat")
    @Expose
    private String restoranAlamat;
    @SerializedName("restoran_lokasi")
    @Expose
    private String restoranLokasi;
    @SerializedName("restoran_deskripsi")
    @Expose
    private String restoranDeskripsi;
    @SerializedName("restoran_gambar")
    @Expose
    private String restoranGambar;
    @SerializedName("restoran_operasional")
    @Expose
    private Integer restoranOperasional;
    @SerializedName("restoran_pemilik_nama")
    @Expose
    private String restoranPemilikNama;
    @SerializedName("restoran_pemilik_phone")
    @Expose
    private String restoranPemilikPhone;
    @SerializedName("restoran_pemilik_email")
    @Expose
    private String restoranPemilikEmail;
    @SerializedName("restoran_balance")
    @Expose
    private String restoranBalance;
    @SerializedName("restoran_delivery")
    @Expose
    private String restoranDelivery;
    @SerializedName("tarif_delivery")
    @Expose
    private String tarifDelivery;
    @SerializedName("restoran_delivery_jarak")
    @Expose
    private Integer restoranDeliveryJarak;
    @SerializedName("restoran_delivery_minimum")
    @Expose
    private String restoranDeliveryMinimum;
    @SerializedName("restoran_create")
    @Expose
    private String restoranCreate;
    @SerializedName("id_pengguna")
    @Expose
    private Integer idPengguna;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("tipe")
    @Expose
    private String tipe;

    public Integer getIdRestoran() {
        return idRestoran;
    }

    public void setIdRestoran(Integer idRestoran) {
        this.idRestoran = idRestoran;
    }

    public String getRestoranNama() {
        return restoranNama;
    }

    public void setRestoranNama(String restoranNama) {
        this.restoranNama = restoranNama;
    }

    public String getRestoranPhone() {
        return restoranPhone;
    }

    public void setRestoranPhone(String restoranPhone) {
        this.restoranPhone = restoranPhone;
    }

    public String getRestoranEmail() {
        return restoranEmail;
    }

    public void setRestoranEmail(String restoranEmail) {
        this.restoranEmail = restoranEmail;
    }

    public String getRestoranAlamat() {
        return restoranAlamat;
    }

    public void setRestoranAlamat(String restoranAlamat) {
        this.restoranAlamat = restoranAlamat;
    }

    public String getRestoranLokasi() {
        return restoranLokasi;
    }

    public void setRestoranLokasi(String restoranLokasi) {
        this.restoranLokasi = restoranLokasi;
    }

    public String getRestoranDeskripsi() {
        return restoranDeskripsi;
    }

    public void setRestoranDeskripsi(String restoranDeskripsi) {
        this.restoranDeskripsi = restoranDeskripsi;
    }

    public String getRestoranGambar() {
        return restoranGambar;
    }

    public void setRestoranGambar(String restoranGambar) {
        this.restoranGambar = restoranGambar;
    }

    public Integer getRestoranOperasional() {
        return restoranOperasional;
    }

    public void setRestoranOperasional(Integer restoranOperasional) {
        this.restoranOperasional = restoranOperasional;
    }

    public String getRestoranPemilikNama() {
        return restoranPemilikNama;
    }

    public void setRestoranPemilikNama(String restoranPemilikNama) {
        this.restoranPemilikNama = restoranPemilikNama;
    }

    public String getRestoranPemilikPhone() {
        return restoranPemilikPhone;
    }

    public void setRestoranPemilikPhone(String restoranPemilikPhone) {
        this.restoranPemilikPhone = restoranPemilikPhone;
    }

    public String getRestoranPemilikEmail() {
        return restoranPemilikEmail;
    }

    public void setRestoranPemilikEmail(String restoranPemilikEmail) {
        this.restoranPemilikEmail = restoranPemilikEmail;
    }

    public String getRestoranBalance() {
        return restoranBalance;
    }

    public void setRestoranBalance(String restoranBalance) {
        this.restoranBalance = restoranBalance;
    }

    public String getRestoranDelivery() {
        return restoranDelivery;
    }

    public void setRestoranDelivery(String restoranDelivery) {
        this.restoranDelivery = restoranDelivery;
    }

    public String getTarifDelivery() {
        return tarifDelivery;
    }

    public void setTarifDelivery(String tarifDelivery) {
        this.tarifDelivery = tarifDelivery;
    }

    public Integer getRestoranDeliveryJarak() {
        return restoranDeliveryJarak;
    }

    public void setRestoranDeliveryJarak(Integer restoranDeliveryJarak) {
        this.restoranDeliveryJarak = restoranDeliveryJarak;
    }

    public String getRestoranDeliveryMinimum() {
        return restoranDeliveryMinimum;
    }

    public void setRestoranDeliveryMinimum(String restoranDeliveryMinimum) {
        this.restoranDeliveryMinimum = restoranDeliveryMinimum;
    }

    public String getRestoranCreate() {
        return restoranCreate;
    }

    public void setRestoranCreate(String restoranCreate) {
        this.restoranCreate = restoranCreate;
    }

    public Integer getIdPengguna() {
        return idPengguna;
    }

    public void setIdPengguna(Integer idPengguna) {
        this.idPengguna = idPengguna;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }


}
