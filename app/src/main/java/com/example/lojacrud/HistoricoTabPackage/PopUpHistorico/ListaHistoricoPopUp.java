package com.example.lojacrud.HistoricoTabPackage.PopUpHistorico;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lojacrud.BancoPackage.ProdutosDAO;
import com.example.lojacrud.ItemHistoricoPackage.ItemHistoricoAdapter;
import com.example.lojacrud.ListDataModel;
import com.example.lojacrud.Produtos;
import com.example.lojacrud.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.lojacrud.CadastroActivity.filePath;

public class ListaHistoricoPopUp extends AppCompatActivity {

    private List<Produtos> produtosHistorico;
    private List<Produtos> produtosHistoricoView = new ArrayList<>();
    private String dataCompra;
    // private ListDataModel pViewModel;
    private RecyclerView recyclerView;
    private PopUpHistoricoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_historico_popup);
        recyclerView = findViewById(R.id.recyclerview_listahistorico_popup);
        Intent intent = getIntent();
        dataCompra = intent.getStringExtra("data");
//        pViewModel = ViewModelProviders.of(this).get(ListDataModel.class);
        produtosHistorico = ListDataModel.produtosHistorico;
        produtosHistoricoView = recuperaItensPorData(produtosHistorico,dataCompra);
        adapter = new PopUpHistoricoAdapter(this,produtosHistoricoView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;


        getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);


//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_ATTACHED_IN_DECOR);


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
}