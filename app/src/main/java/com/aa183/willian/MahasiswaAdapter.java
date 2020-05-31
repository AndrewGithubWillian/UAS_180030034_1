package com.aa183.willian;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.BeritaViewHolder> {

    private Context context;
    private ArrayList<com.aa183.willian.Mahasiswa> namaMahasiswa;
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");

    public MahasiswaAdapter(Context context, ArrayList<com.aa183.willian.Mahasiswa> namaMahasiswa) {
        this.context = context;
        this.namaMahasiswa = namaMahasiswa;
    }

    @NonNull
    @Override
    public BeritaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_mahasiswa, parent, false);
        return new BeritaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeritaViewHolder holder, int position) {

        com.aa183.willian.Mahasiswa tempMahasiswa = namaMahasiswa.get(position);
        holder.idMahasiswa = tempMahasiswa.getIdMahasiswa();
        holder.tvNamaMahasiswa.setText(tempMahasiswa.getNamamahasiswa());
        holder.tvJurusan.setText(tempMahasiswa.getJurusan());
        holder.tanggal = sdFormat.format(tempMahasiswa.getTanggal());
        holder.gambar = tempMahasiswa.getGambar();
        holder.namapanggilan = tempMahasiswa.getNamapanggilan();
        holder.nim =tempMahasiswa.getNim();

        try {
            File file = new File(holder.gambar);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            holder.imgMahasiswa.setImageBitmap(bitmap);
            holder.imgMahasiswa.setContentDescription(holder.gambar);
        } catch (FileNotFoundException er){
            er.printStackTrace();
            Toast.makeText(context, "Gagal mengambil gambar dari media penyimpanan", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return namaMahasiswa.size();
    }

    public class BeritaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private ImageView imgMahasiswa;
        private TextView tvNamaMahasiswa, tvJurusan;
        private int idMahasiswa;
        private String tanggal, gambar, namapanggilan,nim;

        public BeritaViewHolder(@NonNull View itemView) {
            super(itemView);

            imgMahasiswa = itemView.findViewById(R.id.iv_mahasiswa);
            tvNamaMahasiswa = itemView.findViewById(R.id.tv_nama_mahasiswa);
            tvJurusan = itemView.findViewById(R.id.tv_jurusan);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent bukamahasiswa = new Intent(context, TampilActivity.class);
            bukamahasiswa.putExtra("ID", idMahasiswa);
            bukamahasiswa.putExtra("NAMA_MAHASISWA", tvNamaMahasiswa.getText().toString());
            bukamahasiswa.putExtra("TANGGAL", tanggal);
            bukamahasiswa.putExtra("NIM", nim);
            bukamahasiswa.putExtra("GAMBAR", gambar);
            bukamahasiswa.putExtra("NAMA_PANGGILAN", namapanggilan);
            bukamahasiswa.putExtra("JURUSAN", tvJurusan.getText().toString());
            context.startActivity(bukamahasiswa);
        }

        @Override
        public boolean onLongClick(View v) {

            Intent bukaInput = new Intent(context, InputActivity.class);
            bukaInput.putExtra("OPERASI", "update");
            bukaInput.putExtra("ID", idMahasiswa);
            bukaInput.putExtra("NAMA_MAHASISWA", tvNamaMahasiswa.getText().toString());
            bukaInput.putExtra("TANGGAL", tanggal);
            bukaInput.putExtra("GAMBAR", gambar);
            bukaInput.putExtra("NIM", nim);
            bukaInput.putExtra("NAMA_PANGGILAN", namapanggilan);
            bukaInput.putExtra("JURUSAN", tvJurusan.getText().toString());
            context.startActivity(bukaInput);
            return true;
        }
    }
}
