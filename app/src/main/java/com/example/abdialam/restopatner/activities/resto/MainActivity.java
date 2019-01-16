package com.example.abdialam.restopatner.activities.resto;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.activities.WelcomeActivity;
import com.example.abdialam.restopatner.config.ServerConfig;
import com.example.abdialam.restopatner.fragment.AccountFragment;
import com.example.abdialam.restopatner.fragment.LaporanFragment;
import com.example.abdialam.restopatner.fragment.MenuFragment;
import com.example.abdialam.restopatner.fragment.OrderFragment;
import com.example.abdialam.restopatner.fragment.RiwayatFragment;
import com.example.abdialam.restopatner.responses.ResponseValue;
import com.example.abdialam.restopatner.rest.ApiService;
import com.example.abdialam.restopatner.utils.SessionManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {



    Context mContext;
    SessionManager sessionManager;
    HashMap<String,String> sessionRestoran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        sessionManager = new SessionManager(mContext);
        sessionRestoran = sessionManager.getRestoDetail();

        if(sessionManager.isRestoran()) {
            cekLocation();
        }


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (getIntent().hasExtra("menu")){
            loadFragment(new MenuFragment());
            navigation.setSelectedItemId(R.id.action_menu);
        }else {
            loadFragment(new OrderFragment());
        }


//        getSupportActionBar().setElevation(0);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.action_order:
                    fragment = new OrderFragment();
          //          getSupportActionBar().setTitle("Orders");
            //        getSupportActionBar().setElevation(0);
                    break;
                case R.id.action_menu:
                    fragment = new MenuFragment();
//                    getSupportActionBar().setTitle("Menu");
//                    getSupportActionBar().setElevation(0);
                    break;
                case R.id.action_laporan:
                    fragment = new LaporanFragment();
//                    getSupportActionBar().setTitle("Menu");
//                    getSupportActionBar().setElevation(0);
                    break;
                case R.id.action_riwayat:
                    fragment=  new RiwayatFragment();
//                    getSupportActionBar().setTitle("Riwayat");
                    break;
                case R.id.action_akun:
                    fragment = new AccountFragment();
                    break;
            }
            return loadFragment(fragment);
        }
    };

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    private void cekLocation() {
        if(sessionRestoran.get(SessionManager.RESTORAN_LAT)== null||sessionRestoran.get(SessionManager.RESTORAN_LAT)== null){
            Intent intent = new Intent(mContext, MapsActivity.class);
            //    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        }
    }









}
