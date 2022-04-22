package com.mxbqr.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.mxbqr.app.R;
import com.mxbqr.app.model.Personel;

import java.util.ArrayList;

public class OzelAdapter extends BaseAdapter {
    Context context;
    ArrayList<Personel> personels;
    LayoutInflater layoutInflater;

    public OzelAdapter(Context context, ArrayList<Personel> personels) {
        this.context = context;
        this.personels = personels;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return personels.size();
    }

    @Override
    public Object getItem(int position) {
        return personels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.satir, parent, false);

        EditText adsoyad = convertView.findViewById(R.id.edTCKimlikNo);
        EditText tc = convertView.findViewById(R.id.edTCKimlikNo);
        EditText sicilno = convertView.findViewById(R.id.edSicilNo);
        EditText birim = convertView.findViewById(R.id.edBirim);
        EditText adres = convertView.findViewById(R.id.edAdres);
        EditText telefon = convertView.findViewById(R.id.edTelefon);
        EditText lokasyon = convertView.findViewById(R.id.edLokasyon);

        EditText kullaniciadi = convertView.findViewById(R.id.edKullaniciAdi);
        EditText kullanicisifre = convertView.findViewById(R.id.edSifre);


        Personel p = personels.get(position);
        adsoyad.setText(p.getAdsoyad());
        tc.setText(p.getTc());
        sicilno.setText(p.getSicilno());
        birim.setText(p.getBirim());
        adres.setText(p.getAdres());
        telefon.setText(p.getTelefon());
        lokasyon.setText(p.getLokasyon());

        kullaniciadi.setText(p.getKullanici_adi());
        kullanicisifre.setText(p.getKullanici_sifre());


        return convertView;
    }
}
