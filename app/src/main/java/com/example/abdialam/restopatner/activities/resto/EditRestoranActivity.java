package com.example.abdialam.restopatner.activities.resto;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.config.ServerConfig;
import com.example.abdialam.restopatner.models.Restoran;
import com.example.abdialam.restopatner.responses.ResponseValue;
import com.example.abdialam.restopatner.rest.ApiService;
import com.example.abdialam.restopatner.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRestoranActivity extends AppCompatActivity {

    Context mContext;
    ApiService mApiService;
    Restoran restoran;

    @BindView(R.id.etNama)
    EditText etNama;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etAlamat)
    EditText etAlamat;
    @BindView(R.id.btn_edit)
    TextView btnEdit;
    @BindView(R.id.etDeskripsi)
    EditText etDiskripsi;
    @BindView(R.id.rootLayout)
    CoordinatorLayout coordinatorLayout;

    SessionManager sessionManager;

    ProgressDialog progressDialog;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restoran);
        getSupportActionBar().setTitle("Data Usaha");
        restoran = (Restoran) getIntent().getSerializableExtra("restoran");
        mContext = this;
        ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        mApiService = ServerConfig.getAPIService();
        init();
    }


    public void init(){
        etNama.setText(restoran.getRestoranNama());
        etPhone.setText(restoran.getRestoranPhone());
        etEmail.setText(restoran.getRestoranEmail());
        etAlamat.setText(restoran.getRestoranAlamat());
        etDiskripsi.setText(restoran.getRestoranDeskripsi());

    }


    @OnClick(R.id.btn_edit) void ubah(){
        progressDialog = ProgressDialog.show(this,null,getString(R.string.memuat),true,false);

        final String nama = etNama.getText().toString();
        final String email = etEmail.getText().toString();
        final String phone = etPhone.getText().toString();
        final String alamat =etAlamat.getText().toString();
        final String diskripsi =etDiskripsi.getText().toString();

        if(nama.isEmpty()||nama.equals(null)) {
            progressDialog.dismiss();
            etNama.setError("Field Tidak Boleh Kosong");
            etNama.requestFocus();
            return;
        }else if (email.isEmpty()||email.equals(null)) {
            progressDialog.dismiss();
            etEmail.setError("Field Tidak Boleh Kosong");
            etEmail.requestFocus();
            return;
        }else if (phone.isEmpty()||phone.equals(null)) {
            progressDialog.dismiss();
            etPhone.setError("Field Tidak Boleh Kosong");
            etPhone.requestFocus();
            return;
        }else if (alamat.isEmpty()||alamat.equals(null)) {
            progressDialog.dismiss();
            etAlamat.setError("Field Tidak Boleh Kosong");
            etAlamat.requestFocus();
            return;
        }else if (diskripsi.isEmpty()||diskripsi.equals(null)) {
            progressDialog.dismiss();
            etDiskripsi.setError("Field Tidak Boleh Kosong");
            etDiskripsi.requestFocus();
            return;
        }else {

            mApiService.editRestoran(restoran.getId().toString(), nama, phone,email , alamat, diskripsi).enqueue(new Callback<ResponseValue>() {
                @Override
                public void onResponse(Call<ResponseValue> call, Response<ResponseValue> response) {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        if (response.body().getValue().equals("1")) {
                            Snackbar.make(coordinatorLayout, "Berhasil Edit", Snackbar.LENGTH_SHORT).show();
                            sessionManager.updateRestoran(nama, email, phone, alamat, diskripsi);

                        } else {
                            progressDialog.dismiss();
                            Snackbar.make(coordinatorLayout, "Gagal Edit", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseValue> call, Throwable t) {
                    progressDialog.dismiss();

                }
            });
        }
    }
}
