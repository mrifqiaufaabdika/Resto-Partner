package com.example.abdialam.restopatner.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Menu implements Serializable {

    @SerializedName("id_menu")
    @Expose
    private Integer idMenu;
    @SerializedName("menu_restoran_id")
    @Expose
    private Integer menuRestoranId;
    @SerializedName("menu_kategori_id")
    @Expose
    private Integer menuKategoriId;
    @SerializedName("menu_nama")
    @Expose
    private String menuNama;
    @SerializedName("menu_deskripsi")
    @Expose
    private String menuDeskripsi;
    @SerializedName("menu_harga")
    @Expose
    private String menuHarga;
    @SerializedName("menu_gambar")
    @Expose
    private String menuGambar;
    @SerializedName("menu_ketersedian")
    @Expose
    private Integer menuKetersedian;
    @SerializedName("menu_discont")
    @Expose
    private Object menuDiscont;
    @SerializedName("id_kategori")
    @Expose
    private Integer idKategori;
    @SerializedName("kategori_nama")
    @Expose
    private String kategoriNama;
    @SerializedName("kategori_deskripsi")
    @Expose
    private String kategoriDeskripsi;
    @SerializedName("Favorit")
    @Expose
    private Integer favorit;

    public Integer getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Integer idMenu) {
        this.idMenu = idMenu;
    }

    public Integer getMenuRestoranId() {
        return menuRestoranId;
    }

    public void setMenuRestoranId(Integer menuRestoranId) {
        this.menuRestoranId = menuRestoranId;
    }

    public Integer getMenuKategoriId() {
        return menuKategoriId;
    }

    public void setMenuKategoriId(Integer menuKategoriId) {
        this.menuKategoriId = menuKategoriId;
    }

    public String getMenuNama() {
        return menuNama;
    }

    public void setMenuNama(String menuNama) {
        this.menuNama = menuNama;
    }

    public String getMenuDeskripsi() {
        return menuDeskripsi;
    }

    public void setMenuDeskripsi(String menuDeskripsi) {
        this.menuDeskripsi = menuDeskripsi;
    }

    public String getMenuHarga() {
        return menuHarga;
    }

    public void setMenuHarga(String menuHarga) {
        this.menuHarga = menuHarga;
    }

    public String getMenuGambar() {
        return menuGambar;
    }

    public void setMenuGambar(String menuGambar) {
        this.menuGambar = menuGambar;
    }

    public Integer getMenuKetersedian() {
        return menuKetersedian;
    }

    public void setMenuKetersedian(Integer menuKetersedian) {
        this.menuKetersedian = menuKetersedian;
    }

    public Object getMenuDiscont() {
        return menuDiscont;
    }

    public void setMenuDiscont(Object menuDiscont) {
        this.menuDiscont = menuDiscont;
    }

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
    public Integer getFavorit() {
        return favorit;
    }

    public void setFavorit(Integer favorit) {
        this.favorit = favorit;
    }

}
