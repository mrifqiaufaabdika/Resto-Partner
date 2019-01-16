package com.example.abdialam.restopatner.activities.kurir;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.activities.DeliveryActivity;
import com.example.abdialam.restopatner.activities.SignInActivity;
import com.example.abdialam.restopatner.adapter.OrderAdapter;
import com.example.abdialam.restopatner.config.ServerConfig;
import com.example.abdialam.restopatner.models.Order;
import com.example.abdialam.restopatner.responses.ResponseOrder;
import com.example.abdialam.restopatner.rest.ApiService;
import com.example.abdialam.restopatner.utils.SessionManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
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
    List<Order> orderList;
    ApiService mApiService;
    CoordinatorLayout coordinatorLayout;

    View message;
    ImageView icon_message;
    TextView title_message,sub_title_message;

    SwipeRefreshLayout swipeRefreshLayout;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._activity_main_kurir);
        mContext = this;
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mApiService = ServerConfig.getAPIService();
        sessionManager = new SessionManager(mContext);
        user = sessionManager.getKurirDetail();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        swipeRefreshLayout =(SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        message = findViewById(R.id.error);
        icon_message = (ImageView) findViewById(R.id.img_msg);
        title_message =  (TextView) findViewById(R.id.title_msg);
        sub_title_message =  (TextView) findViewById(R.id.sub_title_msg);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               newAsycn();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void newAsycn() {
        String id_restoran = user.get(SessionManager.ID_RESTORAN).toString();
        ArrayList<String> status = new ArrayList<String>();
        status.add("proses");
        mApiService.getOrder(id_restoran,status).enqueue(new Callback<ResponseOrder>() {
            @Override
            public void onResponse(Call<ResponseOrder> call, Response<ResponseOrder> response) {
                if (response.isSuccessful()){
                    if (response.body().getValue().equals("1")){
                        orderAdapter.clear();
                        orderAdapter.addAll(response.body().getData());
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseOrder> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(coordinatorLayout,R.string.loss_connect, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressDialog = ProgressDialog.show(mContext,null,getString(R.string.memuat),true,false);
        setData();
    }

    private void setData() {
        String id_restoran = user.get(SessionManager.ID_RESTORAN).toString();
        ArrayList<String> status = new ArrayList<String>();
        status.add("proses");
        mApiService.getOrder(id_restoran,status).enqueue(new Callback<ResponseOrder>() {
            @Override
            public void onResponse(Call<ResponseOrder> call, Response<ResponseOrder> response) {
                orderList = response.body().getData();
                String value = response.body().getValue();
                String messageStr = response.body().getMessage();
                if(value.equals("1"))
                {
                    progressDialog.dismiss();
                    orderAdapter = new OrderAdapter(mContext,orderList);
                    recyclerView.setAdapter(orderAdapter);
                }else {
                    progressDialog.dismiss();
                    progressDialog.dismiss();
                    message.setVisibility(View.VISIBLE);
                    icon_message.setImageResource(R.drawable.msg_checklist);
                    title_message.setText("Anda Tidak Memiliki Pesanan ");
                }


            }

            @Override
            public void onFailure(Call<ResponseOrder> call, Throwable t) {
                progressDialog.dismiss();
                message.setVisibility(View.VISIBLE);
                icon_message.setImageResource(R.drawable.msg_no_connection);
                title_message.setText("Opps..Gagal Terhubung Keserver");
                sub_title_message.setText("Priksa kembali koneksi internet  \n perangkat anda \n");
                recyclerView.setVisibility(View.GONE);
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

        switch (item.getItemId()) {
            case R.id.action_pengantaran:
                if(sessionManager.isPengantaran()){
                    HashMap<String,String> pengantaran = sessionManager.getPengantaran();
                    Intent intent = new Intent(mContext, DeliveryActivity.class);
                    intent.putExtra("pesan",pengantaran.get(SessionManager.ID_PENGANTARAN));
                    intent.putExtra("lat",pengantaran.get(SessionManager.LAT));
                    intent.putExtra("lang",pengantaran.get(SessionManager.LANG));
                    intent.putExtra("alamat",pengantaran.get(SessionManager.ALAMAT));
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(mContext,"Anda Tidak Dalam Pengantaran",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.account:
                break;
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();
                sessionManager.logoutUser();
                Intent intent = new Intent(mContext, SignInActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }
    
    
    
}
