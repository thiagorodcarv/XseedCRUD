package com.example.lojacrud.HistoricoTabPackage.PopUpHistorico;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lojacrud.R;

public class ListaHistoricoPopUpHolder extends RecyclerView.ViewHolder {

    final TextView quantidade;
    final TextView nome;
    final ImageView imagem;

    public ListaHistoricoPopUpHolder(@NonNull View itemView) {
        super(itemView);
        nome = itemView.findViewById(R.id.nome_lista_hist_popup);
        imagem = itemView.findViewById(R.id.img_lista_hist_popup);
        quantidade = itemView.findViewById(R.id.qnt_lista_hist_popup);
    }
}
