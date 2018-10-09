package com.example.abdialam.restopatner.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Kurir implements Serializable {
    @SerializedName("id_kurir")
    @Expose
    private Integer idKurir;
    @SerializedName("kurir_nama")
    @Expose
    private String kurirNama;
    @SerializedName("kurir_phone")
    @Expose
    private String kurirPhone;
    @SerializedName("kurir_email")
    @Expose
    private String kurirEmail;
    @SerializedName("kurir_restoran_id")
    @Expose
    private Integer kurirRestoranId;
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

    public Integer getIdKurir() {
        return idKurir;
    }

    public void setIdKurir(Integer idKurir) {
        this.idKurir = idKurir;
    }

    public String getKurirNama() {
        return kurirNama;
    }

    public void setKurirNama(String kurirNama) {
        this.kurirNama = kurirNama;
    }

    public String getKurirPhone() {
        return kurirPhone;
    }

    public void setKurirPhone(String kurirPhone) {
        this.kurirPhone = kurirPhone;
    }

    public String getKurirEmail() {
        return kurirEmail;
    }

    public void setKurirEmail(String kurirEmail) {
        this.kurirEmail = kurirEmail;
    }

    public Integer getKurirRestoranId() {
        return kurirRestoranId;
    }

    public void setKurirRestoranId(Integer kurirRestoranId) {
        this.kurirRestoranId = kurirRestoranId;
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
