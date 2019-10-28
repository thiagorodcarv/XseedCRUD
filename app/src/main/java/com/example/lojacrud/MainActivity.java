package com.example.lojacrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.lojacrud.BancoPackage.HistoricoDAO;
import com.example.lojacrud.BancoPackage.ProdutosDAO;
import com.example.lojacrud.CarrinhoPackage.Carrinho;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private PageAdapter pageAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Auxiliar auxiliar;
    private ListDataModel pViewModel;
    private ProdutosDAO produtosDAO;
    private HistoricoDAO historicoDAO;
    public static FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        tabLayout = findViewById(R.id.tabs);

        viewPager = findViewById(R.id.pager);
        pageAdapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        produtosDAO = new ProdutosDAO(this);
        historicoDAO = new HistoricoDAO(this);

        pViewModel = ViewModelProviders.of(this).get(ListDataModel.class);

        pViewModel.produtosBanco.addAll(produtosDAO.recuperarProdutos());
        pViewModel.produtosHistorico.addAll(historicoDAO.recuperarProdutosHistorico());

        tabLayout.setupWithViewPager(viewPager);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_loja,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    auxiliar = (Auxiliar) pageAdapter.getItem(viewPager.getCurrentItem());
                    auxiliar.procuraProduto(newText);
                }catch (Exception e){
                    e.printStackTrace();
                }

                return false;
            }
        });
        return true;
    }

    public void cadastrar(MenuItem menuItem){
        Intent it = new Intent(this,CadastroActivity.class);
        startActivity(it);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pViewModel.mapaChecks.clear();

        pViewModel.produtosSelecionados.clear();
        pViewModel.produtosBanco.clear();
        pViewModel.produtosHistorico.clear();
        pViewModel.produtosBanco.addAll(produtosDAO.recuperarProdutos());
        pViewModel.produtosHistorico.addAll(historicoDAO.recuperarProdutosHistorico());
    }

    public void chamarCarrinho(View view){
        Intent intent = new Intent(this, Carrinho.class);
        intent.putExtra("produtosSelecionados", (Serializable) pViewModel.produtosSelecionados);
        startActivity(intent);
    }

}
