package com.mxbqr.app.authentication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mxbqr.app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mxbqr.app.model.Personel;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements LocationListener {
    EditText AdSoyad,TCKimlik,SicilNo,Birim,Adres,Telefon,Lokasyon,KullaniciAdi,Sifre;
    //firebase veritabanı islemleri ile ilgili tanımlamalar
    FirebaseDatabase database;
    DatabaseReference myRef;
    //lokasyon islemleri ile ilgili tanımlamalar
    private LocationManager konumYoneticisi;
    private String konumSaglayici = "gps";
    private int izinKontrol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AdSoyad=findViewById(R.id.edAdSoyad);
        TCKimlik=findViewById(R.id.edTCKimlikNo);
        SicilNo=findViewById(R.id.edSicilNo);
        Birim=findViewById(R.id.edBirim);
        Adres=findViewById(R.id.edAdres);
        Telefon=findViewById(R.id.edTelefon);
        Lokasyon=findViewById(R.id.edLokasyon);
        KullaniciAdi=findViewById(R.id.edKullaniciAdi);
        Sifre=findViewById(R.id.edSifre);

        konumYoneticisi = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


         database = FirebaseDatabase.getInstance();
         myRef = database.getReference("users");

    }
    @Override
    public void onLocationChanged(Location location) {

        Double enlem = location.getLatitude();
        Double boylam = location.getLongitude();
        //Double yukseklik = location.getAltitude();
        Lokasyon.setText(String.valueOf(enlem)+","+String.valueOf(boylam));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {

            izinKontrol = ContextCompat.checkSelfPermission(RegisterActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION);

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(getApplicationContext(), "İzin kabul edildi.", Toast.LENGTH_LONG).show();

                Location konum = konumYoneticisi.getLastKnownLocation(konumSaglayici);

                if (konum != null) {
                    System.out.println("Provider " + konumSaglayici + " seçildi.");
                    onLocationChanged(konum);
                } else {
                    Lokasyon.setText("Konum aktif değil");
                }

            } else {
                Toast.makeText(getApplicationContext(), "İzin reddedildi.", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void btnPersonelEkle(View view){
        konumAl();
        String edAdSoyad=AdSoyad.getText().toString();
        String edTCKimlik=TCKimlik.getText().toString();
        String edSicilNo=SicilNo.getText().toString();
        String edBirim=Birim.getText().toString();
        String edAdres=Adres.getText().toString();
        String edTelefon=Telefon.getText().toString();
        String edLokasyon=Lokasyon.getText().toString();
        String edKullaniciAdi=KullaniciAdi.getText().toString();
        String edSifre=Sifre.getText().toString();
        String id = myRef.getKey();
        Personel yeniPersonel=new Personel(id,edAdSoyad,edTCKimlik,edSicilNo,edBirim,edAdres,edTelefon,edLokasyon,edKullaniciAdi,edSifre);

        myRef.push().setValue(yeniPersonel);
        kisiEkle();
        Toast.makeText(this, "Personel Eklendi..", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void kisiEkle(){
        String url="http://mxbinteractive.com/MXBQRAPP/insert_personel.php";

        StringRequest istek=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Cevap",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                //Kullanıcı bilgileri
                params.put("personel_adsoyad",AdSoyad.getText().toString());
                params.put("personel_tc",TCKimlik.getText().toString());
                params.put("personel_sicilno",SicilNo.getText().toString());
                params.put("personel_birim",Birim.getText().toString());
                params.put("personel_adres",Adres.getText().toString());
                params.put("personel_telefon",Telefon.getText().toString());
                params.put("personel_lokasyon",Lokasyon.getText().toString());
                //Login Bilgileri
                params.put("personel_kullaniciadi",KullaniciAdi.getText().toString());
                params.put("personel_sifre",Sifre.getText().toString());

                return params;
            }
        };

        Volley.newRequestQueue(this).add(istek);
        //Toast.makeText(this, "Eklendi..", Toast.LENGTH_SHORT).show();

    }
    public void konumAl(){
        izinKontrol = ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(izinKontrol != PackageManager.PERMISSION_GRANTED){
            //uygulamanın manifestinde izin var ama kullanıcı izni onaylamışmı bunun kontrolu yapılır

            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);

            //izin kontrolu daha önce yapılmış ve izine onay verilmemişse , izin alma diyalogu çıkar.
        }else{
            //daha önce izine onay verilmişse burası çalışır.

            Location konum = konumYoneticisi.getLastKnownLocation(konumSaglayici);

            if (konum != null) {

                onLocationChanged(konum);

            } else {
                Lokasyon.setText("Konum aktif degil");
            }
        }
    }
}
