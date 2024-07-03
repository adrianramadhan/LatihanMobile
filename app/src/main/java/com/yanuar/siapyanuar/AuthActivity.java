package com.yanuar.siapyanuar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.yanuar.siapyanuar.model.AuthModel;
import com.yanuar.siapyanuar.services.AuthService;

import java.util.ArrayList;
import java.util.List;

public class AuthActivity extends AppCompatActivity {
    private TextInputEditText inpUsername,inpPassword;

    List<AuthModel> users=new ArrayList<>();
    AuthService dbauth=new AuthService(this);
    Button btn_login,btn_register,btn_exit;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);

        inpUsername = findViewById(R.id.tie_Username);
        inpPassword = findViewById(R.id.tie_Password);
        btn_login = findViewById(R.id.btn_Login);
        btn_register = findViewById(R.id.btn_Register);
        btn_exit = findViewById(R.id.btn_Exit);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regis=new Intent(AuthActivity.this,RegisterActivity.class);
                startActivity(regis);
            }
        });
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                konfiKeluar();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inpPassword.getText().toString().isEmpty()  ||
                        inpUsername.getText().toString().isEmpty() )   {
                    Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
                } else {

                    if (dbauth.login(inpUsername.getText().toString(),inpPassword.getText().toString())) {
                        Intent pel=new Intent(AuthActivity.this,DashboardActivity.class);
                        pel.putExtra("nama",inpUsername.getText().toString());
                        startActivity(pel);
                    }else {
                        Toast.makeText(getApplicationContext(), "ID Jenis sudah terdaftar!", Toast.LENGTH_SHORT).show();
                        inpUsername.selectAll();
                        inpUsername.requestFocus();
                    }


                }
            }
        });

    }
    private void konfiKeluar (){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Keluar")
                .setMessage("Yakin data akan keluar aplikasi?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
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
