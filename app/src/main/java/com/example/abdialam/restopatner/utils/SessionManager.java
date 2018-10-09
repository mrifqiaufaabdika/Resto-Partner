package com.example.abdialam.restopatner.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.abdialam.restopatner.models.Kurir;
import com.example.abdialam.restopatner.models.Restoran;

import java.util.HashMap;

public class SessionManager {

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private Context _context;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String IS_RESTORANT = "isrestorant";

    public static final String ID_RESTORAN = "idUser";
    public static final String ID_PENGGUNA = "username";

    public static final String ID_KURIR = "id_kurir";
    public static final String KURIR_NAMA ="kurir_nama";
    public static final String KURIR_PHONE ="kurir_phone";
    public static final String KURIR_EMAIL = "kurir_email";

    public static final String EMAIL_RESTORAN = "email";
    public static final String NAMA_RESTORAN = "namaLengkap";
    public static final String NO_HP_RESTORAN = "noHP";
    public static final String ALAMAT_RESTORAN ="alamat";
    public static final String DESKRIPSI_RESTORAN ="deskripsi";

    public static final String EMAIL_PEMILIK = "email";
    public static final String NAMA_PEMILIK = "namaPemilik";
    public static final String NO_HP_PEMILIK = "noPhonePemilik";
    public static final String OPERASIONAL = "opersional";

    public static final String RESTORAN_DELIVERY = "delivery";
    public static final String TARIF_DELIVERY = "tarif delivery";
    public static final String RESTORAN_DELIVERY_JARAK = "delivery";
    public static final String RESTORAN_DELIVERY_MINIMUM = "delivery";

    public static final String TIPE = "tipe";

    public Context get_context() {
        return _context;
    }

    //constuctor
    public SessionManager(Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSessionResto(Restoran user){
        editor.putBoolean(IS_LOGGED_IN,true);
        editor.putBoolean(IS_RESTORANT,true);
        editor.putString(ID_RESTORAN, String.valueOf(user.getIdRestoran()));
        editor.putString(ID_PENGGUNA, String.valueOf(user.getIdPengguna()));

        editor.putString(NAMA_RESTORAN,user.getRestoranNama());
        editor.putString(EMAIL_RESTORAN,user.getRestoranEmail());
        editor.putString(NO_HP_RESTORAN,user.getRestoranPhone());
        editor.putString(ALAMAT_RESTORAN,user.getRestoranAlamat());
        editor.putString(DESKRIPSI_RESTORAN, user.getRestoranDeskripsi());

        editor.putString(NAMA_PEMILIK,user.getRestoranPemilikNama());
        editor.putString(EMAIL_PEMILIK,user.getRestoranPemilikEmail());
        editor.putString(NO_HP_PEMILIK,user.getRestoranPemilikPhone());
        editor.putInt(OPERASIONAL,user.getRestoranOperasional());

        editor.putString(RESTORAN_DELIVERY,user.getRestoranDelivery());
        editor.putString(TARIF_DELIVERY ,user.getTarifDelivery());
        editor.putString(RESTORAN_DELIVERY_JARAK,String.valueOf(user.getRestoranDeliveryJarak()));
        editor.putString(RESTORAN_DELIVERY_MINIMUM,user.getRestoranDeliveryMinimum());

        editor.putString(TIPE,user.getTipe());
        editor.commit();
    }

    public void createLoginSessionKurir (Kurir user){
        editor.putBoolean(IS_LOGGED_IN,true);
        editor.putString(ID_RESTORAN, String.valueOf(user.getKurirRestoranId()));
        editor.putString(ID_PENGGUNA, String.valueOf(user.getIdPengguna()));

        editor.putString(ID_KURIR,user.getIdKurir().toString());
        editor.putString(KURIR_NAMA,user.getKurirNama());
        editor.putString(KURIR_PHONE,user.getKurirPhone());
        editor.putString(KURIR_EMAIL,user.getKurirEmail());
        editor.putString(TIPE, user.getTipe());


        editor.commit();
    }



    public void updateOprasinal(int oprasional) {
        editor.putInt(OPERASIONAL, oprasional);
        editor.commit();
    }

    public HashMap<String,String> getRestoDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(ID_RESTORAN,sharedPreferences.getString(ID_RESTORAN,null));
        user.put(ID_PENGGUNA, sharedPreferences.getString(ID_PENGGUNA,null));

        user.put(NAMA_RESTORAN, sharedPreferences.getString(NAMA_RESTORAN,null));
        user.put(EMAIL_RESTORAN, sharedPreferences.getString(EMAIL_RESTORAN,null));
        user.put(NO_HP_RESTORAN, sharedPreferences.getString(NO_HP_RESTORAN,null));


        user.put(NAMA_PEMILIK, sharedPreferences.getString(NAMA_PEMILIK,null));
        user.put(EMAIL_PEMILIK, sharedPreferences.getString(EMAIL_PEMILIK,null));
        user.put(NO_HP_PEMILIK, sharedPreferences.getString(NO_HP_PEMILIK,null));
        user.put(OPERASIONAL, String.valueOf(sharedPreferences.getInt(OPERASIONAL,0)));
        return user;
    }

    public HashMap<String,String> getKurirDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(ID_RESTORAN,sharedPreferences.getString(ID_RESTORAN,null));
        user.put(ID_PENGGUNA, sharedPreferences.getString(ID_PENGGUNA,null));

        user.put(ID_KURIR, sharedPreferences.getString(ID_KURIR,null));
        user.put(KURIR_NAMA, sharedPreferences.getString(KURIR_NAMA,null));
        user.put(KURIR_PHONE, sharedPreferences.getString(KURIR_PHONE,null));


        user.put(KURIR_EMAIL, sharedPreferences.getString(KURIR_EMAIL,null));
        user.put(TIPE, sharedPreferences.getString(TIPE,null));

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

    public boolean isRestoran(){ return sharedPreferences.getBoolean(IS_RESTORANT, false);}
}
