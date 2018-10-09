package com.example.abdialam.restopatner.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.abdialam.restopatner.fragment.MenuFragment;
import com.example.abdialam.restopatner.fragment.MenuListFragment;
import com.example.abdialam.restopatner.models.Kategori;
import com.example.abdialam.restopatner.models.Menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KategoriTabAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private List<Menu> menuList = new ArrayList<>();
    private List<Kategori> kategoriList = new ArrayList<>();

    public KategoriTabAdapter(FragmentManager fm,int NumOfTabs, List<Menu> menuList,List <Kategori> kategoriList) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.menuList = menuList;
        this.kategoriList = kategoriList;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putInt("position", position);
        b.putSerializable("menu", (Serializable) menuList);
        b.putSerializable("kategori",(Serializable) kategoriList);
        Fragment frag = MenuListFragment.newInstance();
        frag.setArguments(b);
        return frag;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
