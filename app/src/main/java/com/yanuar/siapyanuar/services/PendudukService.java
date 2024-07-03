package com.yanuar.siapyanuar.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class PendudukService extends SQLiteOpenHelper {
    public PendudukService(Context context) {
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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Services.TABEL_PENGGUNA);
        db.execSQL("DROP TABLE IF EXISTS " + Services.TABEL_KECAMATAN);
        db.execSQL("DROP TABLE IF EXISTS " + Services.TABEL_PENDUDUK);
        onCreate(db);
    }

    public void insert(String nik, String nama, String jk, String kecamatan, String agama) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            String query = "INSERT INTO penduduk (nik, nama, jk, kecamatan, agama) " +
                    "VALUES (" + nik + ", '" + nama + "', '" + jk + "', '" + kecamatan + "', '" + agama + "')";
            db.execSQL(query);
            System.out.println("Data penduduk berhasil dimasukkan.");
        } catch (SQLiteException e) {
            System.err.println("Error saat memasukkan data penduduk: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void update(String nik, String nama, String jk, String kecamatan, String agama,String old_nik) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            String query = "UPDATE penduduk SET nama = '" + nama + "', jk = '" + jk + "', kecamatan = '" + kecamatan + "', agama = '" + agama + "' " +
                    "WHERE nik = " + old_nik;
            db.execSQL(query);
            System.out.println("Data penduduk berhasil diperbarui.");
        } catch (SQLiteException e) {
            System.err.println("Error saat memperbarui data penduduk: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public void delete(String nik) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            String query = "DELETE FROM penduduk WHERE nik = " + nik;
            db.execSQL(query);
            System.out.println("Data penduduk berhasil dihapus.");
        } catch (SQLiteException e) {
            System.err.println("Error saat menghapus data penduduk: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public ArrayList<HashMap<String, String>> getAll() {
        ArrayList<HashMap<String, String>> daftar = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getWritableDatabase();
            String query = "SELECT * FROM penduduk";
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> temp = new HashMap<>();
                    temp.put("nik", String.valueOf(cursor.getInt(0)));
                    temp.put("nama", cursor.getString(1));
                    temp.put("jk", cursor.getString(2));
                    temp.put("kecamatan", cursor.getString(3));
                    temp.put("agama", cursor.getString(4));
                    daftar.add(temp);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            System.err.println("Error saat mengambil data penduduk: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        System.out.println(daftar);

        return daftar;
    }
    public ArrayList<HashMap<String, String>> getAllByNama(String nama) {
        ArrayList<HashMap<String, String>> daftar = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getWritableDatabase();
            String query = "SELECT * FROM penduduk WHERE nama LIKE '%" + nama + "%'";
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> temp = new HashMap<>();
                    temp.put("nik", String.valueOf(cursor.getInt(0)));
                    temp.put("nama", cursor.getString(1));
                    temp.put("jk", cursor.getString(2));
                    temp.put("kecamatan", cursor.getString(3));
                    temp.put("agama", cursor.getString(4));
                    daftar.add(temp);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            System.err.println("Error saat mengambil data penduduk berdasarkan nama: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        System.out.println(daftar);
        return daftar;
    }

    public boolean isExist(String nik) {
        boolean exist = false;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getWritableDatabase();
            String query = "SELECT * FROM penduduk WHERE nik = " + nik;
            cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                exist = true;
            }
        } catch (SQLiteException e) {
            System.err.println("Error saat memeriksa keberadaan data penduduk: " + e.getMessage());
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