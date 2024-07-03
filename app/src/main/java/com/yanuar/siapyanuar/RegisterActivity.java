package com.yanuar.siapyanuar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.yanuar.siapyanuar.model.AuthModel;
import com.yanuar.siapyanuar.services.AuthService;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    Button btn_simpan;
    private TextInputEditText inpUsername,inpPassword,inpNama;

    List<AuthModel> users=new ArrayList<>();
    AuthService dbauth=new AuthService(this);

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_form);
        inpUsername = findViewById(R.id.tie_Username);
        inpPassword = findViewById(R.id.tie_Password);
        inpNama = findViewById(R.id.tie_Nama);

        btn_simpan = findViewById(R.id.btn_Simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });

    }



    public void simpan() {
        if (inpPassword.getText().toString().isEmpty()  ||
                inpNama.getText().toString().isEmpty() || inpUsername.getText().toString().isEmpty() )   {
            Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
        } else {
            if (!dbauth.isExist(inpUsername.getText().toString())) {
                dbauth.register(inpUsername.getText().toString(),
                        inpPassword.getText().toString(),inpNama.getText().toString());
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Username sudah terdaftar!", Toast.LENGTH_SHORT).show();
                inpUsername.selectAll();
                inpUsername.requestFocus();
            }
        }
    }


}
