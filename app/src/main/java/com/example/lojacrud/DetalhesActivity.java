package com.example.lojacrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import static com.example.lojacrud.CadastroActivity.filePath;

public class DetalhesActivity extends AppCompatActivity {

    Produtos produto;
    private TextView nome;
    private TextView id;
    private TextView departamento;
    private TextView preco;
    private TextView desconto;
    private TextView precoAtual;
    private TextView txtViewDesconto;
    private TextView txtViewAtual;
    private ImageView photoDetalhes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        Intent intent = getIntent();
        produto = (Produtos) intent.getSerializableExtra("produto");

        id = findViewById(R.id.produto_detalhes_id);
        nome = findViewById(R.id.produto_detalhes_nome);
        departamento = findViewById(R.id.produto_detalhes_dep);
        preco = findViewById(R.id.produto_detalhes_preco);
        desconto = findViewById(R.id.produto_detalhes_desconto);
        precoAtual = findViewById(R.id.produto_detalhes_atual);
        txtViewAtual = findViewById(R.id.produto_detalhes_tvw_pAtual);
        txtViewDesconto = findViewById(R.id.produto_detalhes_tvw_Desc);
        photoDetalhes = findViewById(R.id.produto_detalhes_photo);

        id.setText(produto.getId().toString());
        nome.setText(produto.getNome());
        departamento.setText(produto.getDepartamento());
        preco.setText(produto.getPreco());
        if(!produto.getPrecoDesconto().equals("")){
            desconto.setVisibility(View.VISIBLE);
            desconto.setText(produto.getPrecoDesconto());
            txtViewDesconto.setVisibility(View.VISIBLE);
            txtViewAtual.setVisibility(View.VISIBLE);
            Double totalAtual;
            totalAtual = Double.valueOf(produto.getPreco()) - Double.valueOf(produto.getPrecoDesconto());
            precoAtual.setVisibility(View.VISIBLE);
            precoAtual.setText(totalAtual.toString());
        }
        File photo;
        photo = new File((getExternalFilesDir(filePath))+"/"+produto.getId());
        if(photo.exists()){
            Bitmap photoBitmap = BitmapFactory.decodeFile(photo.toString());
            //Bitmap photoAux = Bitmap.createScaledBitmap(photoBitmap,photoBitmap.getWidth(),photoBitmap.getWidth(),false);
            //RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), photoAux);
            //roundedBitmapDrawable.setTargetDensity(photo.getDensity());
            photoDetalhes.setImageBitmap(photoBitmap);
        }
        final Intent intent1 = new Intent(this, ImagemDetalhesPopUp.class);
        photoDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1.putExtra("imagem",produto.getId());
                startActivity(intent1);
            }
        });
    }
}
