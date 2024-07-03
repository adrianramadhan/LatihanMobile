package com.yanuar.siapyanuar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.yanuar.siapyanuar.services.KecamatanService;
import com.yanuar.siapyanuar.services.PendudukService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PendudukAct extends AppCompatActivity {
    private TextInputEditText inpNik,inpNama;
    private AutoCompleteTextView inpKec,InpAgama;
    private Button btnSimpan;
    private RadioGroup jnskelamin;
    private RadioButton LakiLaki,perempuan;

    private PendudukService db = new PendudukService(this);
    private KecamatanService dbkc = new KecamatanService(this);


    String nik, nm,jk,kc,ag;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.penduduk_form);

        inpNik = findViewById(R.id.tie_Nik);
        inpNama = findViewById(R.id.tie_Nama);
        inpKec = findViewById(R.id.tie_Kecamatan);
        InpAgama = findViewById(R.id.tie_Agama);

        btnSimpan = findViewById(R.id.btn_Simpan);
        jnskelamin=(RadioGroup)findViewById(R.id.radiogrup);
        LakiLaki=(RadioButton)findViewById(R.id.rdb1);
        perempuan=(RadioButton)findViewById(R.id.rdb2);

        nik = getIntent().getStringExtra("nik");
        nm = getIntent().getStringExtra("nama");
        jk = getIntent().getStringExtra("jk");
        kc = getIntent().getStringExtra("kecamatan");
        ag = getIntent().getStringExtra("agama");

        if (nik == null || nik.equals("")) {
            setTitle("Tambah Pelanggan");
            inpNik.requestFocus();
        } else {
            setTitle("Ubah Pelanggan");
            inpNik.setText(nik);
            inpNama.setText(nm);
            inpKec.setText(kc);
            InpAgama.setText(ag);

            if (jk != null) {
                if (jk.equalsIgnoreCase("laki-laki")) {
                    LakiLaki.setChecked(true);
                } else if (jk.equalsIgnoreCase("perempuan")) {
                    perempuan.setChecked(true);
                }
            }
        }
        isiAgama();
        isiJenis();
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nik == null || nik.equals("")) {
                    simpan();
                } else {
                    ubah(nik);
                }
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        isiAgama();
        isiJenis();

    }
    public void simpan() {
        String jnsK="";
        String nikInput = inpNik.getText().toString();
        String namaInput = inpNama.getText().toString();
        String kecamatanInput = inpKec.getText().toString();
        String agamaInput = InpAgama.getText().toString();

        if (LakiLaki.isChecked()){
            jnsK="laki_laki";
        }
        if (perempuan.isChecked()){
            jnsK="perempuan";
        }
        int selectedId = jnskelamin.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        String jenisKelamin = selectedRadioButton.getText().toString();

        if (inpNik.getText().toString().isEmpty()  ||
                inpNama.getText().toString().isEmpty()||
                agamaInput.isEmpty() || jenisKelamin.isEmpty() || kecamatanInput.isEmpty()
        ) {
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
        } else {
            if (!db.isExist(inpNik.getText().toString())) {
                db.insert(nikInput,
                        namaInput,jnsK,kecamatanInput,agamaInput);
                finish();

            } else {
                Toast.makeText(getApplicationContext(), "ID Jenis sudah terdaftar!", Toast.LENGTH_SHORT).show();
                inpNik.selectAll();
                inpNik.requestFocus();
            }
        }
    }

    public void isiJenis() {
        ArrayList<HashMap<String, String>> daftar = dbkc.getAll();
        System.out.println(daftar);

        List<String> jenis = new ArrayList<>();

        for (HashMap<String, String> map : daftar) {
            jenis.add(map.get("nama"));
        }

        ArrayAdapter<String> combo = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, jenis);

        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) inpKec;
        autoCompleteTextView.setAdapter(combo);
    }
    public void isiAgama() {
        List<String> agama = new ArrayList<>();

        agama.add("Islam");
        agama.add("Kristen");
        agama.add("Katolik");
        agama.add("Hindu");
        agama.add("Buddha");
        agama.add("Konghucu");

        ArrayAdapter<String> combo = new ArrayAdapter<>(PendudukAct.this,
                android.R.layout.simple_spinner_dropdown_item, agama);
        InpAgama.setAdapter(combo);
    }
    public void ubah(String kode) {
        String jnsK="";
        String nikInput = inpNik.getText().toString();
        String namaInput = inpNama.getText().toString();
        String kecamatanInput = inpKec.getText().toString();
        String agamaInput = InpAgama.getText().toString();

        if (LakiLaki.isChecked()){
            jnsK="laki_laki";
        }
        if (perempuan.isChecked()){
            jnsK="perempuan";
        }
        int selectedId = jnskelamin.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        String jenisKelamin = selectedRadioButton.getText().toString();

        if (inpNik.getText().toString().isEmpty()  ||
                inpNama.getText().toString().isEmpty()||
                agamaInput.isEmpty() || jenisKelamin.isEmpty() || kecamatanInput.isEmpty()
        ) {
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
        } else {
            if (db.isExist(nikInput)) {
                db.update(nikInput,
                        namaInput,jnsK,kecamatanInput,agamaInput,nik);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "ID jenis tidak terdaftar", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
