package com.example.abdialam.restopatner.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.adapter.OrderAdapter;
import com.example.abdialam.restopatner.config.ServerConfig;
import com.example.abdialam.restopatner.models.Pesan;
import com.example.abdialam.restopatner.responses.ResponseGetOrder;
import com.example.abdialam.restopatner.rest.ApiService;
import com.example.abdialam.restopatner.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryListFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Pesan> orderList = new ArrayList<>();
    ApiService mApiService;
    Context mContext;
    String id_restoran;
    HashMap<String,String> user;
    SessionManager sessionManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_list_view,container,false);
        mApiService = ServerConfig.getAPIService();
        mContext = getActivity();
        sessionManager = new SessionManager(mContext);
        user =sessionManager.getRestoDetail();
        setDAta();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        return view;
    }

    public void setDAta(){
        id_restoran = user.get(SessionManager.ID_RESTORAN.toString());
        Toast.makeText(mContext,"Nama "+user.get(SessionManager.NAMA_RESTORAN.toString()),Toast.LENGTH_SHORT).show();
        mApiService.getOrder(id_restoran,"pengantaran").enqueue(new Callback<ResponseGetOrder>() {
            @Override
            public void onResponse(Call<ResponseGetOrder> call, Response<ResponseGetOrder> response) {

                orderList = response.body().getPesan();
                String value = response.body().getValue();
                String message = response.body().getMessage();
                if(value.equals("1")) {
                    orderAdapter = new OrderAdapter(mContext, orderList);
                    recyclerView.setAdapter(orderAdapter);
                }else {
                    Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseGetOrder> call, Throwable t) {
                Toast.makeText(mContext,R.string.loss_connect,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
