package com.yanuar.siapyanuar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class DashboardActivity extends AppCompatActivity {
    ImageButton imgbtn_kecamatan,imgbtn_penduduk;
    String nama;
    TextView txt_nama;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        nama = getIntent().getStringExtra("nama");

        imgbtn_kecamatan =(ImageButton) findViewById(R.id.imgbtn_kecamatan);
        imgbtn_penduduk =(ImageButton) findViewById(R.id.imgbtn_penduduk);
        txt_nama = (TextView) findViewById(R.id.nama);
        txt_nama.setText("Halo Selamat datang " + nama);
        imgbtn_kecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pel=new Intent(DashboardActivity.this,KecamatanListAct.class);
                startActivity(pel);
            }
        });
        imgbtn_penduduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pel=new Intent(DashboardActivity.this,PendudukListAct.class);
                startActivity(pel);
            }
        });

    }
}
