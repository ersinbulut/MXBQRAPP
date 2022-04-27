package com.mxbqr.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mxbqr.app.model.Personel;

import java.util.ArrayList;

public class Veritabani extends SQLiteOpenHelper {
    //YAPICI METOT (Constructor)
    public Veritabani(@Nullable Context context) {

        super(context, "Personels.db", null, 2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Personels(" +
                "personel_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "personel_adsoyad TEXT, " +
                "personel_tc TEXT, " +
                "personel_sicilno TEXT, " +
                "personel_birim TEXT, " +
                "personel_adres TEXT, " +
                "personel_telefon TEXT," +
                "personel_lokasyon TEXT," +
                "personel_cmarkamodel TEXT," +
                "personel_cmacadresi TEXT," +
                "personel_cipadresi TEXT," +
                "personel_kullanici_adi TEXT," +
                "personel_kullanici_sifre TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Personels");
        onCreate(db);
    }

    public long personelEkle(Personel p){
        ContentValues veriler = new ContentValues();
        veriler.put("adsoyad", p.getAdsoyad());
        veriler.put("tc", p.getTc());
        veriler.put("sicilno", p.getSicilno());
        veriler.put("birim", p.getBirim());
        veriler.put("adres", p.getAdres());
        veriler.put("telefon", p.getTelefon());
        veriler.put("lokasyon", p.getLokasyon());
        //kullanıcı giriş bilgileri
        veriler.put("kullanici_adi", p.getKullanici_adi());
        veriler.put("kullanici_sifre", p.getKullanici_sifre());

        SQLiteDatabase db = getWritableDatabase();
        long cevap = db.insert("Personels", null, veriler);
        return cevap;
    }

    public ArrayList<Personel> personelListele(){
        ArrayList<Personel> personeller = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM personeller", null);
        if(c.moveToFirst()){
            do{
                String adsoyad = c.getString(1);
                String tc = c.getString(2);
                String sicilno = c.getString(3);
                String birim = c.getString(4);
                String adres = c.getString(5);
                String telefon = c.getString(6);
                String lokasyon = c.getString(7);

                String kullanici_adi = c.getString(8);
                String kullanici_sifre = c.getString(9);
                //System.out.println(id +" - "+ ad +" - " + yil +" - " + tur);
               // Personel p = new Personel("","", adsoyad, tc, sicilno, birim, adres,telefon,lokasyon,kullanici_adi,kullanici_sifre);
                //personeller.add(p);
            }while(c.moveToNext());
        }
        c.close();
        return personeller;
    }


}
