package com.example.abdialam.restopatner.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.example.abdialam.restopatner.models.Restoran;

import java.util.HashMap;

public class SessionManager {

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private Context _context;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String ID_RESTORAN = "idUser";
    public static final String ID_PENGGUNA = "username";

    public static final String EMAIL_RESTORAN = "email";
    public static final String NAMA_RESTORAN = "namaLengkap";
    public static final String NO_HP_RESTORAN = "noHP";

    public static final String EMAIL_PEMILIK = "email";
    public static final String NAMA_PEMILIK = "namaPemilik";
    public static final String NO_HP_PEMILIK = "noPhonePemilik";

    public Context get_context() {
        return _context;
    }

    //constuctor
    public SessionManager(Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(Restoran user){
        editor.putBoolean(IS_LOGGED_IN,true);
        editor.putString(ID_RESTORAN, String.valueOf(user.getIdRestoran()));
        editor.putString(ID_PENGGUNA, String.valueOf(user.getIdPengguna()));

        editor.putString(NAMA_RESTORAN,user.getRestoranNama());
        editor.putString(EMAIL_RESTORAN,user.getRestoranEmail());
        editor.putString(NO_HP_RESTORAN,user.getRestoranPhone());

        editor.putString(NAMA_PEMILIK,user.getRestoranPemilikNama());
        editor.putString(EMAIL_PEMILIK,user.getRestoranPemilikEmail());
        editor.putString(NO_HP_PEMILIK,user.getRestoranPemilikPhone());
        editor.commit();
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(ID_RESTORAN,sharedPreferences.getString(ID_RESTORAN,null));
        user.put(ID_PENGGUNA, sharedPreferences.getString(ID_PENGGUNA,null));

        user.put(NAMA_RESTORAN, sharedPreferences.getString(NAMA_RESTORAN,null));
        user.put(EMAIL_RESTORAN, sharedPreferences.getString(EMAIL_RESTORAN,null));
        user.put(NO_HP_RESTORAN, sharedPreferences.getString(NO_HP_RESTORAN,null));

        user.put(NAMA_PEMILIK, sharedPreferences.getString(NAMA_PEMILIK,null));
        user.put(EMAIL_PEMILIK, sharedPreferences.getString(EMAIL_PEMILIK,null));
        user.put(NO_HP_PEMILIK, sharedPreferences.getString(NO_HP_PEMILIK,null));
        return user;
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN,false);
    }
}
