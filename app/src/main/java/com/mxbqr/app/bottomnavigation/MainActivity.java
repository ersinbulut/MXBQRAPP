package com.mxbqr.app.bottomnavigation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mxbqr.app.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FQrcreate f1;
    private FQrscanner f2;
    private FQrsettings f3;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // fragment tanımlamaları
        f1=new FQrcreate();
        f2=new FQrscanner();
        f3=new FQrsettings();

        //1.fragment gözükecek
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout,f1)
                .commit();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
                if (Item.getItemId()==R.id.Item1) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout,f1)
                            .commit();
                }
                if (Item.getItemId()==R.id.Item2) {
                    //2.fragment gözükecek
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout,f2)
                            .commit();
                    //Intent i=new Intent(MainActivity.this,QRScannerActivity.class);
                   //startActivity(i);
                }
                if (Item.getItemId()==R.id.Item3) {
                    //3.fragment gözükecek
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameLayout,f3)
                            .commit();
                }
                return true;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }
/*
    //+ ya basıldığında FilmEkleActivty aç
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //favori ekranı eklenecek
        if (item.getItemId() == R.id.item_ekle) {
            Intent i = new Intent(this, EkleActivity.class);
            //i.putExtra("kullaniciAdi", kadi);
            startActivity(i);
        } else if (item.getItemId() == R.id.item_cikis) {
            mAuth.signOut();
            Intent i = new Intent(this, GirisActivity.class);
            startActivity(i);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }*/
}
