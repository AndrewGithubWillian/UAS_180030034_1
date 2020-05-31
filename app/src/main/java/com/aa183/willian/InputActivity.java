package com.aa183.willian;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class InputActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editNamaMahasiswa, editTanggal, editNamaPanggilan, editNim, editJurusan;
    private ImageView ivMahasiswa;
    private com.aa183.willian.DatabaseHandler dbHandler;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");
    private boolean updateData = false;
    private int idMahasiswa = 0;
    private Button btnSimpan;
    private Button btnPilihTanggal;
    private String tanggalMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        editNamaMahasiswa = findViewById(R.id.edit_nama_mahasiswa);
        editTanggal = findViewById(R.id.edit_tanggal);
        editNamaPanggilan = findViewById(R.id.edit_nama_panggilan);
        editNim = findViewById(R.id.edit_nim);
        editJurusan = findViewById(R.id.edit_jurusan);
        ivMahasiswa = findViewById(R.id.iv_mahasiswa);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnPilihTanggal = findViewById(R.id.btn_pilih_tgl);


        dbHandler = new com.aa183.willian.DatabaseHandler(this);

        Intent terimaIntent = getIntent();
        Bundle data = terimaIntent.getExtras();
        if (data.getString("OPERASI").equals("insert")){
            updateData = false;
        } else {
            updateData = true;
            idMahasiswa = data.getInt("ID");
            editNamaMahasiswa.setText(data.getString("NAMA_MAHASISWA"));
            editTanggal.setText(data.getString("TANGGAL"));
            editNamaPanggilan.setText(data.getString("NAMA_PANGGILAN"));
            editNim.setText(data.getString("NIM"));
            editJurusan.setText(data.getString("JURUSAN"));
            loadImageFromInternalStorage(data.getString("GAMBAR"));
        }

        ivMahasiswa.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
        btnPilihTanggal.setOnClickListener(this);
    }

    private void pickImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(6,4)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                try {
                    Uri imageUri = result.getUri();
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(inputStream);
                    //
                    String location =  saveImageToInternalStorage(selectedImage, getApplicationContext());
                    loadImageFromInternalStorage(location);
                }catch (FileNotFoundException er){
                    er.printStackTrace();
                    Toast.makeText(this, "Ada kegagalan dalam pengambilab gambar", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Anda belum memilih gambar", Toast.LENGTH_SHORT).show();
        }
    }

    public static String saveImageToInternalStorage(Bitmap bitmap, Context ctx) {
        ContextWrapper ctxWrapper = new ContextWrapper(ctx);
        File file = ctxWrapper.getDir("images", MODE_PRIVATE);
        String uniqueID = UUID.randomUUID().toString();
        file = new File(file, "mahasiswa- "+ uniqueID + ".jpg");
        try {
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (IOException er) {
            er.printStackTrace();
        }

        Uri savedImage = Uri.parse(file.getAbsolutePath());
        return  savedImage.toString();
    }

    private void loadImageFromInternalStorage(String imageLocation) {
        try {
            File file = new File(imageLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            ivMahasiswa.setImageBitmap(bitmap);
            ivMahasiswa.setContentDescription(imageLocation);
        } catch (FileNotFoundException er){
            er.printStackTrace();
            Toast.makeText(this, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.item_menu_hapus);

        if (updateData == true){
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        } else {
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.input_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.item_menu_hapus){
            hapusData();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void simpanData (){
        String namamahasiswa, gambar, namapanggilan, nim, jurusan;
        Date tanggal = new Date();
        namamahasiswa = editNamaMahasiswa.getText().toString();
        gambar = ivMahasiswa.getContentDescription().toString();
        namapanggilan = editNamaPanggilan.getText().toString();
        nim = editNim.getText().toString();
        jurusan = editJurusan.getText().toString();


        try {
            tanggal = sdFormat.parse(editTanggal.getText().toString());
        } catch (ParseException er) {
            er.printStackTrace();
        }

        com.aa183.willian.Mahasiswa tempMahasiswa = new com.aa183.willian.Mahasiswa(
                idMahasiswa, namamahasiswa, tanggal, gambar, namapanggilan, nim, jurusan
        );

        if (updateData == true){
            dbHandler.editMahasiswa(tempMahasiswa);
            Toast.makeText(this, "Data mahasiswa diperbaharui", Toast.LENGTH_SHORT).show();
        } else {
            dbHandler.tambahMahasiswa(tempMahasiswa);
            Toast.makeText(this, "Data mahasiswa ditambahkan", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void hapusData() {
        dbHandler.hapusMahasiswa(idMahasiswa);
        Toast.makeText(this, "Data mahasiswa berhasil dihapus", Toast.LENGTH_SHORT).show();
    }

    private void pilihtanggal(){
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tanggalMahasiswa = dayOfMonth + "/" + month + "/" + year;
                editTanggal.setText(tanggalMahasiswa);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        pickerDialog.show();
    }
    @Override
    public void onClick(View v) {
        int idView = v.getId();

        if (idView == R.id.btn_simpan){
            simpanData();
        } else if (idView == R.id.iv_mahasiswa){
            pickImage();
        } else if (idView == R.id.btn_pilih_tgl){
            pilihtanggal();
        }

    }
}