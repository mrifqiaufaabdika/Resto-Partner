package com.example.abdialam.restopatner.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.adapter.KategoriTabAdapter;
import com.example.abdialam.restopatner.config.ServerConfig;
import com.example.abdialam.restopatner.models.Kategori;
import com.example.abdialam.restopatner.models.Menu;
import com.example.abdialam.restopatner.responses.ResponseMenuKategori;
import com.example.abdialam.restopatner.rest.ApiService;
import com.example.abdialam.restopatner.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment{

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ApiService mApiService;
    private List<Kategori> kategoriList = new ArrayList<>();
    private List<Menu> menuList = new ArrayList<>();
    Context mContext;
    HashMap<String,String> user;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu,container,false);
        mContext = getActivity();
        mApiService = ServerConfig.getAPIService();
        sessionManager = new SessionManager(mContext);
        user =sessionManager.getRestoDetail();
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager.setOffscreenPageLimit(5);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        setValue(user.get(SessionManager.ID_RESTORAN.toString()));
        initViews();


        return view;
    }

    private void initViews() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setDynamicFragmentToTabLayout();

    }

    private void setDynamicFragmentToTabLayout() {
        for (int i = 0; i < kategoriList.size() ; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(kategoriList.get(i).getKategoriNama().toString()));
        }

        KategoriTabAdapter kategoriTabAdapter = new KategoriTabAdapter(getFragmentManager(),tabLayout.getTabCount(),menuList,kategoriList);
        viewPager.setAdapter(kategoriTabAdapter);
        viewPager.setCurrentItem(0);

    }

    private void setValue(String id_restoran){
        mApiService.getMenuKategori(id_restoran).enqueue(new Callback<ResponseMenuKategori>() {
            @Override
            public void onResponse(Call<ResponseMenuKategori> call, Response<ResponseMenuKategori> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals("success")){
                        menuList = response.body().getMenu();
                        kategoriList = response.body().getKategori();
                        initViews();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMenuKategori> call, Throwable t) {

            }
        });

    }
}
