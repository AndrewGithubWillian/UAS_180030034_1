package com.aa183.willian;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_mahasiswa";
    private final static String TABLE_MAHASISWA = "t_mahasiswa";
    private final static String KEY_ID_MAHASISWA = "ID_Mahasiswa";
    private final static String KEY_NAMA_MAHASISWA = "Nama_Mahasiswa";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_NAMA_PANGGILAN = "Nama_Panggilan";
    private final static String KEY_NIM = "Nim";
    private final static String KEY_JURUSAN = "Jurusan";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private Context context;

    public DatabaseHandler (Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_MAHASISWA = "CREATE TABLE " + TABLE_MAHASISWA
                + "(" + KEY_ID_MAHASISWA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAMA_MAHASISWA + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_NAMA_PANGGILAN + " TEXT, "
                + KEY_NIM + " TEXT, " + KEY_JURUSAN + " TEXT);";

        db.execSQL(CREATE_TABLE_MAHASISWA);
        inialisasiAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_MAHASISWA;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahMahasiswa (Mahasiswa dataMahasiswa){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAMA_MAHASISWA, dataMahasiswa.getNamamahasiswa());
        cv.put(KEY_TGL, sdFormat.format(dataMahasiswa.getTanggal()));
        cv.put(KEY_GAMBAR, dataMahasiswa.getGambar());
        cv.put(KEY_NAMA_PANGGILAN, dataMahasiswa.getNamapanggilan());
        cv.put(KEY_NIM, dataMahasiswa.getNim());
        cv.put(KEY_JURUSAN, dataMahasiswa.getJurusan());

        db.insert(TABLE_MAHASISWA, null, cv);
        db.close();
    }

    public void tambahMahasiswa (Mahasiswa dataMahasiswa, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAMA_MAHASISWA, dataMahasiswa.getNamamahasiswa());
        cv.put(KEY_TGL, sdFormat.format(dataMahasiswa.getTanggal()));
        cv.put(KEY_GAMBAR, dataMahasiswa.getGambar());
        cv.put(KEY_NAMA_PANGGILAN, dataMahasiswa.getNamapanggilan());
        cv.put(KEY_NIM, dataMahasiswa.getNim());
        cv.put(KEY_JURUSAN, dataMahasiswa.getJurusan());

        db.insert(TABLE_MAHASISWA, null, cv);
    }

    public void editMahasiswa (Mahasiswa dataMahasiswa){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_TGL, sdFormat.format(dataMahasiswa.getTanggal()));
        cv.put(KEY_GAMBAR, dataMahasiswa.getGambar());
        cv.put(KEY_NAMA_PANGGILAN, dataMahasiswa.getNamapanggilan());
        cv.put(KEY_NIM, dataMahasiswa.getNim());
        cv.put(KEY_JURUSAN, dataMahasiswa.getJurusan());


        db.update(TABLE_MAHASISWA, cv, KEY_ID_MAHASISWA + "=?", new String[]{String.valueOf(dataMahasiswa.getIdMahasiswa())});
        db.close();
    }

    public void hapusMahasiswa (int idMahasiswa){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_MAHASISWA, KEY_ID_MAHASISWA + "=?", new String[]{String.valueOf(idMahasiswa)});
        db.close();
    }

    public ArrayList<Mahasiswa> getAllMahasiswa(){
        ArrayList<Mahasiswa> dataBerita = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_MAHASISWA;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr =  db.rawQuery(query, null);
        if (csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                } catch (ParseException er){
                    er.printStackTrace();
                }

                Mahasiswa tempBerita = new Mahasiswa(
                        csr.getInt ( 0),
                        csr.getString( 1),
                        tempDate,
                        csr.getString( 3),
                        csr.getString( 4),
                        csr.getString( 5),
                        csr.getString( 6)
                );

                dataBerita.add(tempBerita);
            } while (csr.moveToNext());
        }
        return dataBerita;
    }

    private String storeImagesFiles(int id){
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inialisasiAwal(SQLiteDatabase db){
        int idMahasiswa = 0;
        Date tempDate = new Date();

        try {
            tempDate = sdFormat.parse("13/03/1998");
        }catch (ParseException er){
            er.printStackTrace();
        }



        Mahasiswa list1 = new Mahasiswa(
                idMahasiswa,
                "Brian O'conner",
                tempDate,
                storeImagesFiles(R.drawable.gambar1),
                "Brian",
                "170030031",
                "Teknik Mesin"
        );

        tambahMahasiswa(list1, db);
        idMahasiswa++;

        try {
            tempDate = sdFormat.parse("23/05/1997");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Mahasiswa list2 = new Mahasiswa(
                idMahasiswa,
                "Dominic Toretto",
                tempDate,
                storeImagesFiles(R.drawable.gambar2),
                "Dom",
                "170030032",
                "Teknik Electro"
        );

        tambahMahasiswa(list2, db);
        idMahasiswa++;

        try {
            tempDate = sdFormat.parse("25/11/1998");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Mahasiswa list3 = new Mahasiswa(
                idMahasiswa,
                "Roman Pearce",
                tempDate,
                storeImagesFiles(R.drawable.gambar3),
                "Rome",
                "170030033",
                "Teknik Mesin"
        );

        tambahMahasiswa(list3, db);


    }

}
