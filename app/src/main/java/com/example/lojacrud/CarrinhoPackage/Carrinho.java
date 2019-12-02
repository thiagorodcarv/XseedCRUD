package com.example.lojacrud.CarrinhoPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lojacrud.BancoPackage.HistoricoDAO;
import com.example.lojacrud.BancoPackage.ProdutosDAO;
import com.example.lojacrud.BottomSendPopUp;

import com.example.lojacrud.Produtos;
import com.example.lojacrud.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Carrinho extends AppCompatActivity implements CarrinhoListener, PopupMenu.OnMenuItemClickListener {

    private List<Produtos> produtosCarrinho = new ArrayList<>();
    private List<Produtos> produtosCarrinhoIntent = new ArrayList<>();
    private List<Produtos> produtosCarrinhoView = new ArrayList<>();
    private Double precoTotal = 0.0;
    private RecyclerView recyclerView;
    private CarrinhoAdapter carrinhoAdapter;
    private TextView precoTotalView;
    private HistoricoDAO historicoDAO;
    private ProdutosDAO produtosDAO;
    private FrameLayout frameLayout;
    private MenuItem checkAllItem;
    private FloatingActionButton fab;
    private ImageView imagePlaceHolder;
    private boolean checkedAll = false;
    private int positionMenu = -1;
    private Boolean checkBoxEnabler = false;
    private List<Produtos> produtosDelete = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        precoTotalView = findViewById(R.id.precoTotal);
        recyclerView = findViewById(R.id.lista_carrinhos);
        fab = findViewById(R.id.floatingActionButtonCarrinho);
        frameLayout = findViewById(R.id.frameLayout);
        imagePlaceHolder = findViewById(R.id.imagePlaceHolderCarrinho);
        historicoDAO = new HistoricoDAO(this);
        produtosDAO = new ProdutosDAO(this);
        Intent intent = getIntent();
        produtosCarrinhoIntent = (List<Produtos>) intent.getSerializableExtra("produtosSelecionados");
        for (Produtos produtos : produtosCarrinhoIntent){
            produtos = produtosDAO.selectProduto(produtos);
            produtosCarrinho.add(produtos);
        }
        setImageHolder(produtosCarrinho);
        carrinhoAdapter = new CarrinhoAdapter(this,produtosCarrinho,this, checkBoxEnabler);
//        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_carrinho,menu);
        checkAllItem = menu.findItem(R.id.check_all);
        return true;
    }


    @Override
    public void deleteItem(int position) {
        produtosCarrinho.remove(produtosCarrinho.get(position));
        carrinhoAdapter.notifyDataSetChanged();
        setImageHolder(produtosCarrinho);
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

    @Override
    public void onCheckListener(int position, boolean isChecked) {
        if (checkBoxEnabler==false && isChecked){
            checkBoxEnabler = true;
            //deleteMenu.setVisible(true);
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    carrinhoAdapter.changeMode(checkBoxEnabler);
                    checkAllItem.setVisible(checkBoxEnabler);
                }
            });
        }
        if (isChecked==false){
            produtosDelete.remove(produtosCarrinho.get(position));
            produtosCarrinho.get(position).setChecked(false);
            recyclerView.post(new Runnable()
            {
                @Override
                public void run() {
                    carrinhoAdapter.notifyDataSetChanged();
                }
            });
            /*
            You use your RecyclerView instance and inside the post method a new Runnable added to the message queue.
            The runnable will be run on the user interface thread. This is a limit for Android to access the UI thread from background
            (e.g. inside a method which will be run in a background thread). for more you run it on UI thread if you needed.

             This seems to be a bug in the RecyclerView library, when notifyDataSetChanged() is called either in quick succession
              (within 50 ms) or whilst scrolling -- even if it's called outside of onBindViewHolder().
               For example, it happens when refreshing the whole list with notifyDataSetChanged(), not necessarily adding or removing items.
               The Runnable suggestion works well.
             */
            //Toast.makeText(getActivity(), produtosView.get(position).getNome()+" Removido", Toast.LENGTH_SHORT).show();

        }
        else {

            if(!(produtosDelete.contains(produtosCarrinho.get(position)))){
                produtosCarrinho.get(position).setChecked(true);
                produtosDelete.add(produtosCarrinho.get(position));
                recyclerView.post(new Runnable()
                {
                    @Override
                    public void run() {
                        carrinhoAdapter.notifyDataSetChanged();
                    }
                });
//                Toast.makeText(this, produtosCarrinho.get(position).getNome()+" Adicionado", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void checkAll(MenuItem menuItem){
        checkedAll = !checkedAll;
        for(int i = 0;i<produtosCarrinho.size();i++){
            onCheckListener(i,checkedAll);
        }
    }

    public void abrircheckboxes(final MenuItem menuItem){
        if(checkBoxEnabler && (produtosDelete.size()>0)){
            AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Atenção")
                    .setMessage("Deseja excluir os produtos do carrinho?")
                    .setNegativeButton("NÃO",null)
                    .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            produtosCarrinho.removeAll(produtosDelete);
                            produtosDelete.clear();
                            carrinhoAdapter.changeMode(checkBoxEnabler);
                            checkAllItem.setVisible(checkBoxEnabler);
                            setImageHolder(produtosCarrinho);
                            setPrecoTotalView();
                        }
                    }).create();
            dialog.show();
        }
        else {
            //deleteMenu.setVisible(true);
            carrinhoAdapter.changeMode(!checkBoxEnabler);
            checkAllItem.setVisible(!checkBoxEnabler);
        }
        checkBoxEnabler = !checkBoxEnabler;
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
        if (produtosCarrinho.size()==0 || precoTotalView.getText().equals("0.00")) {
            Toast.makeText(this, "Não há nenhum item no carrinho", Toast.LENGTH_SHORT).show();
        }
        else{
            BottomSendPopUp bottomSendPopUp = new BottomSendPopUp(historicoDAO,produtosCarrinho,gerarPedido(produtosCarrinho));
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

//    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
//        @Override
//        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//            return false;
//        }
//
//        @Override
//        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//            produtosCarrinho.remove(viewHolder.getAdapterPosition());
//            carrinhoAdapter.notifyDataSetChanged();
//            setImageHolder(produtosCarrinho);
//            setPrecoTotalView();
//        }
//    };


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

    public void setImageHolder(List<Produtos> produtos){
        if(produtos.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            imagePlaceHolder.setVisibility(View.GONE);
            fab.show();
            frameLayout.setVisibility(View.VISIBLE);
//            deleteMenu.setVisible(true);
        }
        else {
            fab.hide();
            recyclerView.setVisibility(View.GONE);
            imagePlaceHolder.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
//            deleteMenu.setVisible(false);
        }
    }
}
