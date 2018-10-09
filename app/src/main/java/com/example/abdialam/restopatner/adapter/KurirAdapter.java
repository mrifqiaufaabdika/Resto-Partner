package com.example.abdialam.restopatner.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.models.Kurir;
import com.example.abdialam.restopatner.rest.ApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KurirAdapter extends RecyclerView.Adapter<KurirAdapter.MyViewHolder> {

   private List<Kurir> kurirList = new ArrayList<>();
   private Context mContext;
   FragmentManager fragmentManager;


   public KurirAdapter(Context context,List<Kurir> data){
       super();
       this.kurirList = data;
       this.mContext = context;
   }


    @NonNull
    @Override
    public KurirAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_kurir_list, parent, false);
        KurirAdapter.MyViewHolder holder = new KurirAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull KurirAdapter.MyViewHolder holder, int position) {
            final Kurir kurir = kurirList.get(position);
            holder.mNamaKurir.setText(kurir.getKurirNama());
            holder.mPhoneKurir.setText("+"+kurir.getKurirPhone());
            holder.mEmailKurir.setText(kurir.getKurirEmail());


    }

    @Override
    public int getItemCount() {
        return kurirList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       @BindView(R.id.tvNamaKurir)
        TextView mNamaKurir;
       @BindView(R.id.tvPhoneKurir)
       TextView mPhoneKurir;
       @BindView(R.id.tvEmailKurir)
       TextView mEmailKurir;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
