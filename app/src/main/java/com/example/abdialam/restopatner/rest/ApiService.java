package com.example.abdialam.restopatner.rest;



import com.example.abdialam.restopatner.responses.ResponseAuth;
import com.example.abdialam.restopatner.responses.ResponseGetOrder;
import com.example.abdialam.restopatner.responses.ResponseKurir;
import com.example.abdialam.restopatner.responses.ResponseListKategori;
import com.example.abdialam.restopatner.responses.ResponseMenuKategori;
import com.example.abdialam.restopatner.responses.ResponseSignIn;
import com.example.abdialam.restopatner.responses.ResponseValue;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    //fungsi ini untuk memanggil API localhost:8080/

    //get All Kategori
    @GET("kategori/")
    Call<ResponseListKategori> getAllKategori ();

    @GET("auth/signin/resto/{phone}")
    Call<ResponseSignIn> signinRequest(@Path("phone") String phone);

    //update token
    @FormUrlEncoded
    @PUT("auth/token/{id_pengguna}")
    Call<ResponseAuth> updateToken (@Path("id_pengguna") String id_pengguna,
                                    @Field("token") String token);

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

    //sign up resto Kategori
    @FormUrlEncoded
    @POST("auth/signup/resto/kategori")
    Call<ResponseAuth> signupKategoriRequest(@Field("restoran_id") String restoran_id,
                                     @Field("kategori_id") String kategori_id);

    //get all order
    @GET("order/{id_restoran}")
    Call<ResponseGetOrder> getOrder (@Path("id_restoran") String id_restoran,
                                     @Query("key") String key);


    //get menu & kategori
    @GET("menu/restoran/{id_restoran}")
    Call<ResponseMenuKategori> getMenuKategori(@Path("id_restoran") String id_restoran);

    //add menu
    @Multipart
    @POST("menu/")
    Call<ResponseBody> addMenu (@Part MultipartBody.Part photo,
                                @Part ("menu_nama") String menu_nama,
                                @Part ("menu_deskripsi") String menu_deskripsi,
                                @Part("menu_harga") String menu_harga,
                                @Part("menu_restoran_id") String menu_restoran_id,
                                @Part("menu_kategori_id") String menu_kategori_id);
    @FormUrlEncoded
    @PUT("restoran/{id_restoran}")
    Call<ResponseValue> setOperasional (@Path("id_restoran") String id_restoran,
                                        @Field("operasional") int operasional);


    @FormUrlEncoded
    @PUT("menu/{id_menu}")
    Call<ResponseValue> setKetersedianMenu (@Path("id_menu") String id_menu,
                                        @Field("ketersedian") int ketersedian);

    @FormUrlEncoded
    @POST("restoran/kurir/")
    Call<ResponseValue> addKurir (@Field("kurir_restoran_id") String kurir_restoran_id,
                                  @Field("kurir_phone") String kurir_phone,
                                  @Field("kurir_nama") String kurir_nama,
                                  @Field("kurir_email") String kurir_email);



    @GET("restoran/kurir/{id_restoran}")
    Call<ResponseKurir> getKurir (@Path("id_restoran") String id_restoran);


}
