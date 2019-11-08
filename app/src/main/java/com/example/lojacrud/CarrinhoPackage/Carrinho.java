package com.example.lojacrud.CarrinhoPackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lojacrud.BancoPackage.HistoricoDAO;
import com.example.lojacrud.BottomSendPopUp;

import com.example.lojacrud.Produtos;
import com.example.lojacrud.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Carrinho extends AppCompatActivity implements CarrinhoListener, PopupMenu.OnMenuItemClickListener {

    private List<Produtos> produtosCarrinho = new ArrayList<>();
    private List<Produtos> produtosCarrinhoView = new ArrayList<>();
    private Double precoTotal = 0.0;
    private RecyclerView recyclerView;
    private CarrinhoAdapter carrinhoAdapter;
    private TextView precoTotalView;
    private HistoricoDAO dao;
    int positionMenu = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        precoTotalView = findViewById(R.id.precoTotal);
        recyclerView = findViewById(R.id.lista_carrinhos);
        dao = new HistoricoDAO(this);
        Intent intent = getIntent();
        produtosCarrinho = (List<Produtos>) intent.getSerializableExtra("produtosSelecionados");
        carrinhoAdapter = new CarrinhoAdapter(this,produtosCarrinho,this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(carrinhoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        carrinhoAdapter.notifyDataSetChanged();
        setPrecoTotalView();
    }

//    @Override
//    public void quantidadeChanged(int position, String s) {
//        try {
//            if (s.equals("")){
//                precoTotal = 0.0;
//                produtosCarrinho.get(position).setQuantidade(0);
//                setPrecoTotalView();
//                carrinhoAdapter.notifyDataSetChanged();
//                //TODO: MUDAR FORMA COMO QUANTIDADE É INCREMENTADA
//            }
//            else {
//                precoTotal = 0.0;
//                produtosCarrinho.get(position).setQuantidade(Integer.valueOf(s));
//                setPrecoTotalView();
//                carrinhoAdapter.notifyDataSetChanged();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }

    @Override
    public void deleteItem(int position) {
        produtosCarrinho.remove(produtosCarrinho.get(position));
        if (produtosCarrinho.size()==0){
            precoTotalView.setText("0");
        }
        carrinhoAdapter.notifyDataSetChanged();
        setPrecoTotalView();
    }

    @Override
    public void selectQtd(int position, View view) {
        this.positionMenu = position;
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.setOnMenuItemClickListener(this);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.pop_up_qtd,popupMenu.getMenu());
        popupMenu.show();
    }

    public void setPrecoTotalView() {
        Double precoDescontado;
        precoTotal = 0.0;
        for(Produtos p : produtosCarrinho){
            if ((p.getPrecoDesconto()==null)||p.getPrecoDesconto().equals("")){
                precoDescontado = Double.valueOf(p.getPreco());
            }
            else {
                precoDescontado = Double.valueOf(p.getPreco()) - Double.valueOf(p.getPrecoDesconto());
            }
            precoTotal += p.getQuantidade()*Double.valueOf(precoDescontado);
        }
        precoTotalView.setText((BigDecimal.valueOf(precoTotal).setScale(2, RoundingMode.HALF_UP)).toString());
    }

    public void realizarCompra(View view) {
        if (produtosCarrinho.size()==0 || precoTotalView.getText().equals("0.0")) {
            Toast.makeText(this, "Não há nenhum item no carrinho", Toast.LENGTH_SHORT).show();
        }
        else{
            BottomSendPopUp bottomSendPopUp = new BottomSendPopUp(dao,produtosCarrinho,gerarPedido(produtosCarrinho));
            bottomSendPopUp.show(getSupportFragmentManager(),"example");
        }
    }


    public String gerarPedido(List<Produtos> produtosCarrinho){
        String conteudo = "Comprado: \n";
        Double precoDescontado;

        for(Produtos p : produtosCarrinho){
            if (p.getQuantidade()!=0) {
                if ((p.getPrecoDesconto()==null)||p.getPrecoDesconto().equals("")) {
                    precoDescontado = Double.valueOf(p.getPreco());
                } else {
                    precoDescontado = Double.valueOf(p.getPreco()) - Double.valueOf(p.getPrecoDesconto());
                }
                conteudo += p.getQuantidade().toString() + ", " + p.getNome() + " por " + precoDescontado + " cada \n";
            }

        }
        conteudo += "\t totalizando o valor de: "+ precoTotal.toString();
        return conteudo;
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            produtosCarrinho.remove(viewHolder.getAdapterPosition());
            if (produtosCarrinho.size()==0){
                precoTotalView.setText("0");
            }
            carrinhoAdapter.notifyDataSetChanged();
            setPrecoTotalView();
        }
    };


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        System.out.println(item.getItemId());
        if (item.getItemId() == R.id.qnt_1){
            resetValor(positionMenu,1,carrinhoAdapter);
        }
        else if (item.getItemId() == R.id.qnt_2){
            resetValor(positionMenu,2,carrinhoAdapter);
        }
        else if (item.getItemId() == R.id.qnt_3){
            resetValor(positionMenu,3,carrinhoAdapter);
        }
        else if (item.getItemId() == R.id.qnt_4){
            resetValor(positionMenu,4,carrinhoAdapter);
        }
        else if (item.getItemId() == R.id.qnt_5){
            resetValor(positionMenu,5,carrinhoAdapter);
        }
        else if (item.getItemId() == R.id.qnt_6){
            resetValor(positionMenu,6,carrinhoAdapter);
        }
        else if (item.getItemId() == R.id.qnt_mais){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final View customLayout = getLayoutInflater().inflate(R.layout.pop_up_qtd_change,null);
            builder.setView(customLayout);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText editText = customLayout.findViewById(R.id.editTextQtdPopUp);
                    Integer qtd = Integer.valueOf(editText.getText().toString());
                    if (qtd>99){
                        Toast.makeText(Carrinho.this, "Quantidade muito alta", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        resetValor(positionMenu,qtd,carrinhoAdapter);
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return false;
    }
    public void resetValor(int position, int qtd, CarrinhoAdapter adapter){
        produtosCarrinho.get(position).setQuantidade(qtd);
        setPrecoTotalView();
        adapter.notifyDataSetChanged();
    }
}
