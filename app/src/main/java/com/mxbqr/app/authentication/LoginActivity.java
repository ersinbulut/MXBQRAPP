package com.mxbqr.app.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mxbqr.app.R;
import com.mxbqr.app.bottomnavigation.MainActivity;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    EditText edittext_kullanici, edittext_sifre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edittext_kullanici = findViewById(R.id.etName);
        edittext_sifre = findViewById(R.id.etPassword);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        if (mUser!=null){
            //MainMenuActivitye geç
        }
    }

    public void btnKayıtOl(View v){
        Intent i=new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    public void btnKaydol(View v){
        String email = edittext_kullanici.getText().toString();
        String sifre = edittext_sifre.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,sifre)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override//Giriş başarılı ise
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override//Giriş hatalı ise
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Kayıt Hatalı"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void btnGiris(View v){
        String email = edittext_kullanici.getText().toString();
        String sifre = edittext_sifre.getText().toString();
        mAuth.signInWithEmailAndPassword(email,sifre)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override//Giriş başarılı ise
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.putExtra("kullanici_adi", edittext_kullanici.getText().toString());
                        i.putExtra("kullanici_sifre", edittext_sifre.getText().toString());
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override//Giriş hatalı ise
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Giriş Hatalı"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void btnGec(View v) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra("kullanici_adi", edittext_kullanici.getText().toString());
        i.putExtra("kullanici_sifre", edittext_sifre.getText().toString());
        startActivity(i);
    }

}