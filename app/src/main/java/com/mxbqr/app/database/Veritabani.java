package com.mxbqr.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.mxbqr.app.model.Personel;
import java.util.ArrayList;

public class Veritabani extends SQLiteOpenHelper {

    public Veritabani(@Nullable Context context) {

        super(context, "Personels.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Personels(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "adsoyad TEXT, " +
                "tc TEXT, " +
                "birim TEXT, " +
                "adres TEXT, " +
                "telefon TEXT," +
                "lokasyon TEXT," +
                "kullanici_adi TEXT," +
                "kullanici_sifre TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Personels");
        onCreate(db);
    }

    public long prsEkle(Personel p){
        ContentValues icerik = new ContentValues();
        icerik.put("adsoyad", p.getAdsoyad());
        icerik.put("tc", p.getTc());
        icerik.put("sicilno", p.getSicilno());
        icerik.put("birim", p.getBirim());
        icerik.put("adres", p.getAdres());
        icerik.put("telefon", p.getTelefon());
        icerik.put("lokasyon", p.getLokasyon());
        //kullanıcı giriş bilgileri
        icerik.put("kullanici_adi", p.getKullanici_adi());
        icerik.put("kullanici_sifre", p.getKullanici_sifre());

        SQLiteDatabase db=getWritableDatabase();
        long cevap = db.insert("Personels",null,icerik);
        return cevap;
    }
    public  ArrayList<Personel> prsListele(){
        ArrayList<Personel> personelArrayList=new ArrayList<>();
        SQLiteDatabase db= getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Personels",null);
        if (cursor.moveToFirst()){
            do {
                int i=cursor.getInt(0);
                String adsoyad=cursor.getString(1);
                String tc=cursor.getString(2);
                String sicilno=cursor.getString(3);
                String birim=cursor.getString(4);
                String adres=cursor.getString(5);
                String telefon=cursor.getString(6);
                String lokasyon=cursor.getString(7);
                String kullanici_adi=cursor.getString(8);
                String kullanici_sifre=cursor.getString(9);
                Personel p=new Personel("",adsoyad,tc,sicilno,birim,adres,telefon,lokasyon,kullanici_adi,kullanici_sifre);
                personelArrayList.add(p);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return  personelArrayList;
    }

    public long prsDuzelt(Personel p){
        ContentValues icerik = new ContentValues();
        icerik.put("adsoyad", p.getAdsoyad());
        icerik.put("tc", p.getTc());
        icerik.put("sicilno", p.getSicilno());
        icerik.put("birim", p.getBirim());
        icerik.put("adres", p.getAdres());
        icerik.put("telefon", p.getTelefon());
        icerik.put("lokasyon", p.getLokasyon());
        //kullanıcı giriş bilgileri
        icerik.put("kullanici_adi", p.getKullanici_adi());
        icerik.put("kullanici_sifre", p.getKullanici_sifre());

        SQLiteDatabase db=getWritableDatabase();
        long cevap = db.update("Personels",icerik,"id="+p.getId(),null);
        return cevap;
    }
    public long prsSil(int silinecek_id){
        SQLiteDatabase db=getWritableDatabase();
        long cevap= db.delete("Personels","id="+silinecek_id,null);
        return cevap;
    }

    public long personelEkleParametrleri(int id, String adsoyad, String tc, String sicilno, String birim, String adres, String telefon, String lokasyon, String kullanici_adi, String kullanici_sifre){
        ContentValues veriler = new ContentValues();
        veriler.put("id" , id);
        veriler.put("adsoyad", adsoyad);
        veriler.put("tc", tc);
        veriler.put("birim",birim);
        veriler.put("adres", adres);
        veriler.put("telefon", telefon);
        veriler.put("lokasyon", lokasyon);
        veriler.put("kullanici_adi", kullanici_adi);
        veriler.put("kullanici_sifre", kullanici_sifre);

        SQLiteDatabase db = getWritableDatabase();
        long cevap = db.insert("Personels", null, veriler);
        return cevap;
    }

    public long personelEkle(Personel yeniPersonel){
        ContentValues veriler = new ContentValues();
        veriler.put("adsoyad", yeniPersonel.getAdsoyad());
        veriler.put("tc", yeniPersonel.getTc());
        veriler.put("sicilno", yeniPersonel.getSicilno());
        veriler.put("birim", yeniPersonel.getBirim());
        veriler.put("adres", yeniPersonel.getAdres());
        veriler.put("telefon", yeniPersonel.getTelefon());
        veriler.put("lokasyon", yeniPersonel.getLokasyon());
        //kullanıcı giriş bilgileri
        veriler.put("kullanici_adi", yeniPersonel.getKullanici_adi());
        veriler.put("kullanici_sifre", yeniPersonel.getKullanici_sifre());

        SQLiteDatabase db = getWritableDatabase();
        long cevap = db.insert("Personels", null, veriler);
        return cevap;
    }

    public ArrayList<Personel> personelleriListele(){
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
                Personel p = new Personel("", adsoyad, tc, sicilno, birim, adres,telefon,lokasyon,kullanici_adi,kullanici_sifre);
                personeller.add(p);
            }while(c.moveToNext());
        }
        c.close();
        return personeller;
    }

    public long personelEkle(EditText edAdSoyad, EditText edTCKimlik, EditText edSicilNo, EditText edBirim, EditText edAdres, EditText edTelefon, EditText edLokasyon, EditText edKullaniciAdi, EditText edSifre) {
        ContentValues veriler = new ContentValues();
        veriler.put("adsoyad", String.valueOf(edAdSoyad.getText()));
        veriler.put("tc", String.valueOf(edTCKimlik));
        veriler.put("sicilno", String.valueOf(edSicilNo));
        veriler.put("birim", String.valueOf(edBirim));
        veriler.put("adres", String.valueOf(edAdres));
        veriler.put("telefon", String.valueOf(edTelefon));
        veriler.put("lokasyon", String.valueOf(edLokasyon));
        //kullanıcı giriş bilgileri
        veriler.put("kullanici_adi", String.valueOf(edKullaniciAdi));
        veriler.put("kullanici_sifre", String.valueOf(edSifre));

        SQLiteDatabase db = getWritableDatabase();
        long cevap = db.insert("Personels", null, veriler);
        return cevap;
    }
}
