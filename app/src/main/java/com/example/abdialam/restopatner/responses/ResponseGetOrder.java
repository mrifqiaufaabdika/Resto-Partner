package com.example.abdialam.restopatner.responses;

import com.example.abdialam.restopatner.models.Pesan;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGetOrder {
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("jumlah_pesan")
    @Expose
    private Integer jumlahPesan;
    @SerializedName("pesan")
    @Expose
    private List<Pesan> pesan = null;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getJumlahPesan() {
        return jumlahPesan;
    }

    public void setJumlahPesan(Integer jumlahPesan) {
        this.jumlahPesan = jumlahPesan;
    }

    public List<Pesan> getPesan() {
        return pesan;
    }

    public void setPesan(List<Pesan> pesan) {
        this.pesan = pesan;
    }
}