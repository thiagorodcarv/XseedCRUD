package com.example.lojacrud.ItemHistoricoPackage;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lojacrud.R;

public class ItemHistoricoViewHolder extends RecyclerView.ViewHolder {
    final TextView nome;
    final TextView preco;
    final TextView departamento;
    final TextView quantidade;
    final ImageView imagem;


    public ItemHistoricoViewHolder(@NonNull View itemView) {
        super(itemView);
        nome = itemView.findViewById(R.id.nome_produto_item_historico);
        preco = itemView.findViewById(R.id.preco_produto_item_historico);
        departamento = itemView.findViewById(R.id.dep_produto_historico_item);
        quantidade = itemView.findViewById(R.id.quantidade_item_historico);
        imagem = itemView.findViewById(R.id.photo_produto_item_historico);
    }
}
