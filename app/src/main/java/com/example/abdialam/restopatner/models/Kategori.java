package com.example.abdialam.restopatner.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kategori {
    @SerializedName("id_kategori")
    @Expose
    private Integer idKategori;
    @SerializedName("kategori_nama")
    @Expose
    private String kategoriNama;
    @SerializedName("kategori_deskripsi")
    @Expose
    private String kategoriDeskripsi;
    @SerializedName ("is_selected")
    @Expose
    private boolean isSelected;

    public Integer getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(Integer idKategori) {
        this.idKategori = idKategori;
    }

    public String getKategoriNama() {
        return kategoriNama;
    }

    public void setKategoriNama(String kategoriNama) {
        this.kategoriNama = kategoriNama;
    }

    public String getKategoriDeskripsi() {
        return kategoriDeskripsi;
    }

    public void setKategoriDeskripsi(String kategoriDeskripsi) {
        this.kategoriDeskripsi = kategoriDeskripsi;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
