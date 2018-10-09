package com.example.abdialam.restopatner.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Detailorder implements Serializable{
    @SerializedName("id_detail_pesan")
    @Expose
    private Integer idDetailPesan;
    @SerializedName("pesan_id")
    @Expose
    private Integer pesanId;
    @SerializedName("menu_id")
    @Expose
    private Integer menuId;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("catatan")
    @Expose
    private String catatan;
    @SerializedName("menu_nama")
    @Expose
    private String menuNama;
    @SerializedName("jumlah")
    @Expose
    private String jumlah;

    public Integer getIdDetailPesan() {
        return idDetailPesan;
    }

    public void setIdDetailPesan(Integer idDetailPesan) {
        this.idDetailPesan = idDetailPesan;
    }

    public Integer getPesanId() {
        return pesanId;
    }

    public void setPesanId(Integer pesanId) {
        this.pesanId = pesanId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getMenuNama() {
        return menuNama;
    }

    public void setMenuNama(String menuNama) {
        this.menuNama = menuNama;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}
