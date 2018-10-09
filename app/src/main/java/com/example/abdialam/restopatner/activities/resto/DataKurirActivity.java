package com.example.abdialam.restopatner.activities.resto;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.adapter.KurirAdapter;
import com.example.abdialam.restopatner.config.ServerConfig;
import com.example.abdialam.restopatner.models.Kurir;
import com.example.abdialam.restopatner.responses.ResponseKurir;
import com.example.abdialam.restopatner.responses.ResponseValue;
import com.example.abdialam.restopatner.rest.ApiService;
import com.example.abdialam.restopatner.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKurirActivity extends AppCompatActivity{

    AlertDialog.Builder dialog;
    AlertDialog myAlert;
    ApiService mApiService ;
    Context mContext;
    EditText mNamaKurir,mPhoneKurir,mEmailKurir;
    SessionManager sessionManager;
    List<Kurir> kurirList = new ArrayList<>();
    HashMap<String,String> user;

    RecyclerView recyclerView;
    KurirAdapter kurirAdapter;






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_view);
        getSupportActionBar().setTitle("Daftar Kurir");
        mApiService = ServerConfig.getAPIService();
        mContext = this;
        ButterKnife.bind(this);
        sessionManager = new SessionManager(mContext);
        user = sessionManager.getRestoDetail();
        kurirList= (List<Kurir>) getIntent().getSerializableExtra("kurir");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        kurirAdapter = new KurirAdapter(mContext,kurirList);
        recyclerView.setAdapter(kurirAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addkurir,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.addkurir){
                DialogAddKurir();
        }
        return true;
    }


 //form input kurir baru
    private void DialogAddKurir() {
        dialog = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_kurir,null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Tambah Kurir");

        mNamaKurir = (EditText) dialogView.findViewById(R.id.etNamaKurir);
        mPhoneKurir = (EditText) dialogView.findViewById(R.id.etPhoneKurir);
        mEmailKurir= (EditText) dialogView.findViewById(R.id.etEmailKurir);


        dialog.setPositiveButton("Tambah Kurir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            //insert new kurir
                String id_restoran = user.get(SessionManager.ID_RESTORAN);
                String nama = mNamaKurir.getText().toString();
                String phone = mPhoneKurir.getText().toString();
                String email = mEmailKurir.getText().toString();
                addNewKurir(id_restoran,phone,nama,email);

            }
        });

        dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myAlert.dismiss();

            }
        });

        myAlert = dialog.create();
        myAlert.show();


    }





//menambah kurir baru
    private void addNewKurir(String id_restoran,String phone,String nama, String email) {
        mApiService.addKurir(id_restoran,phone,nama,email).enqueue(new Callback<ResponseValue>() {
            @Override
            public void onResponse(Call<ResponseValue> call, Response<ResponseValue> response) {

                if(response.isSuccessful()){
                    String value = response.body().getStatus();
                    String message = response.body().getMessage();

                    if(value.equals("1")){
                        myAlert.dismiss();

                        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
                    }else {
                        myAlert.dismiss();
                        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseValue> call, Throwable t) {
                Toast.makeText(mContext,R.string.loss_connect,Toast.LENGTH_SHORT).show();
                myAlert.dismiss();
            }
        });
    }
}
