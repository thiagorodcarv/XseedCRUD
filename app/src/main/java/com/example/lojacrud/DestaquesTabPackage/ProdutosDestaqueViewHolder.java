package com.example.lojacrud.DestaquesTabPackage;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lojacrud.ClickOnItems;
import com.example.lojacrud.R;

public class ProdutosDestaqueViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener, ImageView.OnClickListener{
    final TextView nome;
    final TextView preco;
    final TextView precoDesconto;
//    final TextView departamento;
    final ImageView imagem;
    final CardView parentLayout;
    CheckBox checkBox;
    ClickOnItems clickOnItems;

    public ProdutosDestaqueViewHolder(@NonNull View itemView, ClickOnItems clickOnItems) {
        super(itemView);
        this.clickOnItems = clickOnItems;
        nome = itemView.findViewById(R.id.nome_produto_destaque);
        preco = itemView.findViewById(R.id.preco_produto_destaque);
        precoDesconto = itemView.findViewById(R.id.preco_produto_destaque_desconto);
//        departamento = itemView.findViewById(R.id.dep_produto_destaque);
        imagem = itemView.findViewById(R.id.photo_produto_destaque);
        parentLayout = itemView.findViewById(R.id.card_produto_destaque);
        checkBox = itemView.findViewById(R.id.checkBox_destaque);
        checkBox.setOnCheckedChangeListener(this);
        imagem.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        clickOnItems.onCheckListener(getAdapterPosition(),isChecked);
    }

    @Override
    public void onClick(View v) {
        clickOnItems.onImgClickedListener(getAdapterPosition());
    }
}
