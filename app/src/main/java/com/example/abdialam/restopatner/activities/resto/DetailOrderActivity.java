package com.example.abdialam.restopatner.activities.resto;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.activities.DeliveryActivity;
import com.example.abdialam.restopatner.adapter.DetailOrderAdapter;
import com.example.abdialam.restopatner.config.ServerConfig;
import com.example.abdialam.restopatner.models.Detailorder;
import com.example.abdialam.restopatner.models.Pesan;
import com.example.abdialam.restopatner.rest.ApiService;
import com.example.abdialam.restopatner.utils.NonScrollListView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailOrderActivity extends AppCompatActivity{

    private List<Pesan> pesanList = new ArrayList<>();
    private List<Detailorder> detailOrders = new ArrayList<>();
    private DetailOrderAdapter orderAdapter;
    private Pesan pesan;
    ApiService mApiService;
    Context mContext ;

    @BindView(R.id.listview)
    NonScrollListView list;
    @BindView(R.id.sbDelivery)
    SwipeButton mSwipeDelivery;
    @BindView(R.id.tvNamaKonsumen)
    TextView mNamaKonsumen;
    @BindView(R.id.tvPhoneKonsumen)
    TextView mPhoneKonsumen;
    @BindView(R.id.tvAlamatAntar)
    TextView mAlamat;
    @BindView(R.id.tvSubTotal)
    TextView mSubTotal;
    @BindView(R.id.tvBiayaAntar)
    TextView mBiayaAntar;
    @BindView(R.id.tvTotal)
    TextView mTotal;
    @BindView(R.id.btnTelponKonsumen)
    ImageView mTelponKonsumen;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        ButterKnife.bind(this);
        mApiService = ServerConfig.getAPIService();
        mContext = this;
        setListViewHeightBasedOnChildren(list);
        getIncomingIntent();
        orderAdapter = new DetailOrderAdapter(DetailOrderActivity.this,detailOrders);
        list.setAdapter(orderAdapter);

        setData();


        mSwipeDelivery.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                Toast.makeText(mContext, "Status = "+active,Toast.LENGTH_SHORT).show();
                if(active){


                    Intent intent = new Intent(mContext, DeliveryActivity.class);
                    intent.putExtra("pesan",pesan);
                    startActivity(intent);
                }
            }
        });



    }

    private void setData() {
        mNamaKonsumen.setText(pesan.getKonsumenNama());
        mPhoneKonsumen.setText(pesan.getKonsumenPhone());
        mAlamat.setText(pesan.getPesanAlamat());
         Double subtotal = 0.0;
        for (int i = 0; i <  detailOrders.size(); i++) {
            subtotal += Double.parseDouble(detailOrders.get(i).getHarga())*detailOrders.get(i).getQty();
        }
        mSubTotal.setText(kursIndonesia(subtotal));
        mBiayaAntar.setText(kursIndonesia(Double.parseDouble(pesan.getPesanBiayaAntar())));
        double total = subtotal+ Double.parseDouble(pesan.getPesanBiayaAntar());
        mTotal.setText(kursIndonesia(total));

    }

//get inten comming
    private void getIncomingIntent (){

        if(getIntent().hasExtra("pesan") && getIntent().hasExtra("position")){

            pesanList = (List<Pesan>) getIntent().getSerializableExtra("pesan");
            int position = getIntent().getIntExtra("position",0);
            pesan = pesanList.get(position);
            detailOrders = pesan.getDetailorder();

        }
    }


    @OnClick(R.id.btnTelponKonsumen) void telponKonsumen(){
        String numberPhone = "tel:"+"+"+pesan.getKonsumenPhone().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(numberPhone));
        startActivity(intent);
    }












//listview not scrolll
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }



//konfersi ke mata uang rupiah
    public String kursIndonesia(double nominal){
        Locale localeID = new Locale("in","ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String idnNominal = formatRupiah.format(nominal);
        return idnNominal;


    }

}
