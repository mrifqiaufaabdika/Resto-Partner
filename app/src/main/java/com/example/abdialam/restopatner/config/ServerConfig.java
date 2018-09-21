package com.example.abdialam.restopatner.config;

import com.example.abdialam.restopatner.rest.ApiService;
import com.example.abdialam.restopatner.rest.ClientService;

public class ServerConfig {
    // .0.2.2 ini adalah localhost.
    private static final String BASE_URL_API = "http://192.168.100.5/marketresto/public/";
    // Mendeklarasikan Interface BaseApiService
    public static ApiService getAPIService(){
        return ClientService.getClient(BASE_URL_API).create(ApiService.class);
    }
}
