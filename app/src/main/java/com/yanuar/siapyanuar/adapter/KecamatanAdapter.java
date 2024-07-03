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

import java.util.List;

public class KecamatanAdapter extends BaseAdapter {
    Activity activity;
    LayoutInflater inflater;
    List<KecamatanModel> kecamatans;

    public KecamatanAdapter(Activity activity, List<KecamatanModel> kecamatans) {
        this.activity = activity;
        this.kecamatans = kecamatans;
    }

    @Override
    public int getCount() {
        return kecamatans.size();
    }

    @Override
    public Object getItem(int i) {
        return kecamatans.get(i);
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
            view = inflater.inflate(R.layout.kecamatan_card, null);
        }
        if (view != null) {
            TextView tvNama = view.findViewById(R.id.tv_Nama);
            TextView tvId = view.findViewById(R.id.tv_Id);


            KecamatanModel kecamatan = kecamatans.get(i);
            tvNama.setText(kecamatan.get_nama());
            tvId.setText(kecamatan.get_id());
        }

        return view;
    }
}