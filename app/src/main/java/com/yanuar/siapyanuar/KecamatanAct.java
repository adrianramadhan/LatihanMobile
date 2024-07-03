package com.yanuar.siapyanuar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.yanuar.siapyanuar.services.KecamatanService;

public class KecamatanAct extends AppCompatActivity {
    private TextInputEditText inpId,inpNama;
    private Button btnSimpan;

    private KecamatanService db = new KecamatanService(this);

    String kd, nm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kecamatan_form);
        inpId = findViewById(R.id.tie_Id);
        inpNama = findViewById(R.id.tie_Nama);
        btnSimpan = findViewById(R.id.btn_Simpan);
        kd = getIntent().getStringExtra("id");
        nm = getIntent().getStringExtra("nama");

        if (kd == null || kd.equals("")) {
            setTitle("Tambah Pelanggan");
            inpId.requestFocus();
        } else {
            setTitle("Ubah Pelanggan");
            inpId.setText(kd);
            inpNama.setText(nm);
        }
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kd == null || kd.equals("")) {
                    simpan();
                } else {
                    ubah(kd);
                }
            }
        });
    }
    public void simpan() {
        if (inpId.getText().toString().isEmpty()  ||
                inpNama.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
        } else {
            if (!db.isExist(inpId.getText().toString())) {
                db.insert(inpId.getText().toString(),
                        inpNama.getText().toString());
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "ID Jenis sudah terdaftar!", Toast.LENGTH_SHORT).show();
                inpId.selectAll();
                inpId.requestFocus();
            }
        }
    }

    public void ubah(String kode) {
        if (inpId.getText().toString().isEmpty() ||
                inpNama.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
        } else {
            if (db.isExist(kd)) {
                db.update(inpId.getText().toString(),
                        inpNama.getText().toString(), kode);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "ID jenis tidak terdaftar", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
