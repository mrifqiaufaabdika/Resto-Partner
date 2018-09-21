package com.example.abdialam.restopatner.rest;



import com.example.abdialam.restopatner.responses.ResponseAuth;
import com.example.abdialam.restopatner.responses.ResponseListKategori;
import com.example.abdialam.restopatner.responses.ResponseSignIn;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    //fungsi ini untuk memanggil API localhost:8080/

    //get All Kategori
    @GET("kategori/")
    Call<ResponseListKategori> getAllKategori ();

    @GET("auth/signin/resto/{phone}")
    Call<ResponseSignIn> signinRequest(@Path("phone") String phone);

    //sign up resto
    @FormUrlEncoded
    @POST("auth/signup/resto")
    Call<ResponseAuth> signupRequest(@Field("restoran_nama") String restoran_nama,
                                     @Field("restoran_phone") String restoran_phone,
                                     @Field("restoran_email")String restoran_email,
                                     @Field("restoran_alamat")String restoran_alamat,
                                     @Field("restoran_lokasi")String restoran_lokasi,
                                     @Field("restoran_deskripsi") String restoran_deskripsi,
                                     @Field("restoran_gambar") String restoran_gambar,
                                     @Field("restoran_pemilik_nama") String restoran_pemilik_nama,
                                     @Field("restoran_pemilik_phone") String restoran_pemilik_phone,
                                     @Field("restoran_pemilik_email") String restoran_pemilik_email,
                                     @Field("restoran_delivery") String restoran_delivery,
                                     @Field("tarif_delivery") String tarif_delivery,
                                     @Field("restoran_delivery_jarak") String restoran_delivery_jarak,
                                     @Field("restoran_delivery_minimum") String restoran_delivery_minimum,
                                     @Field("token") String token,
                                     @Field("tipe" ) String tipe
                                     );

    //sign up resto
    @FormUrlEncoded
    @POST("auth/signup/resto/kategori")
    Call<ResponseAuth> signupKategoriRequest(@Field("restoran_id") String restoran_id,
                                     @Field("kategori_id") String kategori_id);
}
