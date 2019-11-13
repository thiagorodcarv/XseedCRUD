package com.example.lojacrud.ProdutosTabPackage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lojacrud.Auxiliar;
import com.example.lojacrud.ClickOnItems;
import com.example.lojacrud.ListDataModel;
import com.example.lojacrud.PopUp;
import com.example.lojacrud.Produtos;
import com.example.lojacrud.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import static com.example.lojacrud.MainActivity.produtosSelecionados;

public class ProdutosTab extends Fragment implements Auxiliar, ClickOnItems {

    private View rootView;
    private List<Produtos> produtosView = new ArrayList<>();
    private List<Produtos> produtosSD = new ArrayList<>();
    private List<Produtos> produtos;
    private RecyclerView recyclerView;
    private ImageView imagePlaceHolder;
    private Map<Integer, Boolean> mapaChecks;
    private ProdutosAdapter adapter;
    private ListDataModel pViewModel;


    public ProdutosTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_produtos_tab, container, false);
        recyclerView = rootView.findViewById(R.id.lista_produtos);
        pViewModel = ViewModelProviders.of(getActivity()).get(ListDataModel.class);
//        dao = new ProdutosDAO(getActivity());
//        produtos = dao.recuperarProdutos();
        imagePlaceHolder = rootView.findViewById(R.id.imagePlaceHolderProdutos);
        produtos = pViewModel.produtosBanco;
        for (Produtos p : produtos){
            if((p.getPrecoDesconto().equals(""))){
                produtosSD.add(p);
            }
        }
        produtosView.addAll(produtosSD);
        setImageHolder(produtosView);
        adapter = new ProdutosAdapter(getActivity(),produtosView,this,pViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.notifyDataSetChanged();

        return rootView;
    }

    @Override
    public void onImgClickedListener(int position) {
        Produtos produto = produtosView.get(position);
        Intent intent = new Intent(getActivity(), PopUp.class);
        intent.putExtra("produto",produto);
        startActivity(intent);
    }

    @Override
    public void procuraProduto(String nome){
        produtosView.clear();
        for(Produtos p : produtosSD){
            if(p.getNome().toLowerCase().contains(nome)){
                produtosView.add(p);
            }
        }
        adapter.notifyDataSetChanged();
        setImageHolder(produtosView);
    }

    @Override
    public void onResume() {
        super.onResume();
        produtos = pViewModel.produtosBanco;
        produtosView.clear();
        produtosSD.clear();
        for (Produtos p : produtos){
            if((p.getPrecoDesconto().equals(""))){
                produtosSD.add(p);
            }
        }
        produtosView.addAll(produtosSD);
        adapter.notifyDataSetChanged();
        setImageHolder(produtosView);
    }

    @Override
    public void onCheckListener(int position, boolean isChecked) {
        if (!isChecked){
            pViewModel.produtosSelecionados.remove(produtosView.get(position));
            //Toast.makeText(getActivity(), produtosView.get(position).getNome()+" Removido", Toast.LENGTH_SHORT).show();

        }
        else {
            if(!(pViewModel.produtosSelecionados.contains(produtosView.get(position)))){
                pViewModel.produtosSelecionados.add(produtosView.get(position));
                //Toast.makeText(getActivity(), produtosView.get(position).getNome()+" Adicionado", Toast.LENGTH_SHORT).show();
            }

        }

        pViewModel.mapaChecks.put(produtosView.get(position).getId(),isChecked);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        pViewModel = ViewModelProviders.of(getActivity()).get(ListDataModel.class);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

//    public boolean containsId(Produtos produto, List<Produtos> produtos){
//        if (produtos.size()>0){
//            for (Produtos p : produtos) {
//                if (p.getId() == produto.getId()) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    public void setImageHolder(List<Produtos> produtos){
        if(produtos.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            imagePlaceHolder.setVisibility(View.GONE);
        }
        else {
            recyclerView.setVisibility(View.GONE);
            imagePlaceHolder.setVisibility(View.VISIBLE);
        }
    }

}
