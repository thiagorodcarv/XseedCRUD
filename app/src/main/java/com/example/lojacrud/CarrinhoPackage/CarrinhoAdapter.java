package com.example.lojacrud.CarrinhoPackage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;


import com.example.lojacrud.Produtos;
import com.example.lojacrud.R;

import java.io.File;
import java.util.List;

import static com.example.lojacrud.CadastroActivity.filePath;

public class CarrinhoAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Produtos> produtos;
    private CarrinhoListener carrinhoListener;
    private Boolean teste;

    public CarrinhoAdapter(Context context, List<Produtos> produtos, CarrinhoListener carrinhoListener, Boolean teste) {
        this.context = context;
        this.produtos = produtos;
        this.carrinhoListener = carrinhoListener;
        this.teste = teste;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.carrinho_adapter,parent,false);
        CarrinhoHolder holder = new CarrinhoHolder(view,carrinhoListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        CarrinhoHolder holder = (CarrinhoHolder) viewHolder;
        Produtos produto = produtos.get(position);
        holder.nome.setText(produto.getNome());
        if (produto.getChecked()){
            holder.checkBox.setChecked(true);
        }
        if (teste){
            holder.deleteItem.setVisibility(View.GONE);
            holder.checkBox.setVisibility(View.VISIBLE);
        }
        else {
            holder.checkBox.setVisibility(View.GONE);
            holder.deleteItem.setVisibility(View.VISIBLE);
        }
        Double precoTotal;
        if ((produto.getPrecoDesconto()==null)||(produto.getPrecoDesconto().equals(""))){
            precoTotal = Double.valueOf(produto.getPreco());
        }
        else {
            precoTotal = Double.valueOf(produto.getPreco()) - Double.valueOf(produto.getPrecoDesconto());
        }
        holder.preco.setText(precoTotal.toString());
        holder.precoTotal.setText(Double.toString(precoTotal*produto.getQuantidade()));
        holder.qtd.setText(produto.getQuantidade().toString());
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


    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

}
