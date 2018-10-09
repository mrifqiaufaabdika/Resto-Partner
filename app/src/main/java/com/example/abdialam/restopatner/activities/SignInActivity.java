package com.example.abdialam.restopatner.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.activities.VerifyActifity;
import com.example.abdialam.restopatner.activities.kurir.MainKurirActivity;
import com.example.abdialam.restopatner.config.ServerConfig;
import com.example.abdialam.restopatner.models.Kurir;
import com.example.abdialam.restopatner.models.Restoran;
import com.example.abdialam.restopatner.responses.ResponseAuth;
import com.example.abdialam.restopatner.responses.ResponseSignIn;
import com.example.abdialam.restopatner.rest.ApiService;
import com.example.abdialam.restopatner.utils.SharedPrefManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    Context mContext;
    ApiService mApiService ;
    @BindView(R.id.etPhone)
    EditText mPhone ;
    @BindView(R.id.btnDisplayToken)Button mDisplayToken;
    @BindView(R.id.token)
    TextView mToken;
    String strPhone,value,message,tipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mApiService = ServerConfig.getAPIService();
        mContext = this;
        ButterKnife.bind(this);


    }

    @OnClick (R.id.btnSendCode) void getCode (){

        strPhone = clearPhone(mPhone.getText().toString());
        mApiService.signinRequest(strPhone).enqueue(new Callback<ResponseSignIn>() {
            @Override
            public void onResponse(Call<ResponseSignIn> call, Response<ResponseSignIn> response) {
                if(response.isSuccessful()){

                    value = response.body().getValue();
                    message = response.body().getMessage();
                    tipe = response.body().getTipe();
                    if(value.equals("1")){
                        Intent intent = new Intent(mContext,VerifyActifity.class);
                        if(tipe.equals("restoran")){
                            Restoran resto = response.body().getRestoran();
                            intent.putExtra("resto",resto);
                        }else if(tipe.equals("kurir")){
                            Kurir kurir = response.body().getKurir();
                            intent.putExtra("kurir",kurir);
                        }
                        startActivity(intent);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NO_HISTORY);
                        finish();
                    }else {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(mContext, "gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSignIn> call, Throwable t) {
                Toast.makeText(mContext,R.string.loss_connect, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.btnDisplayToken) void viewToken () {
        String Token = SharedPrefManager.getInstance(this).getDeviceToken();
        mToken.setText(Token);
    }

    public String clearPhone (String phoneNumber){
        String hp = phoneNumber.replaceAll("-","");
        String clearPhone = hp.substring(1,hp.length());
        return clearPhone;
    }
}
