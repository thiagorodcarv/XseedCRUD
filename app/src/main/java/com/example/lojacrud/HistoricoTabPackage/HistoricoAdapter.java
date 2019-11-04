package com.example.lojacrud.HistoricoTabPackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lojacrud.Produtos;
import com.example.lojacrud.R;

import java.io.File;
import java.util.List;

import static com.example.lojacrud.CadastroActivity.filePath;

public class HistoricoAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<String> dataDeCompras;
    private HistoricoListener historicoListener;

    public HistoricoAdapter(Context context, List<String> dataDeCompras, HistoricoListener historicoListener){
        this.context = context;
        this.dataDeCompras = dataDeCompras;
        this.historicoListener = historicoListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.historico_adapter,parent,false);
        HistoricoViewHolder holder = new HistoricoViewHolder(view,historicoListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        HistoricoViewHolder holder = (HistoricoViewHolder) viewHolder;
        String data = dataDeCompras.get(position);
        data = "Dia: "+ data.split(" ")[0] + "\nhora: " + data.split(" ")[1];
        holder.data.setText(data);
    }

    @Override
    public int getItemCount() {
        return dataDeCompras.size();
    }
}
