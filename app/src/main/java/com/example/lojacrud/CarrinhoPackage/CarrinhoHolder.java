package com.example.lojacrud.CarrinhoPackage;

import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lojacrud.R;

public class CarrinhoHolder extends RecyclerView.ViewHolder implements Button.OnClickListener, CheckBox.OnCheckedChangeListener, CardView.OnLongClickListener{
    final TextView nome;
    final TextView preco;
    final TextView precoTotal;
    final TextView qtd;
    final ImageView imagem;
    final CardView parentLayout;
//    final EditText quantidadeProdutos;
    final Button deleteItem;
    final CardView cardView;
    final CheckBox checkBox;
    final ConstraintLayout layout;
    CarrinhoListener carrinhoListener;

    public CarrinhoHolder(@NonNull final View itemView, final CarrinhoListener carrinhoListener) {
        super(itemView);
        this.carrinhoListener = carrinhoListener;
        nome = itemView.findViewById(R.id.nome_produto_item_historico);
        nome.setOnLongClickListener(this);
        layout = itemView.findViewById(R.id.layout_carrinho);
        preco = itemView.findViewById(R.id.preco_produto_item_historico);
        cardView = itemView.findViewById(R.id.cardViewCarrinhoQTD);
        qtd = itemView.findViewById(R.id.qtd_destaque);
        checkBox = itemView.findViewById(R.id.checkBox_carrinho);
        checkBox.setOnCheckedChangeListener(this);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carrinhoListener.selectQtd(getAdapterPosition(),itemView);
            }
        });
        imagem = itemView.findViewById(R.id.photo_produto_item_historico);
        precoTotal = itemView.findViewById(R.id.preco_total_carrinho_adapter);
        parentLayout = itemView.findViewById(R.id.card_produto_historico);
        parentLayout.setOnLongClickListener(this);
//        quantidadeProdutos = itemView.findViewById(R.id.quantidadeProdutos);
        deleteItem = itemView.findViewById(R.id.deleteItemCarrinho);
        deleteItem.setOnClickListener(this);
//        quantidadeProdutos.addTextChangedListener(this);
    }

//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        carrinhoListener.quantidadeChanged(getAdapterPosition(),s.toString());
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//
//    }

    @Override
    public void onClick(View v) {
        carrinhoListener.deleteItem(getAdapterPosition());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        carrinhoListener.onCheckListener(getAdapterPosition(),isChecked);
    }


    @Override
    public boolean onLongClick(View v) {
        carrinhoListener.onCheckListener(getAdapterPosition(),!checkBox.isChecked());
        return true;
    }
}
