package com.example.abdialam.restopatner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.activities.DeliveryActivity;
import com.example.abdialam.restopatner.activities.resto.DetailOrderActivity;
import com.example.abdialam.restopatner.models.Menu;
import com.example.abdialam.restopatner.models.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.MyViewHolder> {

    Context mContext;
    List<Order> pesanList = new ArrayList<>();
    List<Menu> detailorderList = new ArrayList<>();

    public DeliveryAdapter (Context context,List<Order> data){
        this.mContext = context;
        this.pesanList = data;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_order_list,parent,false);
        DeliveryAdapter.MyViewHolder holder = new DeliveryAdapter.MyViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Order order = pesanList.get(position);
        detailorderList = pesanList.get(position).getDetailOrder();
        String pesan ="";
        String tanda = ", ";
        for (int i = 0; i < detailorderList.size() ; i++) {
            if(i == detailorderList.size()-1){
                tanda =".";
            }
            pesan += "("+detailorderList.get(i).getPivot().getQty()+") "+detailorderList.get(i).getMenuNama()+tanda;
        }

        holder.mNamaPemesan.setText(order.getOrderKonsumen());
        holder.mAlamat.setText(order.getOrderAlamat());
        holder.mDaftarPesan.setText(pesan);
        holder.mTime.setText(satuan_jarak(order.getOrderJarakAntar().toString()));
        holder.mIdPesan.setText("#"+order.getId().toString());

        holder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Anda memilih "+order.getOrderKonsumen(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, DeliveryActivity.class);
                intent.putExtra("pesan",order.getId().toString());
                intent.putExtra("lat",order.getOrderLat().toString());
                intent.putExtra("lang",order.getOrderLong().toString());
                intent.putExtra("alamat",order.getOrderAlamat().toString());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pesanList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNamaPemesan)
        TextView mNamaPemesan;
        @BindView(R.id.tvDaftarPesan)
        TextView mDaftarPesan;
        @BindView(R.id.tvAlamat)
        TextView mAlamat;
        @BindView(R.id.tvTime)
        TextView mTime;
        @BindView(R.id.tvIDpesan)
        TextView mIdPesan;
        @BindView(R.id.parentLayout)RelativeLayout mParentLayout;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private String satuan_jarak(String jarak){
        String jarakStr = null;
        if(Double.parseDouble(jarak) < 1) {
            jarakStr = jarak + " m";
        }else{
            jarakStr = jarak + " Km";
        }
        return jarakStr;
    }
}
