package com.yanuar.siapyanuar.services;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class AuthService extends SQLiteOpenHelper {
    public AuthService(Context context) {
        super(context, Services.NAMA_DATABASE, null, Services.VERSI_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String BUAT_TABEL = "CREATE TABLE kecamatan " +
            "(id INTEGER PRIMARY KEY, " +
            "nama TEXT NOT NULL)";
        db.execSQL(BUAT_TABEL);
        final String BUAT_TABEL2 = "CREATE TABLE " + Services.TABEL_PENGGUNA +
                " (uid TEXT PRIMARY KEY NOT NULL, " +
                "password TEXT NOT NULL, " +
                "nama TEXT NOT NULL)";
        db.execSQL(BUAT_TABEL2);
        final String BUAT_TABEL3 = "CREATE TABLE penduduk " +
                "(nik INTEGER PRIMARY KEY, " +
                "nama TEXT NOT NULL, " +
                "jk TEXT NOT NULL, " +
                "kecamatan TEXT NOT NULL, " +
                "agama TEXT NOT NULL)";
        db.execSQL(BUAT_TABEL3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Services.TABEL_PENGGUNA);
        db.execSQL("DROP TABLE IF EXISTS " + Services.TABEL_KECAMATAN);
        db.execSQL("DROP TABLE IF EXISTS " + Services.TABEL_PENDUDUK);

        onCreate(db);
    }

    public void register(String uid, String password, String nama) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            String query = "INSERT INTO " + Services.TABEL_PENGGUNA + " (uid, password, nama) " +
                    "VALUES ('" + uid + "', '" + password + "', '" + nama + "')";
            db.execSQL(query);
            System.out.println("Pengguna berhasil terdaftar.");
            System.out.println(query);

        } catch (SQLiteException e) {
            System.err.println("Error saat mendaftar: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean login(String uid, String password) {
        boolean loggedIn = false;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getWritableDatabase();
            String query = "SELECT * FROM " + Services.TABEL_PENGGUNA +
                    " WHERE uid = '" + uid + "' AND password = '" + password + "'";
            cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                loggedIn = true;
                System.out.println("Login berhasil.");
            } else {
                System.out.println("Login gagal. UID atau password salah.");
            }
        } catch (SQLiteException e) {
            System.err.println("Error saat login: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return loggedIn;
    }
    public boolean isExist(String uid) {
        boolean exist = false;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getWritableDatabase();
            String query = "SELECT * FROM " + Services.TABEL_PENGGUNA +
                    " WHERE uid = '" + uid + "'";
            cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                exist = true;
            }
        } catch (SQLiteException e) {
            System.err.println("Error saat memeriksa keberadaan pengguna: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return exist;
    }

}