package com.example.abdialam.restopatner.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdialam.restopatner.R;
import com.example.abdialam.restopatner.models.Detailorder;
import com.example.abdialam.restopatner.models.Pesan;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeliveryActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    @BindView(R.id.tvNamaPemesan)
    TextView mNamaPemesan;
    @BindView(R.id.tvPhonePemesan)
    TextView mPhonePemesan;
    @BindView(R.id.tvAlamatAntar)
    TextView mAlamatAntar;
    @BindView(R.id.tvTotal)
    TextView mTotal;
    @BindView(R.id.btnNavigation)
    ImageButton btnNavigation;

    Pesan pesan;
    List<Detailorder> detailOrders;
    
    Context mContext;

    private LatLng pickUpLatLng = new LatLng(-6.175110, 106.865039); // Jakarta
    private LatLng locationLatLng = new LatLng(-6.197301,106.795951); // Cirebon



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        ButterKnife.bind(this);
        getIncomingIntent();
        mContext = this;
        //maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        //set Title bar
        getSupportActionBar().setTitle("Delivery Pesanan");
        //set value
        setValue();

    }


    private void getIncomingIntent (){

        if(getIntent().hasExtra("pesan")){
            pesan = (Pesan) getIntent().getSerializableExtra("pesan");
            detailOrders = pesan.getDetailorder();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String lokasiAwal = pickUpLatLng.latitude + "," + pickUpLatLng.longitude;
        String lokasiAkhir = locationLatLng.latitude + "," + locationLatLng.longitude;





        // Tambah Marker
        mMap.addMarker(new MarkerOptions().position(pickUpLatLng).title("Lokasi Awal"));
        mMap.addMarker(new MarkerOptions().position(locationLatLng).title("Lokasi Akhir"));
        mMap.addPolyline(new PolylineOptions()
                .add(pickUpLatLng, locationLatLng)
                .width(5)
                .color(Color.RED));
        /** START
         * Logic untuk membuat layar berada ditengah2 dua koordinat
         */

        LatLngBounds.Builder latLongBuilder = new LatLngBounds.Builder();
        latLongBuilder.include(pickUpLatLng);
        latLongBuilder.include(locationLatLng);

        // Bounds Coordinata
        LatLngBounds bounds = latLongBuilder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int paddingMap = (int) (width * 0.2); //jarak dari
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, paddingMap);
        mMap.animateCamera(cu);

        /** END
         * Logic untuk membuat layar berada ditengah2 dua koordinat
         */
    }


    public void setValue (){
        mNamaPemesan.setText(pesan.getKonsumenNama());
        mAlamatAntar.setText(pesan.getPesanAlamat());
        mPhonePemesan.setText(pesan.getKonsumenPhone());
        double total = 0;
        for (int i = 0; i < detailOrders.size(); i++) {
            total += Double.parseDouble(detailOrders.get(i).getHarga() )* detailOrders.get(i).getQty();
        }
        total = total+ Double.parseDouble(pesan.getPesanBiayaAntar());
        mTotal.setText(kursIndonesia(total));
    }


    @OnClick(R.id.btnNavigation) void navigation  (){
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+pesan.getPesanLokasi()+"&mode=d");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    //konfersi ke mata uang rupiah
    public String kursIndonesia(double nominal){
        Locale localeID = new Locale("in","ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String idnNominal = formatRupiah.format(nominal);
        return idnNominal;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top_up,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.topup){
            Toast.makeText(mContext,"top Up",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
