package com.example.abdialam.restopatner.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.config.ServerConfig;
import com.example.abdialam.restopatner.models.Restoran;
import com.example.abdialam.restopatner.rest.ApiService;
import com.example.abdialam.restopatner.utils.SessionManager;
import com.github.irvingryan.VerifyCodeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyActifity extends AppCompatActivity {

    @BindView(R.id.editTextCode)
    VerifyCodeView editTextCode;
    @BindView(R.id.buttonSignIn)
    Button btnSigin ;

    ApiService mApiService ;


    Context mContext;
    FirebaseAuth mAuth;
    String codeSent,phone;
    ProgressDialog progressDialog;
    SessionManager sessionManager;
    Restoran resto;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        mContext = this;
        phone = getIntent().getStringExtra("phone");

        resto = new Restoran();
        mApiService = ServerConfig.getAPIService();
        sessionManager = new SessionManager(mContext);
        mAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);
        getIncomingIntent();



        Toast.makeText(mContext,phone, Toast.LENGTH_SHORT).show();
//      Memanggil method untuk mengirim code
        sendVerificationCode(phone);
    }

    @OnClick (R.id.buttonSignIn) void signin (){
        progressDialog = ProgressDialog.show(mContext,null,getString(R.string.memuat),true,false);
//        untuk melakukan verifikasi dari code OTP yang di inputkan
          verifySignInCode();
//        jika menguji login tanpa menggunakan code OTP
         //SessionUser();


    }

    private void SessionUser() {
        progressDialog.dismiss();

        Toast.makeText(getApplicationContext(),"login successfuli", Toast.LENGTH_LONG).show();
        sessionManager.createLoginSession(resto);
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
    }


    private void verifySignInCode(){
        String code =  editTextCode.getText();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //to sucses login
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(),"login successfuli", Toast.LENGTH_LONG).show();
                            sessionManager.createLoginSession(resto);
                            Intent intent = new Intent(mContext, MainActivity.class);
                            startActivity(intent);

                            // ...
                        } else {

                            // The verification code entered was invalid
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Incorrect Verificarion Code", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }


    private void sendVerificationCode(String phone){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            Toast.makeText(mContext,"verification completed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(mContext,"verification fialed", Toast.LENGTH_SHORT).show();
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                // [START_EXCLUDE]
                Toast.makeText(mContext, "invalid mob no", Toast.LENGTH_LONG).show();
                // [END_EXCLUDE]
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                // [START_EXCLUDE]
                Toast.makeText(mContext, "quota over", Toast.LENGTH_LONG).show();
                // [END_EXCLUDE]
            }
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Toast.makeText(mContext,"Code sent", Toast.LENGTH_SHORT).show();
            codeSent = s;
        }
    };






    private void getIncomingIntent (){

        if(getIntent().hasExtra("Resto")){
            resto = (Restoran)getIntent().getSerializableExtra("resto");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(mContext, "on Pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(mContext, "on Resume", Toast.LENGTH_SHORT).show();
    }
}
