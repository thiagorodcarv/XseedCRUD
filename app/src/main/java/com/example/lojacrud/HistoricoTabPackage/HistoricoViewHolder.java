package com.example.lojacrud.HistoricoTabPackage;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lojacrud.R;

public class HistoricoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

//    TextView nome;
//    TextView preco;
//    TextView quantidade;
//    ImageView photoProduto;
    TextView data;
    CardView layoutHistorico;
    HistoricoListener historicoListener;

    public HistoricoViewHolder(@NonNull View itemView, HistoricoListener historicoListener) {
        super(itemView);
        this.historicoListener=historicoListener;
//        nome = itemView.findViewById(R.id.nome_produto_historico);
//        preco = itemView.findViewById(R.id.preco_produto_historico);
//        quantidade = itemView.findViewById(R.id.quantidade_historico);
//        photoProduto = itemView.findViewById(R.id.photo_produto_historico);
        layoutHistorico = itemView.findViewById(R.id.card_produto_historico);
        data = itemView.findViewById(R.id.data_historico);
        layoutHistorico.setOnClickListener(this);
        layoutHistorico.setOnLongClickListener(this);
    }


    @Override
    public void onClick(View v) {
        historicoListener.onCardClick(getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        historicoListener.onLongCardClick(getAdapterPosition());
        return true;
    }
}
