package com.example.abdialam.restopatner.responses;

import com.example.abdialam.restopatner.models.Kategori;
import com.example.abdialam.restopatner.models.Menu;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMenuKategori {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("kategori")
    @Expose
    private List<Kategori> kategori = null;
    @SerializedName("menu")
    @Expose
    private List<Menu> menu = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Kategori> getKategori() {
        return kategori;
    }

    public void setKategori(List<Kategori> kategori) {
        this.kategori = kategori;
    }

    public List<Menu> getMenu() {
        return menu;
    }

    public void setMenu(List<Menu> menu) {
        this.menu = menu;
    }

}
