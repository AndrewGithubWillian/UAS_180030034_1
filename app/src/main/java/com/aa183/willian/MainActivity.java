package com.aa183.willian;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Mahasiswa> namaMahasiswa = new ArrayList<>();
    private RecyclerView rvMahasiswa;
    private MahasiswaAdapter mahasiswaAdapter;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMahasiswa = findViewById(R.id.rv_tampil_mahasiswa);
        dbHandler = new DatabaseHandler(this);
        tampilDatamahasiswa();

    }

    private void tampilDatamahasiswa(){
        namaMahasiswa = dbHandler.getAllMahasiswa();
        mahasiswaAdapter = new MahasiswaAdapter(this, namaMahasiswa);
        RecyclerView.LayoutManager layMangaer = new LinearLayoutManager(MainActivity.this);
        rvMahasiswa.setLayoutManager(layMangaer);
        rvMahasiswa.setAdapter(mahasiswaAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id==R.id.item_menu_tambah){
            Intent bukaInput = new Intent(getApplicationContext(), InputActivity.class);
            bukaInput.putExtra("OPERASI", "insert");
            startActivity(bukaInput);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilDatamahasiswa();
    }

}
