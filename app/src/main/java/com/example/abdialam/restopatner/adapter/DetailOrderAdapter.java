package com.example.abdialam.restopatner.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.models.Detailorder;
import com.example.abdialam.restopatner.models.Pesan;

import java.util.List;

public class DetailOrderAdapter extends BaseAdapter{

    Context mContext;
    List<Detailorder> detailOrders ;
    ViewHolder viewHolder;



    public DetailOrderAdapter (Context context, List<Detailorder> data){
        super();
        this.mContext = context;
        this.detailOrders = data;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.single_row_detail_order_list,null);

            viewHolder = new ViewHolder();

            viewHolder.mNamaMenu = (TextView)view.findViewById(R.id.tvNamaMenu);
            viewHolder.mQty = (TextView)view.findViewById(R.id.tvQty);
            viewHolder.catatan =(TextView) view.findViewById(R.id.tvCatatan);
//            viewHolder.harga = (TextView)view.findViewById(R.id.harga);

            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();
        }


        final Detailorder order = (Detailorder) getItem(position);
        viewHolder.mNamaMenu.setText(order.getMenuNama());
        viewHolder.mQty.setText(String.valueOf(order.getQty()));
//        viewHolder.qty.setText(String .valueOf(cart.getQty()));
        if(order.getCatatan().isEmpty()){
            viewHolder.catatan.setVisibility(View.GONE);
        }else {
            viewHolder.catatan.setVisibility(View.VISIBLE);
            viewHolder.catatan.setText(order.getCatatan());
        }

        return view;
    }

    @Override
    public int getCount() {
        return detailOrders.size();
    }

    @Override
    public Object getItem(int i) {
        return detailOrders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    private class ViewHolder{
        TextView mNamaMenu;
        TextView mQty;
        TextView mJml;
        TextView catatan;

    }
}
