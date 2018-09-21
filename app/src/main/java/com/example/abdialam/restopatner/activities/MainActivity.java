package com.example.abdialam.restopatner.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.accessibility.AccessibilityManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.fragment.AccountFragment;
import com.example.abdialam.restopatner.fragment.MenuFragment;
import com.example.abdialam.restopatner.fragment.OrderFragment;
import com.example.abdialam.restopatner.utils.SessionManager;

public class MainActivity extends AppCompatActivity {


    SessionManager sessionManager;
    Context mContext;
    Switch aSwitchOprasional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        sessionManager = new SessionManager(this);
        checkSessionLogin();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(new OrderFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.orders:
                    fragment = new OrderFragment();
                    break;
                case R.id.menus:
                    fragment = new MenuFragment();
                    break;
                case R.id.account:
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



    private void checkSessionLogin (){
        if(!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(mContext ,WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar,menu);
        MenuItem itemSwitch = menu.findItem(R.id.switchId);
        itemSwitch.setActionView(R.layout.switch_layout);
        final Switch sw = (Switch) menu.findItem(R.id.switchId).getActionView().findViewById(R.id.switchAB);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(mContext,"ON",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mContext,"OFF",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return true;

    }
}
