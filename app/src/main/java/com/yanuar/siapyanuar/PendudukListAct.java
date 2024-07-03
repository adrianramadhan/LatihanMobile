package com.yanuar.siapyanuar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.yanuar.siapyanuar.adapter.KecamatanAdapter;
import com.yanuar.siapyanuar.adapter.PendudukAdapter;
import com.yanuar.siapyanuar.model.KecamatanModel;
import com.yanuar.siapyanuar.model.PendudukModel;
import com.yanuar.siapyanuar.services.KecamatanService;
import com.yanuar.siapyanuar.services.PendudukService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PendudukListAct extends AppCompatActivity {
    Button btn_tambah;
    ListView listView;

    AlertDialog.Builder dialog;
    List<PendudukModel> penduduks=new ArrayList<>();
    PendudukAdapter pendudukAdp;
    PendudukService db=new PendudukService(this);
    KecamatanService dbkec=new KecamatanService(this);

    TextInputEditText tiCari;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kecamatan_list);
        btn_tambah = findViewById(R.id.btn_Tambah);
        tiCari = findViewById(R.id.tie_Cari);
        listView = findViewById(R.id.lv_kecamatan); // Initialize listView here

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pel = new Intent(PendudukListAct.this, PendudukAct.class);
                startActivity(pel);
            }
        });
        ArrayList<HashMap<String,String>> daftar=db.getAll();
        System.out.println(daftar);
        pendudukAdp=new PendudukAdapter(PendudukListAct.this,penduduks);
        listView.setDivider(null);
        listView.setAdapter(pendudukAdp);
        tiCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getData(tiCari.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String nik=penduduks.get(position).get_nik();
                final String nama=penduduks.get(position).get_nama();
                final String kecamatan=penduduks.get(position).get_kecamatan();
                final String agama=penduduks.get(position).get_agama();
                final String jk=penduduks.get(position).get_jk();

                final CharSequence[] dialogItem={"Ubah", "Hapus", "Copy"};
                dialog=new AlertDialog.Builder(PendudukListAct.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0 :
                                Intent pel=new Intent(PendudukListAct.this,PendudukAct.class);
                                pel.putExtra("nik",nik);
                                pel.putExtra("nama",nama);
                                pel.putExtra("kecamatan",kecamatan);
                                pel.putExtra("agama",agama);
                                pel.putExtra("jk",jk);
                                startActivity(pel);
                                break;
                            case 1:
                                konfirmHapus(nik);
                                break;
                            case 2:
                                copyToClipBoard(nik);
                                tiCari.setText("");
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
    }


    public void getData(){
        ArrayList<HashMap<String,String>> daftar=db.getAll();
        System.out.println(daftar);

        penduduks.clear();
        for (int i=0;i<daftar.size();i++){
            String nik=daftar.get(i).get("nik");
            String nama=daftar.get(i).get("nama");
            String kecamatanV=daftar.get(i).get("kecamatan");
            String agama=daftar.get(i).get("agama");
            String jk=daftar.get(i).get("jk");

            PendudukModel penduduk=new PendudukModel();
            penduduk.set_nik(nik);
            penduduk.set_nama(nama);
            penduduk.set_kecamatan(kecamatanV);
            penduduk.set_agama(agama);
            penduduk.set_jk(jk);

            penduduks.add(penduduk);
        }
        pendudukAdp.notifyDataSetChanged();
    }
    @Override
    protected void onResume(){
        super.onResume();
        penduduks.clear();
        if (tiCari.getText().equals("")){
            getData();
        }else{
            getData(tiCari.getText().toString());
        }

    }
    public void getData(String namaCari){
        ArrayList<HashMap<String,String>> daftar=db.getAllByNama(namaCari);
        System.out.println(daftar);

        penduduks.clear();
        for (int i=0;i<daftar.size();i++){
            String nik=daftar.get(i).get("nik");
            String nama=daftar.get(i).get("nama");
            String kecamatanV=daftar.get(i).get("kecamatan");
            String agama=daftar.get(i).get("agama");
            String jk=daftar.get(i).get("jk");

            PendudukModel penduduk=new PendudukModel();
            penduduk.set_nik(nik);
            penduduk.set_nama(nama);
            penduduk.set_kecamatan(kecamatanV);
            penduduk.set_agama(agama);
            penduduk.set_jk(jk);

            penduduks.add(penduduk);
        }
        pendudukAdp.notifyDataSetChanged();
    }
    private void copyToClipBoard (String data){
        String ambilData = data;
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("copy data", ambilData);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this,"Nik penduduk di Copy", Toast.LENGTH_SHORT).show();
    }
    private void konfirmHapus (String kode){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Konfirmasi Hapus")
                .setMessage("Yakin data akan dihapus?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(kode);
                        penduduks.clear();
                        getData();
                    }
                })

                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false);
        alert.show();
    }
}
