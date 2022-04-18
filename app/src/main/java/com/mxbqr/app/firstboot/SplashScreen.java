package com.mxbqr.app.firstboot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.mxbqr.app.authentication.LoginActivity;
import com.mxbqr.app.R;

public class SplashScreen extends AppCompatActivity {

    private ImageView imgGorsel;
    private static int GECİS_SURESİ=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //TANIMLAMALAR

        imgGorsel=(ImageView)findViewById(R.id.imgGorsel);

        //ANİMASYON
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.animation);

        imgGorsel.startAnimation(animation);

        //GEÇİŞ
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent gecis=new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(gecis);
                finish();
            }
        },GECİS_SURESİ);
    }
}
