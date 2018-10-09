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
import com.example.abdialam.restopatner.activities.resto.DetailOrderActivity;
import com.example.abdialam.restopatner.models.Detailorder;
import com.example.abdialam.restopatner.models.Pesan;
import com.example.abdialam.restopatner.utils.DateHelper;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Pesan> orderList;
    private Context mContext;
    private List<Detailorder> detailorderList;

    public OrderAdapter(Context context,List<Pesan> orders){
        this.mContext = context;
        this.orderList = orders;
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_order_list,parent,false);
        OrderViewHolder holder = new OrderViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, final int position) {
        final Pesan order = orderList.get(position);
        detailorderList = orderList.get(position).getDetailorder();
        String pesan ="";
        String tanda = ", ";
        for (int i = 0; i < detailorderList.size() ; i++) {
            if(i == detailorderList.size()-1){
                tanda =".";
            }
            pesan += detailorderList.get(i).getMenuNama()+" "+detailorderList.get(i).getQty()+tanda;
        }

        holder.mNamaPemesan.setText(order.getKonsumenNama());
        holder.mAlamat.setText(order.getPesanAlamat());
        holder.mDaftarPesan.setText(pesan);
        String strTimeOrder = order.getPesanWaktu();
        long longDate = timeStringToMilis(strTimeOrder);
        holder.mTime.setText(DateHelper.getGridDate(mContext,longDate));
        holder.mIdPesan.setText("#"+order.getIdPesan().toString());

        holder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Anda memilih "+order.getKonsumenNama(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, DetailOrderActivity.class);
                intent.putExtra("pesan", (Serializable) orderList);
                intent.putExtra("position",position);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
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


        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

//convert time string to milis date
    public long timeStringToMilis (String strDate ){

        SimpleDateFormat tgl = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long milliseconds = 0;
        Date date = null;
        try {
            date = tgl.parse(strDate);
            milliseconds = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;

    }
}
