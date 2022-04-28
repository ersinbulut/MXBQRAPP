package com.mxbqr.app.bottomnavigation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mxbqr.app.R;
import com.mxbqr.app.adapter.OzelAdapter;
import com.mxbqr.app.authentication.LoginActivity;
import com.mxbqr.app.database.LocalDatabase;
import com.mxbqr.app.model.Personel;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FQrsettings extends Fragment implements LocationListener {
    EditText AdSoyad,TCKimlik,SicilNo,Birim,Adres,Telefon,Lokasyon,KullaniciAdi,Sifre,SifreTekrar;
    TextView txtkey,markamodel,macadresi,ipadresi;
    Button btnKaydet,btnCikisYap;
    String key;
    //firebase veritabanı islemleri ile ilgili tanımlamalar
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    FirebaseDatabase database;
    DatabaseReference myRef;
    //Local veritabanı ile ilgili tanımlamalar
    ListView listView;
    ArrayList<Personel> personeller;
    OzelAdapter ozelAdapter;
    LocalDatabase vt;


    //lokasyon islemleri ile ilgili tanımlamalar
    private LocationManager konumYoneticisi;
    private String konumSaglayici = "gps";
    private int izinKontrol;


    public FQrsettings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_f_qrsettings, container, false);

        listView=view.findViewById(R.id.listView);

        vt =new LocalDatabase(this.getContext());

        personeller = vt.personelListeleID("123");
        vt.close();

        ozelAdapter = new OzelAdapter(this.getContext(),personeller);
        listView.setAdapter(ozelAdapter);


        //array list içerisindeki elemanları log ekranına yazdırma
        for (int i = 0; i < personeller.size();i++) {
            System.out.println("id: "+personeller.get(i).getId1());
            System.out.println("ad soyad: "+personeller.get(i).getAdsoyad());
            System.out.println("tc: "+personeller.get(i).getTc());
            System.out.println("sicil no: "+personeller.get(i).getSicilno());
            System.out.println("birim: "+personeller.get(i).getBirim());
            System.out.println("adres: "+personeller.get(i).getAdres());
            System.out.println("telefon: "+personeller.get(i).getTelefon());
            System.out.println("cihaz marka model: "+personeller.get(i).getMarkamodel());
            System.out.println("cihaz mac adresi: "+personeller.get(i).getMacadresi());
            System.out.println("cihaz ip adresi: "+personeller.get(i).getIpadresi());
            System.out.println("kullanıcı adı: "+personeller.get(i).getKullanici_adi());
            System.out.println("sifre: "+personeller.get(i).getKullanici_sifre());
        }

        btnKaydet=view.findViewById(R.id.button4);
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Verileri güncelleme işlemleri
                kisiGuncelle();//sunucu veri tabanını güncelleyen metod
                Toast.makeText(getContext(), "Bilgiler Güncellendi..", Toast.LENGTH_SHORT).show();
            }
        });

        btnCikisYap=view.findViewById(R.id.button5);
        btnCikisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mAuth.signOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
                Toast.makeText(getContext(), "Oturum Sonlandırıldı..", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    //Sunucu veritabanını güncelleyen method
    public void kisiGuncelle(){
        String url="http://mxbinteractive.com/MXBQRAPP/update_personel.php";

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
                for (int i = 0; i < personeller.size();i++) {
                    params.put("personel_id","29");
                    params.put("personel_adsoyad",personeller.get(i).getAdsoyad());
                    params.put("personel_tc",personeller.get(i).getTc());
                    params.put("personel_sicilno",personeller.get(i).getSicilno());
                    params.put("personel_birim",personeller.get(i).getBirim());
                    params.put("personel_adres",personeller.get(i).getAdres());
                    params.put("personel_telefon",personeller.get(i).getTelefon());

                    params.put("personel_lokasyon",personeller.get(i).getLokasyon());

                    params.put("personel_cmarkamodel",personeller.get(i).getMarkamodel());
                    params.put("personel_cmacadresi",personeller.get(i).getMacadresi());
                    params.put("personel_cipadresi",personeller.get(i).getIpadresi());

                    //Login Bilgileri
                    params.put("personel_kullaniciadi",personeller.get(i).getKullanici_adi());
                    params.put("personel_sifre",personeller.get(i).getKullanici_sifre());
                }
                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(istek);
        //Toast.makeText(this, "Eklendi..", Toast.LENGTH_SHORT).show();

    }
/*
    public void kisiGuncelle(){
        final String tc=TCKimlik.getText().toString().trim();
        final String adsoyad=AdSoyad.getText().toString().trim();
        final String sicilno=SicilNo.getText().toString().trim();
        final String birim=Birim.getText().toString().trim();
        final String adres=Adres.getText().toString().trim();
        final String telefon=Telefon.getText().toString().trim();
        final String lokasyon=Lokasyon.getText().toString().trim();
        final String kadi=KullaniciAdi.getText().toString().trim();
        final String sifre=Sifre.getText().toString().trim();

        String url="http://mxbinteractive.com/MXBQRAPP/update_personel.php";

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
                //Kullanici
                Personel personel=new Personel();
                //params.put("user_id", personel.getId());
                params.put("personel_adsoyad",adsoyad);
                params.put("personel_tc",tc);
                params.put("personel_sicilno",sicilno);
                params.put("personel_birim",birim);
                params.put("personel_adres",adres);
                params.put("personel_telefon",telefon);
                params.put("personel_lokasyon",lokasyon);
                //Login
                params.put("personel_kullaniciadi",kadi);
                params.put("personel_sifre",sifre);
                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(istek);
        //Toast.makeText(getContext(), "Bilgiler güncellendi..", Toast.LENGTH_SHORT).show();
    }
*/
/*
    //ekleme işleminden sonra listview ı güncelleyen method
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        vt =new LocalDatabase(getContext());
        personeller = vt.personelListele();
        vt.close();

        ozelAdapter = new OzelAdapter(getContext(),personeller);
        listView.setAdapter(ozelAdapter);
    }
    @Override
    public void onResume() {
        super.onResume();
    }
*/
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

            izinKontrol = ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION);

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(getContext(), "İzin kabul edildi.", Toast.LENGTH_LONG).show();

                Location konum = konumYoneticisi.getLastKnownLocation(konumSaglayici);

                if (konum != null) {
                    System.out.println("Provider " + konumSaglayici + " seçildi.");
                    onLocationChanged(konum);
                } else {
                    Lokasyon.setText("Konum aktif değil");
                }

            } else {
                Toast.makeText(getContext(), "İzin reddedildi.", Toast.LENGTH_LONG).show();
            }
        }

    }
    //cihazın konumunu alan method
    public void konumAl(){
        izinKontrol = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        if(izinKontrol != PackageManager.PERMISSION_GRANTED){
            //uygulamanın manifestinde izin var ama kullanıcı izni onaylamışmı bunun kontrolu yapılır

            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);

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
