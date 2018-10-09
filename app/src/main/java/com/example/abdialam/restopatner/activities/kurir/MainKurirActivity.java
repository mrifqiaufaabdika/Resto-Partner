package com.example.abdialam.restopatner.activities.kurir;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.activities.WelcomeActivity;
import com.example.abdialam.restopatner.adapter.OrderAdapter;
import com.example.abdialam.restopatner.config.ServerConfig;
import com.example.abdialam.restopatner.models.Pesan;
import com.example.abdialam.restopatner.responses.ResponseGetOrder;
import com.example.abdialam.restopatner.rest.ApiService;
import com.example.abdialam.restopatner.utils.SessionManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainKurirActivity extends AppCompatActivity{

    SessionManager sessionManager;
    HashMap<String,String> user ;
    Context mContext;
    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    List<Pesan> orderList;
    ApiService mApiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._activity_main_kurir);
        mContext = this;
        mApiService = ServerConfig.getAPIService();
        sessionManager = new SessionManager(mContext);
        user = sessionManager.getKurirDetail();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setData();


    }

    private void setData() {
        String id_restoran = user.get(SessionManager.ID_RESTORAN).toString();
        mApiService.getOrder(id_restoran,"proses").enqueue(new Callback<ResponseGetOrder>() {
            @Override
            public void onResponse(Call<ResponseGetOrder> call, Response<ResponseGetOrder> response) {
                orderList = response.body().getPesan();
                String value = response.body().getValue();
                String message = response.body().getMessage();
                if(value.equals("1")){
                    orderAdapter = new OrderAdapter(mContext,orderList);
                    recyclerView.setAdapter(orderAdapter);
                }
                Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseGetOrder> call, Throwable t) {
                Toast.makeText(mContext,R.string.loss_connect,Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kurir,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId()==R.id.account){

        }else if(item.getItemId()==R.id.Logout){
            FirebaseAuth.getInstance().signOut();
            sessionManager.logoutUser();
            Intent intent = new Intent(mContext, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}
