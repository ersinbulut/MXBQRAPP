package com.mxbqr.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mxbqr.app.model.Personel;

import java.util.ArrayList;

public class LocalDatabase extends SQLiteOpenHelper {
    //YAPICI METOT (Constructor)
    public LocalDatabase(@Nullable Context context) {
        super(context, "Personels.db",null,1);
    }
    //bu method uygulamayı ilk kurduğumuzda yalnızca bir kere çalışır.
    @Override
    public void onCreate(SQLiteDatabase db) {//UYGULAMA İLK DEFA KURULDUĞUNDA ÇALIŞIR
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
    //UYGULAMADA GÜNCELLEME OLDUĞU ZAMAN ÇALIŞIR
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Personels");//uygulamayı kurduğunda tabloyu siler
        onCreate(db);//ve yeniden oluşturması için oncreate tekrar çalıştırır.
    }

    public long personelEkle(Personel p){
        ContentValues icerik=new ContentValues();
        //tablodaki alan adına göre nesne içerisindeki alan ı icerik olarak ekleme
        icerik.put("personel_adsoyad",p.getAdsoyad());
        icerik.put("personel_tc",p.getTc());
        icerik.put("personel_sicilno",p.getSicilno());
        icerik.put("personel_birim",p.getBirim());
        icerik.put("personel_adres",p.getAdres());
        icerik.put("personel_telefon",p.getTelefon());
        icerik.put("personel_lokasyon",p.getLokasyon());
        icerik.put("personel_cmarkamodel",p.getMarkamodel());
        icerik.put("personel_cmacadresi",p.getMacadresi());
        icerik.put("personel_cipadresi",p.getIpadresi());
        icerik.put("personel_kullanici_adi",p.getKullanici_adi());
        icerik.put("personel_kullanici_sifre",p.getKullanici_sifre());

        SQLiteDatabase db=  getWritableDatabase();
        //YENİ EKLENEN KAYDIN ID SİNİ CEVAP OLARAK VERİR
        long cevap = db.insert("Personels",null,icerik);
        //HATA OLURSA -1 SONUCU VERİR
        return cevap;
    }

    public ArrayList<Personel> personelListele(){
        ArrayList<Personel> personelArrayList=new ArrayList<>();
        //veri tabanından okuma işlemleri
        SQLiteDatabase db= getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, turkce, ingilizce FROM kelime",null);
        //rawquery bilgileri cursor tipinde döndürür
        //cursor nesnesi: kayıtları üzerinde gezinebileceğimiz şekile getirir örneğin 10 kayıt dönüyorsa sırayla cursor u haraket
        //ettirip bu kaydın içeriğine erişebiliriz

        //ilk kayıt var ise true döner
        if (cursor.moveToFirst()){
            do {
                int i=cursor.getInt(0);
                String adsoyad = cursor.getString(1);
                String tc = cursor.getString(2);
                String sicilno = cursor.getString(3);
                String birim = cursor.getString(4);
                String adres = cursor.getString(5);
                String telefon = cursor.getString(6);
                String lokasyon = cursor.getString(7);
                String markamodel = cursor.getString(8);
                String macadresi = cursor.getString(9);
                String ipadresi = cursor.getString(10);

                String kullanici_adi = cursor.getString(11);
                String kullanici_sifre = cursor.getString(12);
                //bu değerleri diziye atma
                Personel p=new Personel(i,adsoyad,tc,sicilno,birim,adres,telefon,lokasyon,markamodel,macadresi,ipadresi,kullanici_adi,kullanici_sifre);
                personelArrayList.add(p);
            }while (cursor.moveToNext());//her kaydı okuduktan sonra bir sonrakine geçer
        }
        cursor.close();
        return personelArrayList;
    }

    public Long personelDuzelt(Personel p){
        ContentValues icerik=new ContentValues();
        icerik.put("personel_adsoyad",p.getAdsoyad());
        icerik.put("personel_tc",p.getTc());
        icerik.put("personel_sicilno",p.getSicilno());
        icerik.put("personel_birim",p.getBirim());
        icerik.put("personel_adres",p.getAdres());
        icerik.put("personel_telefon",p.getTelefon());
        icerik.put("personel_lokasyon",p.getLokasyon());
        icerik.put("personel_cmarkamodel",p.getMarkamodel());
        icerik.put("personel_cmacadresi",p.getMacadresi());
        icerik.put("personel_cipadresi",p.getIpadresi());
        icerik.put("personel_kullanici_adi",p.getKullanici_adi());
        icerik.put("personel_kullanici_sifre",p.getKullanici_sifre());

        SQLiteDatabase db=  getWritableDatabase();
        //GÜNCELLENEN KAYDIN ID SİNİ CEVAP OLARAK VERİR
        long cevap = db.update("kelime",icerik,"id="+p.getId1(),null);
        //HATA OLURSA -1 SONUCU VERİR
        return cevap;
    }

    public long personelSil(int silinecek_id){
        SQLiteDatabase db=  getWritableDatabase();
        long cevap = db.delete("Personels","id="+silinecek_id,null);
        return cevap;
    }
    /*
    public ArrayList<Personel> personelAra(String aranan){
        String[] args={aranan+"%",aranan+"%"};

        ArrayList<Personel> personelArrayList=new ArrayList<>();
        //veri tabanından okuma işlemleri
        SQLiteDatabase db= getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id,turkce,ingilice FROM kelime WHERE turkce LIKE ? or ingilizce LIKE ?",args);
        //rawquery bilgileri cursor tipinde döndürür
        //cursor nesnesi: kayıtları üzerinde gezinebileceğimiz şekile getirir örneğin 10 kayıt dönüyorsa sırayla cursor u haraket
        //ettirip bu kaydın içeriğine erişebiliriz

        if (cursor.moveToFirst()){
            //ilk kayıt var ise true döner
            do {
                int i=cursor.getInt(0);
                String tur=cursor.getString(1);
                String ing=cursor.getString(2);
                //bu değerleri diziye atma
                Personel k=new Personel(i,tur,ing);
                personelArrayList.add(k);
            }while (cursor.moveToNext());//her kaydı okuduktan sonra bir sonrakine geçer
        }
        cursor.close();
        return personelArrayList;
    }
    */
}
