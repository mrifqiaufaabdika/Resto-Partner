package com.example.abdialam.restopatner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.config.ServerConfig;
import com.example.abdialam.restopatner.models.Menu;
import com.example.abdialam.restopatner.responses.ResponseValue;
import com.example.abdialam.restopatner.rest.ApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder>{

    private List<Menu> menuList = new ArrayList<>();
    private Context mContext;
    FragmentManager fragmentManager;
    ApiService mApiService;

    public MenuAdapter (Context context, List<Menu> data){
        super();
        this.menuList = data;
        this.mContext = context;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_list_menu, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            final Menu menu = menuList.get(position);
            holder.mNamaMenu.setText(menu.getMenuNama());
            holder.mHargaMenu.setText(menu.getMenuHarga());



            if(menu.getFavorit() > 0) {
                holder.mFavorit.setText(menu.getFavorit().toString());
                holder.imgFavorit.setVisibility(View.VISIBLE);
            }

            int ketersedian = menu.getMenuKetersedian();
            if (ketersedian ==1) {
                holder.mSwitchKetersedian.setChecked(true);
            }else{
                holder.mSwitchKetersedian.setChecked(false);
            }

            holder.mSwitchKetersedian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        Toast.makeText(mContext,"Menu Tersedia",Toast.LENGTH_SHORT).show();
                        setKetersedian(menu.getIdMenu().toString(),b);
                    }else {
                        Toast.makeText(mContext,"Menu Tidak Tersedia",Toast.LENGTH_SHORT).show();
                        setKetersedian(menu.getIdMenu().toString(),b);
                    }
                }
            });


            holder.mParentlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"Menu "+menu.getMenuNama(),Toast.LENGTH_SHORT).show();
                }
            });

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvNamaMenu)
        TextView mNamaMenu ;
        @BindView(R.id.tvHargaMenu)
        TextView mHargaMenu;
        @BindView(R.id.tvFavorit)
        TextView mFavorit;
        @BindView(R.id.imgFavorit)
        ImageView imgFavorit;
        @BindView(R.id.swKetersediaan)
        Switch mSwitchKetersedian;
        @BindView(R.id.imageMenu)
        ImageView mImageMenu;
        @BindView(R.id.parentLayout)
        LinearLayout mParentlayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mApiService = ServerConfig.getAPIService();
        }
    }

    public void setKetersedian(String id_menu,boolean ketersedian){
        int intKetersedian = 0;
        if(ketersedian){
            intKetersedian = 1;
        }else {
            intKetersedian = 0;
        }

        mApiService.setKetersedianMenu(id_menu, intKetersedian).enqueue(new Callback<ResponseValue>() {
            @Override
            public void onResponse(Call<ResponseValue> call, Response<ResponseValue> response) {
                if(response.isSuccessful()){
                    String mesage = response.body().getMessage();
                    if(mesage.equals("1")){
                        Toast.makeText(mContext,mesage,Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(mContext,mesage,Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseValue> call, Throwable t) {
                Toast.makeText(mContext,R.string.loss_connect,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
