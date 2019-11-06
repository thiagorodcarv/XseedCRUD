package com.example.lojacrud.CarrinhoPackage;

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

import com.example.lojacrud.Produtos;
import com.example.lojacrud.R;

import java.io.File;
import java.util.List;

import static com.example.lojacrud.CadastroActivity.filePath;

public class CarrinhoAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Produtos> produtos;
    private CarrinhoListener carrinhoListener;

    public CarrinhoAdapter(Context context, List<Produtos> produtos, CarrinhoListener carrinhoListener) {
        this.context = context;
        this.produtos = produtos;
        this.carrinhoListener = carrinhoListener;
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
        holder.departamento.setText(produto.getDepartamento());
        Double precoTotal;
        if ((produto.getPrecoDesconto()==null)||(produto.getPrecoDesconto().equals(""))){
            precoTotal = Double.valueOf(produto.getPreco());
        }
        else {
            precoTotal = Double.valueOf(produto.getPreco()) - Double.valueOf(produto.getPrecoDesconto());
        }
        holder.preco.setText(precoTotal.toString());
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

        holder.quantidadeProdutos.setText(produto.getQuantidade().toString());
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

}
