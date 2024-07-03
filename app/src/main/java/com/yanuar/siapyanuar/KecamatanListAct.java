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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.yanuar.siapyanuar.adapter.KecamatanAdapter;
import com.yanuar.siapyanuar.model.KecamatanModel;
import com.yanuar.siapyanuar.services.KecamatanService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KecamatanListAct extends AppCompatActivity {
    Button btn_tambah;
    ListView listView;
    AlertDialog.Builder dialog;
    List<KecamatanModel> kecamatans=new ArrayList<>();
    KecamatanAdapter kecamatanAdp;
    KecamatanService db=new KecamatanService(this);
    TextInputEditText tiCari;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kecamatan_list);
        btn_tambah = findViewById(R.id.btn_Tambah);
        tiCari=findViewById(R.id.tie_Cari);
        listView = findViewById(R.id.lv_kecamatan); // Initialize listView here

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pel=new Intent(KecamatanListAct.this,KecamatanAct.class);
                startActivity(pel);
            }
        });
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
        kecamatanAdp=new KecamatanAdapter(KecamatanListAct.this,kecamatans);
        listView.setDivider(null);
        listView.setAdapter(kecamatanAdp);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String kode=kecamatans.get(position).get_id();
                final String nama=kecamatans.get(position).get_nama();

                final CharSequence[] dialogItem={"Ubah", "Hapus", "Copy"};
                dialog=new AlertDialog.Builder(KecamatanListAct.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0 :
                                Intent pel=new Intent(KecamatanListAct.this,KecamatanAct.class);
                                pel.putExtra("id",kode);
                                pel.putExtra("nama",nama);
                                startActivity(pel);
                                break;
                            case 1:
                                konfirmHapus(kode);
                                break;
                            case 2:
                                copyToClipBoard(kode);
                                tiCari.setText("");
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        kecamatans.clear();
        if (tiCari.getText().equals("")){
            getData();
        }else{
            getData(tiCari.getText().toString());
        }

    }
    public void getData(){
        ArrayList<HashMap<String,String>> daftar=db.getAll();
        System.out.println(daftar);

        kecamatans.clear();
        for (int i=0;i<daftar.size();i++){
            String kode=daftar.get(i).get("id");
            String nama=daftar.get(i).get("nama");

            KecamatanModel kecamatan=new KecamatanModel();
            kecamatan.set_id(kode);
            kecamatan.set_nama(nama);
            kecamatans.add(kecamatan);
        }
        kecamatanAdp.notifyDataSetChanged();
    }
    public void getData(String namaCari){
        ArrayList<HashMap<String,String>> daftar=db.getAllByNama(namaCari);
        System.out.println(daftar);

        kecamatans.clear();
        for (int i=0;i<daftar.size();i++){
            String kode=daftar.get(i).get("id");
            String nama=daftar.get(i).get("nama");

            KecamatanModel kecamatan=new KecamatanModel();
            kecamatan.set_id(kode);
            kecamatan.set_nama(nama);
            kecamatans.add(kecamatan);
        }
        kecamatanAdp.notifyDataSetChanged();
    }
    private void copyToClipBoard (String data){
        String ambilData = data;
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("copy data", ambilData);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this,"ID pelanggan di Copy", Toast.LENGTH_SHORT).show();
    }
    private void konfirmHapus (String kode){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Konfirmasi Hapus")
                .setMessage("Yakin data akan dihapus?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(kode);
                        kecamatans.clear();
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
