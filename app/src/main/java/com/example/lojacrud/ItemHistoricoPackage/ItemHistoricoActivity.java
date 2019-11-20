package com.example.lojacrud.ItemHistoricoPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lojacrud.CarrinhoPackage.Carrinho;
import com.example.lojacrud.ListDataModel;
import com.example.lojacrud.MainActivity;
import com.example.lojacrud.Produtos;
import com.example.lojacrud.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemHistoricoActivity extends AppCompatActivity {

    private List<Produtos> produtosHistorico;
    private List<Produtos> produtosHistoricoView = new ArrayList<>();
    private String dataCompra;
   // private ListDataModel pViewModel;
    private RecyclerView recyclerView;
    private ItemHistoricoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_historico);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.lista_item_historico);
        Intent intent = getIntent();
        dataCompra = intent.getStringExtra("data");
//        pViewModel = ViewModelProviders.of(this).get(ListDataModel.class);
        produtosHistorico = ListDataModel.produtosHistorico;
        produtosHistoricoView = recuperaItensPorData(produtosHistorico,dataCompra);
        adapter = new ItemHistoricoAdapter(this,produtosHistoricoView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();

    }

    public List<Produtos> recuperaItensPorData(List<Produtos> produtos, String data){
        List<Produtos> itensRecuperados = new ArrayList<>();

        for(Produtos p: produtos){
            if (p.getDataCompra().equals(data)){
                itensRecuperados.add(p);
            }
        }

        return itensRecuperados;
    }

    public List<Produtos> recuperaListaParaCarrinho(List<Produtos> produtos){
        List<Produtos> itensRecuperados = new ArrayList<>();

        for(Produtos p: produtos){
            if (!(p.getNome()==null||p.getNome().equals(""))){
                itensRecuperados.add(p);
            }
        }

        return itensRecuperados;
    }

    public void chamarCarrinho(View view){
        Intent intent = new Intent(this, Carrinho.class);
        intent.putExtra("produtosSelecionados", (Serializable) recuperaListaParaCarrinho(produtosHistoricoView));
        startActivity(intent);
    }

}
