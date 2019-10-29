package com.example.lojacrud.ProdutosTabPackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lojacrud.ClickOnItems;
import com.example.lojacrud.ListDataModel;
import com.example.lojacrud.Produtos;
import com.example.lojacrud.R;

import java.io.File;
import java.util.List;
import java.util.Map;

import static com.example.lojacrud.CadastroActivity.filePath;
//import static com.example.lojacrud.MainActivity.produtosSelecionados;

public class ProdutosAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Produtos> produtos;
    private ClickOnItems clickOnItems;
    private ListDataModel pViewModel;
    private Map<Integer, Boolean> mapaChecks;

    public ProdutosAdapter(Context context, List<Produtos> produtos, ClickOnItems clickOnItems,ListDataModel pViewModel) {
        this.context = context;
        this.produtos = produtos;
        this.clickOnItems = clickOnItems;
        this.pViewModel = pViewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.produtos_adapter,parent,false);
        ProdutosViewHolder holder = new ProdutosViewHolder(view, clickOnItems);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ProdutosViewHolder holder = (ProdutosViewHolder) viewHolder;
        Produtos produto = produtos.get(position);
        if ((pViewModel.mapaChecks!=null)&&pViewModel.mapaChecks.containsKey(produto.getId())){
            holder.checkBox.setChecked(pViewModel.mapaChecks.get(produto.getId()));
        }
        else {
            holder.checkBox.setChecked(false);
        }
        holder.nome.setText(produto.getNome());
        holder.departamento.setText(produto.getDepartamento());
        holder.preco.setText(produto.getPreco());
        File photo;
        photo = new File((context.getExternalFilesDir(filePath))+"/"+produto.getId());
        if(photo.exists()){
            Bitmap photoBitmap = BitmapFactory.decodeFile(photo.toString());
            Bitmap photoAux = Bitmap.createScaledBitmap(photoBitmap,photoBitmap.getWidth(),photoBitmap.getWidth(),false);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), photoAux);
            roundedBitmapDrawable.setCircular(true);
            //roundedBitmapDrawable.setTargetDensity(photo.getDensity());
            holder.imagem.setImageDrawable(roundedBitmapDrawable);
        }
        else {
            Resources res = context.getResources();
            Bitmap src = BitmapFactory.decodeResource(res, R.drawable.waifu);
            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, src);
            dr.setCornerRadius(Math.max(src.getWidth(), src.getHeight()) / 2.0f);
            holder.imagem.setImageDrawable(dr);
        }


//        for(Produtos p : pViewModel.produtosSelecionados){
//            if(p.getId()==produto.getId()){
//                holder.checkBox.setChecked(true);
//                break;
//            }
//        }


    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }
}
