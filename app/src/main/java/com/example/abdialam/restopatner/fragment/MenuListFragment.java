package com.example.abdialam.restopatner.fragment;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.activities.resto.AddMenuActivity;
import com.example.abdialam.restopatner.adapter.MenuAdapter;
import com.example.abdialam.restopatner.models.Kategori;
import com.example.abdialam.restopatner.models.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuListFragment extends Fragment {

    private List<Menu> menuList = new ArrayList<>();
    private List<Kategori> kategoriList = new ArrayList<>();
    private List<Menu> menuListTemp = new ArrayList<>();
    private RecyclerView recyclerView;
    private MenuAdapter adapter;
    int position;

    Kategori kategori[];
    public static MenuListFragment newInstance()
    {
        return new MenuListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_list,container,false);
        initviews(view);
        return view;
    }

    private void initviews(View view) {

        menuList = (List<Menu>) getArguments().getSerializable("menu");
        kategoriList = (List<Kategori>) getArguments().getSerializable("kategori");
        position = getArguments().getInt("position");
        Button mAddMenu = view.findViewById(R.id.btnAddMenu);

        recyclerView =(RecyclerView) view.findViewById(R.id.my_recycler_view);

        kategori = new Kategori[kategoriList.size()];
        setByKategori();


        mAddMenu.setText(String.valueOf("+ "+kategori[getArguments().getInt("position")].getKategoriNama().toString()));
        adapter = new MenuAdapter(getActivity(),menuListTemp);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        mAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Kode "+kategori[position].getIdKategori().toString(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), AddMenuActivity.class);
                intent.putExtra("id_kategori",kategori[position].getIdKategori().toString());
                startActivity(intent);
            }
        });

    }


    public void setByKategori(){


        for (int i = 0; i < kategoriList.size() ; i++) {

            kategori[i] = kategoriList.get(i);
            if(position == i){
                for (int j = 0; j < menuList.size(); j++) {
                    if (menuList.get(j).getMenuKategoriId().toString().equals(kategoriList.get(i).getIdKategori().toString())){
                        menuListTemp.add(menuList.get(j));
                    }
                }
            }
        }



    }
}
