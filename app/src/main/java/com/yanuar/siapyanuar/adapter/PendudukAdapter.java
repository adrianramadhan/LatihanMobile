package com.yanuar.siapyanuar.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yanuar.siapyanuar.R;
import com.yanuar.siapyanuar.model.KecamatanModel;
import com.yanuar.siapyanuar.model.PendudukModel;

import java.util.List;

public class PendudukAdapter extends BaseAdapter {
    Activity activity;
    LayoutInflater inflater;
    List<PendudukModel> penduduks;

    public PendudukAdapter(Activity activity, List<PendudukModel> penduduks) {
        this.activity = activity;
        this.penduduks = penduduks;
    }

    @Override
    public int getCount() {
        return penduduks.size();
    }

    @Override
    public Object getItem(int i) {
        return penduduks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null && inflater != null) {
            view = inflater.inflate(R.layout.penduduk_card, null);
        }
        if (view != null) {
            TextView tvNama = view.findViewById(R.id.tv_Nama);
            TextView tv_Nik = view.findViewById(R.id.tv_Nik);
            TextView tv_Kec = view.findViewById(R.id.tv_Kec);


            PendudukModel penduduk = penduduks.get(i);
            tvNama.setText(penduduk.get_nama());
            tv_Nik.setText(penduduk.get_nik());
            tv_Kec.setText(penduduk.get_kecamatan());
        }

        return view;
    }
}
