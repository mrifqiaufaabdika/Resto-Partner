package com.example.abdialam.restopatner.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Pesan implements Serializable{
    @SerializedName("id_pesan")
    @Expose
    private Integer idPesan;
    @SerializedName("konsumen_id")
    @Expose
    private Integer konsumenId;
    @SerializedName("restoran_id")
    @Expose
    private Integer restoranId;
    @SerializedName("pesan_waktu")
    @Expose
    private String pesanWaktu;
    @SerializedName("pesan_lokasi")
    @Expose
    private String pesanLokasi;
    @SerializedName("pesan_alamat")
    @Expose
    private String pesanAlamat;
    @SerializedName("pesan_catatan")
    @Expose
    private String pesanCatatan;
    @SerializedName("pesan_metode_bayar")
    @Expose
    private String pesanMetodeBayar;
    @SerializedName("jarak_antar")
    @Expose
    private Integer jarakAntar;
    @SerializedName("pesan_biaya_antar")
    @Expose
    private String pesanBiayaAntar;
    @SerializedName("pesan_status")
    @Expose
    private String pesanStatus;
    @SerializedName("id_konsumen")
    @Expose
    private Integer idKonsumen;
    @SerializedName("konsumen_phone")
    @Expose
    private String konsumenPhone;
    @SerializedName("konsumen_nama")
    @Expose
    private String konsumenNama;
    @SerializedName("konsumen_email")
    @Expose
    private String konsumenEmail;
    @SerializedName("konsumen_credit_balance")
    @Expose
    private Integer konsumenCreditBalance;
    @SerializedName("konsumen_baru")
    @Expose
    private Integer konsumenBaru;
    @SerializedName("blacklist")
    @Expose
    private Integer blacklist;
    @SerializedName("konsumen_create")
    @Expose
    private String konsumenCreate;
    @SerializedName("detailorder")
    @Expose
    private List<Detailorder> detailorder = null;

    public Integer getIdPesan() {
        return idPesan;
    }

    public void setIdPesan(Integer idPesan) {
        this.idPesan = idPesan;
    }

    public Integer getKonsumenId() {
        return konsumenId;
    }

    public void setKonsumenId(Integer konsumenId) {
        this.konsumenId = konsumenId;
    }

    public Integer getRestoranId() {
        return restoranId;
    }

    public void setRestoranId(Integer restoranId) {
        this.restoranId = restoranId;
    }

    public String getPesanWaktu() {
        return pesanWaktu;
    }

    public void setPesanWaktu(String pesanWaktu) {
        this.pesanWaktu = pesanWaktu;
    }

    public String getPesanLokasi() {
        return pesanLokasi;
    }

    public void setPesanLokasi(String pesanLokasi) {
        this.pesanLokasi = pesanLokasi;
    }

    public String getPesanAlamat() {
        return pesanAlamat;
    }

    public void setPesanAlamat(String pesanAlamat) {
        this.pesanAlamat = pesanAlamat;
    }

    public String getPesanCatatan() {
        return pesanCatatan;
    }

    public void setPesanCatatan(String pesanCatatan) {
        this.pesanCatatan = pesanCatatan;
    }

    public String getPesanMetodeBayar() {
        return pesanMetodeBayar;
    }

    public void setPesanMetodeBayar(String pesanMetodeBayar) {
        this.pesanMetodeBayar = pesanMetodeBayar;
    }

    public Integer getJarakAntar() {
        return jarakAntar;
    }

    public void setJarakAntar(Integer jarakAntar) {
        this.jarakAntar = jarakAntar;
    }

    public String getPesanBiayaAntar() {
        return pesanBiayaAntar;
    }

    public void setPesanBiayaAntar(String pesanBiayaAntar) {
        this.pesanBiayaAntar = pesanBiayaAntar;
    }

    public String getPesanStatus() {
        return pesanStatus;
    }

    public void setPesanStatus(String pesanStatus) {
        this.pesanStatus = pesanStatus;
    }

    public Integer getIdKonsumen() {
        return idKonsumen;
    }

    public void setIdKonsumen(Integer idKonsumen) {
        this.idKonsumen = idKonsumen;
    }

    public String getKonsumenPhone() {
        return konsumenPhone;
    }

    public void setKonsumenPhone(String konsumenPhone) {
        this.konsumenPhone = konsumenPhone;
    }

    public String getKonsumenNama() {
        return konsumenNama;
    }

    public void setKonsumenNama(String konsumenNama) {
        this.konsumenNama = konsumenNama;
    }

    public String getKonsumenEmail() {
        return konsumenEmail;
    }

    public void setKonsumenEmail(String konsumenEmail) {
        this.konsumenEmail = konsumenEmail;
    }

    public Integer getKonsumenCreditBalance() {
        return konsumenCreditBalance;
    }

    public void setKonsumenCreditBalance(Integer konsumenCreditBalance) {
        this.konsumenCreditBalance = konsumenCreditBalance;
    }

    public Integer getKonsumenBaru() {
        return konsumenBaru;
    }

    public void setKonsumenBaru(Integer konsumenBaru) {
        this.konsumenBaru = konsumenBaru;
    }

    public Integer getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Integer blacklist) {
        this.blacklist = blacklist;
    }

    public String getKonsumenCreate() {
        return konsumenCreate;
    }

    public void setKonsumenCreate(String konsumenCreate) {
        this.konsumenCreate = konsumenCreate;
    }

    public List<Detailorder> getDetailorder() {
        return detailorder;
    }

    public void setDetailorder(List<Detailorder> detailorder) {
        this.detailorder = detailorder;
    }

}
