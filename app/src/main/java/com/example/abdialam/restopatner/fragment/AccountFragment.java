package com.example.abdialam.restopatner.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.activities.resto.DataKurirActivity;
import com.example.abdialam.restopatner.activities.WelcomeActivity;
import com.example.abdialam.restopatner.config.ServerConfig;
import com.example.abdialam.restopatner.models.Kurir;
import com.example.abdialam.restopatner.responses.ResponseKurir;
import com.example.abdialam.restopatner.rest.ApiService;
import com.example.abdialam.restopatner.utils.SessionManager;
import com.google.android.gms.common.api.Api;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    Context mContext ;
    SessionManager sessionManager;
    TextView btnSignout,btnDataDelivery,btnDataPemilik,tvNamaResto,tvPhoneResto,tvEmailResto,tvAlamatResto,tvBalance,tvJumlahKurir;
    LinearLayout btnDataKurir;
    ImageButton edit;
    HashMap<String,String> user ;
    ApiService mApiService;
    List<Kurir> kurirList= new ArrayList<>() ;
    int jumlahKurir;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);

        mApiService = ServerConfig.getAPIService();
        mContext =getActivity();
        sessionManager = new SessionManager(mContext);
        user = sessionManager.getRestoDetail();
        init(view);
        getAllKurir();
        setValue();
        return  view;
    }

    private void setValue() {
        tvNamaResto.setText(user.get(SessionManager.NAMA_RESTORAN));
        tvPhoneResto.setText("+"+user.get(SessionManager.NO_HP_RESTORAN));
        tvEmailResto.setText(user.get(SessionManager.EMAIL_RESTORAN));
        tvAlamatResto.setText(user.get(SessionManager.ALAMAT_RESTORAN));


        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                sessionManager.logoutUser();
                Intent intent = new Intent(mContext, WelcomeActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        btnDataKurir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DataKurirActivity.class);
                intent.putExtra("kurir", (Serializable) kurirList);
                startActivity(intent);
            }
        });

        btnDataDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"data Delivery",Toast.LENGTH_SHORT).show();
            }
        });

        btnDataPemilik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"data pemilik",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(View view) {
        btnSignout = (TextView) view.findViewById(R.id.btn_sign_out);
        btnDataKurir =(LinearLayout) view.findViewById(R.id.btnDataKurir);
        btnDataDelivery =(TextView) view.findViewById(R.id.btnDataDelivery);
        btnDataPemilik = (TextView) view.findViewById(R.id.btnDataPemilik);
        tvNamaResto = (TextView) view.findViewById(R.id.tvNamaResto);
        tvPhoneResto = (TextView) view.findViewById(R.id.tvPhoneResto);
        tvEmailResto = (TextView)view.findViewById(R.id.tvEmailResto);
        tvAlamatResto =(TextView)view.findViewById(R.id.tvAlamatResto);
        tvBalance = (TextView) view.findViewById(R.id.tvBalance);
        tvJumlahKurir =(TextView)view.findViewById(R.id.tvJumlahKurir);

    }



    //get All kurir
    private void getAllKurir(){
        mApiService.getKurir(user.get(SessionManager.ID_RESTORAN)).enqueue(new Callback<ResponseKurir>() {
            @Override
            public void onResponse(Call<ResponseKurir> call, Response<ResponseKurir> response) {
                if(response.isSuccessful()) {
                    String status = response.body().getStatus();
                    String message = response.body().getMessage();
                    if(status.equals("1")) {
                        jumlahKurir = response.body().getJumlah();
                        kurirList = response.body().getKurir();
                        tvJumlahKurir.setText(jumlahKurir + " Kurir");
                    }else {
                        tvJumlahKurir.setText("Tidak ada kurir");
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseKurir> call, Throwable t) {

            }
        });
    }
}
