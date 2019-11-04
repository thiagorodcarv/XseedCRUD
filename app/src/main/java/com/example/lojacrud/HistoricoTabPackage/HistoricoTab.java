package com.example.lojacrud.HistoricoTabPackage;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lojacrud.ItemHistoricoPackage.ItemHistoricoActivity;
import com.example.lojacrud.ListDataModel;
import com.example.lojacrud.MainActivity;
import com.example.lojacrud.Produtos;
import com.example.lojacrud.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.lojacrud.MainActivity.fab;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoricoTab extends Fragment implements HistoricoListener{

    private View rootView;
    private RecyclerView recyclerView;
    private ListDataModel pViewModel;
    private List<String> datasDeCompras;
    private List<String> datasDeComprasView = new ArrayList<>();
    private HistoricoAdapter adapter;


    public HistoricoTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_historico_tab, container, false);
        recyclerView = rootView.findViewById(R.id.lista_item_historico);
        pViewModel = ViewModelProviders.of(getActivity()).get(ListDataModel.class);
        datasDeCompras = recuperaDatas(pViewModel.produtosHistorico);
        datasDeComprasView.addAll(datasDeCompras);
        adapter = new HistoricoAdapter(getActivity(),datasDeComprasView,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter.notifyDataSetChanged();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        datasDeComprasView.clear();
        datasDeCompras = recuperaDatas(pViewModel.produtosHistorico);
        datasDeComprasView.addAll(datasDeCompras);
        adapter.notifyDataSetChanged();
    }

    public List<String> recuperaDatas(List<Produtos> produtos){
        List<String> datasDeCompras = new ArrayList<>();

        for (Produtos p : produtos){
            if(!(datasDeCompras.contains(p.getDataCompra()))){
               datasDeCompras.add(p.getDataCompra());
            }
        }

        return datasDeCompras;
    }

    @Override
    public void onCardClick(int position) {
        Intent intent = new Intent(getContext(), ItemHistoricoActivity.class);
        intent.putExtra("data",datasDeComprasView.get(position));
        startActivity(intent);
    }

}
