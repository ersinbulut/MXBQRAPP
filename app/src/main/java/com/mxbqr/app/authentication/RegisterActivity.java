package com.mxbqr.app.authentication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mxbqr.app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mxbqr.app.database.LocalDatabase;
import com.mxbqr.app.model.Personel;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements LocationListener {
    EditText AdSoyad,TCKimlik,SicilNo,Birim,Adres,Telefon,Lokasyon,KullaniciAdi,Sifre;
    TextView markamodel,mac,ipaddress;
    //firebase veritabanı islemleri ile ilgili tanımlamalar
    FirebaseDatabase database;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    //lokasyon islemleri ile ilgili tanımlamalar
    private LocationManager konumYoneticisi;
    private String konumSaglayici = "gps";
    private int izinKontrol;
    //Local veri tabanı islemleri ile ilgili tanımlamalar
    LocalDatabase vt;
    Button bEkle;

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

        markamodel=(TextView) findViewById(R.id.textView1_model);
        mac=(TextView) findViewById(R.id.textView2_mac);
        ipaddress=(TextView) findViewById(R.id.textView3_manufacture);
        //cihazın marka model, mac ve ip adresini gizli textview e yazdırma
        markamodel.setText(Build.MANUFACTURER+ " " + Build.MODEL);
        String mac_adresi=getMacAddr();
        String ip_adresi=getIpAddress(true);
        mac.setText(mac_adresi);
        ipaddress.setText(ip_adresi);
        ///////////////////////////////////////////
        bEkle=findViewById(R.id.button);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

         database = FirebaseDatabase.getInstance();
         myRef = database.getReference("personels");

    }

    public void btnEkle(View v){
        String adsoyad=AdSoyad.getText().toString();
        String tc=TCKimlik.getText().toString();
        String sicilno=SicilNo.getText().toString();
        String birim=Birim.getText().toString();
        String adres=Adres.getText().toString();
        String telefon=Telefon.getText().toString();
        String lokasyon=Lokasyon.getText().toString();
        String marka=markamodel.getText().toString();
        String macadresi=mac.getText().toString();
        String ipadresi=ipaddress.getText().toString();
        String kullaniciadi=KullaniciAdi.getText().toString();
        String sifre=Sifre.getText().toString();


        Personel yeniPersonel=new Personel(0,adsoyad,tc,sicilno,birim,adres,telefon,lokasyon,marka,macadresi,ipadresi,kullaniciadi,sifre);
        if (!adsoyad.equals("") && !tc.equals("")&& !sicilno.equals("")&& !birim.equals("")&& !adres.equals("")&& !telefon.equals("")&& !kullaniciadi.equals("")&& !sifre.equals("")){
            vt =new LocalDatabase(this);
            long sonuc = vt.personelEkle(yeniPersonel);
            vt.close();
            if(sonuc>0){
                Toast.makeText(this, "Kayıt ekleme işlemi tamamlandı.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "kayıt ekleme başarısız.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Eksik bilgi girdiniz!", Toast.LENGTH_SHORT).show();
        }
        finish();
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
    //firebase tarafına personel ekleyen method
    public void btnPersonelEkle(View view){
        konumAl();
        String edAdSoyad=AdSoyad.getText().toString();
        String edTCKimlik=TCKimlik.getText().toString();
        String edSicilNo=SicilNo.getText().toString();
        String edBirim=Birim.getText().toString();
        String edAdres=Adres.getText().toString();
        String edTelefon=Telefon.getText().toString();
        String edLokasyon=Lokasyon.getText().toString();

        String chzmarkamodel=markamodel.getText().toString();
        String chzmac=mac.getText().toString();
        String chzip=ipaddress.getText().toString();

        String edKullaniciAdi=KullaniciAdi.getText().toString();
        String edSifre=Sifre.getText().toString();

        mAuth.createUserWithEmailAndPassword(edKullaniciAdi,edSifre)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override//Giriş başarılı ise
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(RegisterActivity.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override//Giriş hatalı ise
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Kayıt Hatalı"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        String id = myRef.getKey();
        String userid="mUser.getUid()";

        Personel yeniPersonel=new Personel(id,userid,edAdSoyad,edTCKimlik,edSicilNo,edBirim,edAdres,edTelefon,edLokasyon,chzmarkamodel,chzmac,chzip,edKullaniciAdi,edSifre);

        myRef.push().setValue(yeniPersonel);
        kisiEkle();  //sunucu veri tabanına ekleme metodu
        Toast.makeText(this, "Personel Eklendi..", Toast.LENGTH_SHORT).show();
        finish();
    }


    //sunucu tarafına personel ekleyen method
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

                params.put("personel_cmarkamodel",markamodel.getText().toString());
                params.put("personel_cmacadresi",mac.getText().toString());
                params.put("personel_cipadresi",ipaddress.getText().toString());
                //Login Bilgileri
                params.put("personel_kullaniciadi",KullaniciAdi.getText().toString());
                params.put("personel_sifre",Sifre.getText().toString());

                return params;
            }
        };

        Volley.newRequestQueue(this).add(istek);
        //Toast.makeText(this, "Eklendi..", Toast.LENGTH_SHORT).show();

    }
    //Cihazın konumunu alan method
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
    //Cihazın mac adresini alan method
    public static String getMacAddr() {
        try {
            ArrayList<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
    //Cihazın ıp adresini alan method
    public String getIpAddress(boolean useIPv4){
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) { } // for now eat exceptions
        return "";
    }
}
