package com.example.lojacrud;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lojacrud.BancoPackage.ProdutosDAO;

import java.io.File;

import static com.example.lojacrud.CadastroActivity.filePath;

public class PopUp extends AppCompatActivity {

    private TextView nome;
    private ImageView perfil;
    private Produtos produto;
    private ListDataModel pViewModel;
    private ProdutosDAO dao;
    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        Intent intent = getIntent();
        produto = (Produtos) intent.getSerializableExtra("produto");
        pViewModel = ViewModelProviders.of(this).get(ListDataModel.class);
        dao = new ProdutosDAO(this);
        layout =  findViewById(R.id.pop_up_back);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;


        nome = findViewById(R.id.pop_nome_produto);
        perfil = findViewById(R.id.photo_produto_popup);

        nome.setText(produto.getNome());
        File photo;
        photo = new File((getExternalFilesDir(filePath))+"/"+produto.getId());
        if(photo.exists()){
            Bitmap photoBitmap = BitmapFactory.decodeFile(photo.toString());
            //Bitmap photoAux = Bitmap.createScaledBitmap(photoBitmap,photoBitmap.getWidth(),photoBitmap.getWidth(),false);
            //RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), photoAux);
            //roundedBitmapDrawable.setTargetDensity(photo.getDensity());
            perfil.setImageBitmap(photoBitmap);
        }

        getWindow().setLayout((int) (width*.8),(int)(height*.58));

    }


    public void excluir(View view){
        final Produtos produtosExcluir = produto;

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Atenção")
                .setMessage("Deseja excluir o produto?")
                .setNegativeButton("NÃO",null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pViewModel.produtosBanco.remove(produtosExcluir);
                        dao.excluir(produtosExcluir);
                        finish();
                    }
                }).create();
        dialog.show();
        finish();
    }

    public void atualizar(View view){
        final Produtos produtoAtualizar = produto;
        Intent intent = new Intent(this,CadastroActivity.class);
        intent.putExtra("produto",produtoAtualizar);
        startActivityForResult(intent,1);
    }

    public void detalhes(View view){
        final Produtos produtoDetalhes = produto;
        Intent intent = new Intent(this,DetalhesActivity.class);
        intent.putExtra("produto",produtoDetalhes);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        produto = (Produtos) data.getSerializableExtra("result");
        nome.setText(produto.getNome());
        File photo;
        photo = new File((getExternalFilesDir(filePath))+"/"+produto.getId());
        if(photo.exists()){
            Bitmap photoBitmap = BitmapFactory.decodeFile(photo.toString());
            perfil.setImageBitmap(photoBitmap);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pViewModel.produtosBanco.clear();

    }
}
