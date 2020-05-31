package com.aa183.willian;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

public class TampilActivity extends AppCompatActivity {

    private ImageView imgMahasiswa;
    private TextView tvNamamahasiswa, tvTanggal, tvNamapanggilan, tvnim, tvjurusan;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        imgMahasiswa = findViewById(R.id.iv_mahasiswa);
        tvNamamahasiswa = findViewById(R.id.tv_nama_mahasiswa);
        tvTanggal = findViewById(R.id.tv_tanggal);
        tvNamapanggilan = findViewById(R.id.tv_nama_panggilan);
        tvnim = findViewById(R.id.tv_nim);
        tvjurusan = findViewById(R.id.tv_jurusan);

        Intent terimaData = getIntent();
        tvNamamahasiswa.setText(terimaData.getStringExtra("NAMA_MAHASISWA"));
        tvTanggal.setText(terimaData.getStringExtra("TANGGAL"));
        tvNamapanggilan.setText(terimaData.getStringExtra("NAMA_PANGGILAN"));
        tvnim.setText(terimaData.getStringExtra("NIM"));
        tvjurusan.setText(terimaData.getStringExtra("JURUSAN"));
        String imgLocation = terimaData.getStringExtra("GAMBAR");

        try {
            File file = new File(imgLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            imgMahasiswa.setImageBitmap(bitmap);
            imgMahasiswa.setContentDescription(imgLocation);
        } catch (FileNotFoundException er){
            er.printStackTrace();
            Toast.makeText(this, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tampil_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_bagikan){
            Intent bagikanBerita = new Intent(Intent.ACTION_SEND);
            bagikanBerita.putExtra(Intent.EXTRA_SUBJECT, tvNamamahasiswa.getText().toString());
            bagikanBerita.setType("text/plain");
            startActivity(Intent.createChooser(bagikanBerita, "Bagikan Berita"));
        }

        return super.onOptionsItemSelected(item);
    }
}
