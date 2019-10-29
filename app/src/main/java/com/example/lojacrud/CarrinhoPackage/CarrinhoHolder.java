package com.example.lojacrud.CarrinhoPackage;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lojacrud.R;

public class CarrinhoHolder extends RecyclerView.ViewHolder implements TextWatcher,Button.OnClickListener{
    final TextView nome;
    final TextView preco;
    final TextView departamento;
    final ImageView imagem;
    final CardView parentLayout;
    final EditText quantidadeProdutos;
    final Button deleteItem;
    CarrinhoListener carrinhoListener;

    public CarrinhoHolder(@NonNull View itemView, CarrinhoListener carrinhoListener) {
        super(itemView);
        this.carrinhoListener = carrinhoListener;
        nome = itemView.findViewById(R.id.nome_produto_item_historico);
        preco = itemView.findViewById(R.id.preco_produto_item_historico);
        departamento = itemView.findViewById(R.id.dep_produto_carrinho);
        imagem = itemView.findViewById(R.id.photo_produto_item_historico);
        parentLayout = itemView.findViewById(R.id.card_produto_historico);
        quantidadeProdutos = itemView.findViewById(R.id.quantidadeProdutos);
        deleteItem = itemView.findViewById(R.id.deleteItemCarrinho);
        deleteItem.setOnClickListener(this);
        quantidadeProdutos.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        carrinhoListener.quantidadeChanged(getAdapterPosition(),s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        carrinhoListener.deleteItem(getAdapterPosition());
    }
}
