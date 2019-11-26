package com.example.lojacrud.HistoricoTabPackage.PopUpHistorico;

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

import com.example.lojacrud.ItemHistoricoPackage.ItemHistoricoViewHolder;
import com.example.lojacrud.Produtos;
import com.example.lojacrud.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.lojacrud.CadastroActivity.filePath;

public class PopUpHistoricoAdapter extends RecyclerView.Adapter {

    Context context;
    List<Produtos> produtos;

    public PopUpHistoricoAdapter(Context context, List<Produtos> produtos){
        this.context = context;
        this.produtos = produtos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lista_historico_popup_adapter,parent,false);
        ListaHistoricoPopUpHolder holder = new ListaHistoricoPopUpHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ListaHistoricoPopUpHolder holder = (ListaHistoricoPopUpHolder) viewHolder;
        Produtos produto = produtos.get(position);
        if (produto.getNome()==null){
            holder.nome.setText("Produto indisponivel");
        }
        else {
            holder.nome.setText(produto.getNome());
        }
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
            Bitmap src = BitmapFactory.decodeResource(res, R.drawable.indisponivel);
            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, src);
            dr.setCornerRadius(Math.max(src.getWidth(), src.getHeight()) / 2.0f);
            holder.imagem.setImageDrawable(dr);
        }
        holder.quantidade.setText(produto.getQuantidade().toString());
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }
}
