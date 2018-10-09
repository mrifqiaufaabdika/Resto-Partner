package com.example.abdialam.restopatner.activities.resto;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.config.ServerConfig;
import com.example.abdialam.restopatner.rest.ApiService;
import com.example.abdialam.restopatner.utils.SessionManager;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMenuActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 100;
    private static final int PICK_IMAGE_REQUEST = 1;

    SessionManager sessionManager;
    HashMap<String,String> user;
    Context mContext;
    String id_kategori,id_restoran;
    private Uri filePath ;
    private Bitmap bitmap;
    ApiService mApiService ;
    MultipartBody.Part body;
    String mediaPath;

    @BindView(R.id.btnChooseFile)
    Button btnPilihPhoto;
    @BindView(R.id.imageView)
    ImageView mPhoto;
    @BindView(R.id.etNamaMenu)
    EditText mNamamenu;
    @BindView(R.id.etHargaMenu)
    EditText mHargaMenu;
    @BindView(R.id.etDeskripsi)
    EditText mDeskripsi;
    @BindView(R.id.btnSubmitMenu)
    Button mSubmitMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);
        mContext = this;
        ButterKnife.bind(this);
        mApiService = ServerConfig.getAPIService();
        getIntentComing();
        sessionManager = new SessionManager(mContext);
        user = sessionManager.getRestoDetail();
        id_restoran = user.get(SessionManager.ID_RESTORAN).toString();

        Toast.makeText(mContext,"id resto "+id_restoran+" id kat"+id_kategori,Toast.LENGTH_SHORT).show();



    }




    private void getIntentComing() {

        if(getIntent().hasExtra("id_kategori")){
            id_kategori = getIntent().getStringExtra("id_kategori");

        }

    }





    @OnClick(R.id.btnChooseFile) void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_PICK);

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath =  pictureDirectory.getPath();

        Uri data = Uri.parse(pictureDirectoryPath);

        intent.setDataAndType(data,"image/*");


        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                mPhoto.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
                Cursor cursor = getContentResolver().query(filePath, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                mPhoto.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();


//            android.net.Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            android.database.Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            if (cursor == null)
//                return;
//
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String filePath = cursor.getString(columnIndex);
//            cursor.close();
//
//            File file = new File(filePath);
//
//            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//            body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
//            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

            }else {
                Toast.makeText(mContext,"anda belum memilih foto",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(mContext,"ada yang error",Toast.LENGTH_SHORT).show();
        }

    }


    @OnClick(R.id.btnSubmitMenu) void SubmitMenu () {
        String nama = "a";
        String harga = "5000";
        String deskripsi = "c";

        File file = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        mApiService.addMenu(fileToUpload, nama, deskripsi, harga, id_restoran, id_kategori).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(mContext, "Berhasil Menambah Menu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

//        mApiService.setMenu(fileToUpload,filename).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.isSuccessful()) {
//                    Toast.makeText(mContext, response.body().toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(mContext, "gagal berhasil menabah menu", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }

    }
}
