package com.example.abdialam.restopatner.responses;

import com.example.abdialam.restopatner.models.Kurir;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKurir {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("jumlah")
    @Expose
    private Integer jumlah;
    @SerializedName("kurir")
    @Expose
    private List<Kurir> kurir = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public List<Kurir> getKurir() {
        return kurir;
    }

    public void setKurir(List<Kurir> kurir) {
        this.kurir = kurir;
    }
}
