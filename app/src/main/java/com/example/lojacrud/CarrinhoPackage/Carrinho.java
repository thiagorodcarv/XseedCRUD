package com.example.lojacrud.CarrinhoPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lojacrud.BancoPackage.HistoricoDAO;
import com.example.lojacrud.BottomSendPopUp;
import com.example.lojacrud.Produtos;
import com.example.lojacrud.R;

import java.util.ArrayList;
import java.util.List;

public class Carrinho extends AppCompatActivity implements CarrinhoListener {

    private List<Produtos> produtosCarrinho = new ArrayList<>();
    private List<Produtos> produtosCarrinhoView = new ArrayList<>();
    private Double precoTotal = 0.0;
    private RecyclerView recyclerView;
    private CarrinhoAdapter carrinhoAdapter;
    private TextView precoTotalView;
    private HistoricoDAO dao;


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

    @Override
    public void quantidadeChanged(int position, String s) {
        try {
            if (s.equals("")){
                precoTotal = 0.0;
                produtosCarrinho.get(position).setQuantidade(0);
                setPrecoTotalView();
            }
            else {
                precoTotal = 0.0;
                produtosCarrinho.get(position).setQuantidade(Integer.valueOf(s));
                setPrecoTotalView();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteItem(int position) {
        produtosCarrinho.remove(produtosCarrinho.get(position));
        if (produtosCarrinho.size()==0){
            precoTotalView.setText("0");
        }
        carrinhoAdapter.notifyDataSetChanged();
    }

    public void setPrecoTotalView() {
        Double precoDescontado;
        for(Produtos p : produtosCarrinho){
            if ((p.getPrecoDesconto()==null)||p.getPrecoDesconto().equals("")){
                precoDescontado = Double.valueOf(p.getPreco());
            }
            else {
                precoDescontado = Double.valueOf(p.getPreco()) - Integer.valueOf(p.getPrecoDesconto());
            }
            precoTotal += p.getQuantidade()*Double.valueOf(precoDescontado);
        }
        precoTotalView.setText(precoTotal.toString());
    }

    public void realizarCompra(View view) {
        if (produtosCarrinho.size()==0 || precoTotalView.getText().equals("0")) {
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
        }
    };


}
