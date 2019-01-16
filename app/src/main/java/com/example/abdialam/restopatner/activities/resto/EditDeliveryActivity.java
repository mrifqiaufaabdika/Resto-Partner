package com.example.abdialam.restopatner.activities.resto;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.config.ServerConfig;
import com.example.abdialam.restopatner.models.Restoran;
import com.example.abdialam.restopatner.responses.ResponseValue;
import com.example.abdialam.restopatner.rest.ApiService;
import com.example.abdialam.restopatner.utils.SessionManager;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditDeliveryActivity extends AppCompatActivity{

    Context mContext;
    ApiService mApiService;
    Restoran restoran;
    SessionManager sessionManager;
    HashMap<String,String> user;

    String metodeBayar ;
    private String[] metode = {
            "gratis",
            "flat"
    };

    @BindView(R.id.etTarifDelivery)
    EditText mTarifDelivery;
    @BindView(R.id.etMinimmumOrder)
    EditText mMinimumOrder;
    @BindView(R.id.etJarakMax)
    EditText mJarakMax;
    @BindView(R.id.metodeBayar)
    Spinner mDelivery;
    @BindView(R.id.btnSubmit)
    Button mSubmit;
    @BindView(R.id.rootLayout)
    CoordinatorLayout coordinatorLayout;

     ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delivery);
        getSupportActionBar().setTitle("Data Delivery");
        restoran = (Restoran) getIntent().getSerializableExtra("restoran");
        mContext = this;
        ButterKnife.bind(this);
        mApiService = ServerConfig.getAPIService();
        sessionManager = new SessionManager(this);
        user = sessionManager.getRestoDetail();

        mDelivery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectSpiner(adapter.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        init();
    }


    public void init(){
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, metode);
        metodeBayar = restoran.getRestoranDelivery();
        mDelivery.setAdapter(adapter);
        if (metodeBayar != null) {
            int spinnerPosition = adapter.getPosition(metodeBayar);
            mDelivery.setSelection(spinnerPosition);
        }
        if(metodeBayar.equals("gratis")){
            mTarifDelivery.setVisibility(View.GONE);
            mTarifDelivery.setText("0");
        }else {
            mTarifDelivery.setText((restoran.getRestoranDeliveryTarif()));
        }
        mJarakMax.setText(restoran.getRestoranDeliveryJarak().toString());
        mMinimumOrder.setText((restoran.getRestoranDeliveryMinimum().toString()));
    }

    @OnClick(R.id.btnSubmit) void submit (){
        final ProgressDialog progressDialog = ProgressDialog.show(this, null, getString(R.string.memuat), true, false);

        final String id_restoran = user.get(SessionManager.ID_RESTORAN);
        final String minimumOreder = mMinimumOrder.getText().toString();
        final String jarakMax = mJarakMax.getText().toString();
        final String tarifDelivery = mTarifDelivery.getText().toString();


        if(minimumOreder.isEmpty()||minimumOreder.equals(null)) {
            progressDialog.dismiss();
            mMinimumOrder.setError("Field Tidak Boleh Kosong");
            mMinimumOrder.requestFocus();
            return;
        }else if (jarakMax.isEmpty()||jarakMax.equals(null)) {
            progressDialog.dismiss();
            mJarakMax.setError("Field Tidak Boleh Kosong");
            mJarakMax.requestFocus();
            return;
        }else if (tarifDelivery.isEmpty()||tarifDelivery.equals(null)) {
            progressDialog.dismiss();
            mTarifDelivery.setError("Field Tidak Boleh Kosong");
            mTarifDelivery.requestFocus();
            return;
        }else {
            Toast.makeText(this,metodeBayar+","+tarifDelivery+","+minimumOreder+","+jarakMax,Toast.LENGTH_LONG).show();
            mApiService.editDelivery(id_restoran,metodeBayar,tarifDelivery,jarakMax,minimumOreder).enqueue(new Callback<ResponseValue>() {
                @Override
                public void onResponse(Call<ResponseValue> call, Response<ResponseValue> response) {
                    progressDialog.dismiss();
                    if(response.isSuccessful()){

                        if(response.body().getValue().equals("1")){
                            Snackbar.make(coordinatorLayout,"Berhasil Edit", Snackbar.LENGTH_SHORT).show();
                            sessionManager.updateDelivery(metodeBayar,tarifDelivery,jarakMax,minimumOreder);
                        }else {
                            Snackbar.make(coordinatorLayout,"Gagal Edit", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseValue> call, Throwable t) {
                    progressDialog.dismiss();
                    Snackbar.make(coordinatorLayout,"Periksa Konseksi Anda", Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }


    //konfersi ke mata uang rupiah
    public String kursIndonesia(double nominal){
        Locale localeID = new Locale("in","ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String idnNominal = formatRupiah.format(nominal);
        return idnNominal;
    }


    private void selectSpiner(String item) {
        if(item.equals("gratis")){
            mTarifDelivery.setVisibility(View.GONE);
            mTarifDelivery.setText("0");
            metodeBayar = "gratis";
        }else if(item.equals("flat")){
            mTarifDelivery.setVisibility(View.VISIBLE);

            metodeBayar = "flat";
        }


    }
}
