package com.example.abdialam.restopatner.activities.resto;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.abdialam.restopatner.models.Menu;
import com.example.abdialam.restopatner.models.Order;
import com.example.abdialam.restopatner.responses.ResponseValue;
import com.example.abdialam.restopatner.rest.ApiService;
import com.example.abdialam.restopatner.utils.NonScrollListView;
import com.example.abdialam.restopatner.utils.SessionManager;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrderActivity extends AppCompatActivity{


    private List<Menu> detailOrders = new ArrayList<>();
    private DetailOrderAdapter orderAdapter;
    private Order pesan;
    ApiService mApiService;
    Context mContext ;
    ProgressDialog progressOrder;
    String id_order;

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
    @BindView(R.id.tvMetodeBayar)
    TextView mMetodeBayar;
    @BindView(R.id.btnTelponKonsumen)
    ImageView mTelponKonsumen;
    @BindView(R.id.btnCancel)
    TextView mCancel;
    @BindView(R.id.layoutCatatan)
    LinearLayout layoutCatatn;
    @BindView(R.id.catatan)
    TextView mCatatan;
    @BindView(R.id.tvPengantaran)
    TextView tvPengantaran;
    @BindView(R.id.layoutStatus)
    LinearLayout layoutStatus;

    SessionManager sessionManager;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        ButterKnife.bind(this);
        mContext = this;
        sessionManager = new SessionManager(mContext);
        mApiService = ServerConfig.getAPIService();
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

                    progressDialog = ProgressDialog.show(mContext,null,getString(R.string.memuat),true,true);
                    mApiService.updateStatusPesan(pesan.getId().toString(),"pengantaran").enqueue(new Callback<ResponseValue>() {
                        @Override
                        public void onResponse(Call<ResponseValue> call, Response<ResponseValue> response) {
                            if(response.isSuccessful()){
                                String value = response.body().getValue();
                                String message = response.body().getMessage();
                                if(value.equals("1")){
                                    progressDialog.dismiss();
                                    Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(mContext, DeliveryActivity.class);
                                    intent.putExtra("pesan",pesan.getId().toString());
                                    intent.putExtra("lat",pesan.getOrderLat().toString());
                                    intent.putExtra("lang",pesan.getOrderLong().toString());
                                    intent.putExtra("alamat",pesan.getOrderAlamat().toString());
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    sessionManager.pengantaran(pesan.getId().toString(),pesan.getOrderLat().toString(),pesan.getOrderLong().toString(),pesan.getOrderAlamat().toString());
                                    startActivity(intent);
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
                                    mSwipeDelivery.setActivated(false);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseValue> call, Throwable t) {
                                progressDialog.dismiss();
                                mSwipeDelivery.setActivated(false);
                                Toast.makeText(mContext,R.string.loss_connect,Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertBatalPesanan();
            }
        });


    }



    private void setData() {
        id_order = pesan.getId().toString();
        mNamaKonsumen.setText(pesan.getOrderKonsumen());
        mPhoneKonsumen.setText("+"+pesan.getOrderKonsumenPhone());
        mAlamat.setText(pesan.getOrderAlamat());
         Double subtotal = 0.0;
        for (int i = 0; i <  detailOrders.size(); i++) {
            if(detailOrders.get(i).getMenuDiscount() == 0 ||detailOrders.get(i).getMenuDiscount().toString().isEmpty()) {
                subtotal += Double.parseDouble(detailOrders.get(i).getPivot().getHarga()) * detailOrders.get(i).getPivot().getQty();
            }else {
                Double harga_discount = HitungDiscount(Double.parseDouble(detailOrders.get(i).getPivot().getHarga()),detailOrders.get(i).getPivot().getDiscount());
                subtotal += harga_discount * detailOrders.get(i).getPivot().getQty();
            }
        }
        mSubTotal.setText(kursIndonesia(subtotal));
        mBiayaAntar.setText(kursIndonesia(Double.parseDouble(pesan.getOrderBiayaAnatar())));
        double total = subtotal+ Double.parseDouble(pesan.getOrderBiayaAnatar());
        mTotal.setText(kursIndonesia(total));
        mMetodeBayar.setText(pesan.getOrderMetodeBayar());
        //catatan
        if(pesan.getOrderCatatan() != null){
            layoutCatatn.setVisibility(View.VISIBLE);
            mCatatan.setText(pesan.getOrderCatatan());

        }

        if(sessionManager.isPengantaran()) {
            Toast.makeText(mContext,""+sessionManager.isPengantaran(),Toast.LENGTH_SHORT).show();
            mSwipeDelivery.setVisibility(View.GONE);
            tvPengantaran.setVisibility(View.VISIBLE);
        }

        if (!sessionManager.isRestoran()){
            mCancel.setVisibility(View.GONE);
        }

        layoutStatus.setVisibility(View.GONE);

    }

//get inten comming
    private void getIncomingIntent (){

        if(getIntent().hasExtra("pesan")){


            pesan = (Order) getIntent().getSerializableExtra("pesan");
            detailOrders = pesan.getDetailOrder();

        }
    }


    @OnClick(R.id.btnTelponKonsumen) void telponKonsumen(){
        String numberPhone = "tel:"+"+"+pesan.getOrderKonsumenPhone().toString();
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

    public Double HitungDiscount (Double Harga,Integer Discount){
        double harga_potongan = ((Discount/100.00)*Harga);
        return Harga-harga_potongan;
    }

    private void alertBatalPesanan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Batalkan Peasanan");
        builder.setMessage("Apakah Anda yakin akan membatalkan pesanan ini ? ");
        builder.setCancelable(false);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //send data to server
                progressOrder = new ProgressDialog(mContext);
                progressOrder = ProgressDialog.show(mContext,null,getString(R.string.memuat),true,false);
                cancelOrder();
                // intentSucess();

            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });


        final AlertDialog alert =builder.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
            }
        });
        alert.show();

    }

    private void cancelOrder() {
        mApiService.updateStatusPesan(id_order,"batal").enqueue(new Callback<ResponseValue>() {
            @Override
            public void onResponse(Call<ResponseValue> call, Response<ResponseValue> response) {
                progressOrder.dismiss();
                if(response.isSuccessful()){
                    if(response.body().getValue().equals("1")){
                        Intent intent = new Intent(mContext,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseValue> call, Throwable t) {
                progressOrder.dismiss();

            }
        });
    }


}
