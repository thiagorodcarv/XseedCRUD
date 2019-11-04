package com.example.lojacrud;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lojacrud.BancoPackage.ProdutosDAO;

import java.io.File;
import java.io.FileOutputStream;

import static com.example.lojacrud.R.*;

public class CadastroActivity extends Activity {
    private static final int CAMERA_REQUEST = 1888;
    private EditText nome;
    private EditText preco;
    private EditText departamento;
    private EditText precodesconto;
    private ImageView imageView;
    private ProdutosDAO dao;
    private Bitmap photo = null;
    private boolean insert = true;
    private boolean transacaoConcluida = false;
    private Produtos produtos = null;
    static public String filePath = "MyFileStorage";
    File myExternalFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_cadastro);

        nome = findViewById(id.txt_Nome);
        preco = findViewById(id.txt_Preco);
        departamento = findViewById(id.txt_Departamento);
        precodesconto = findViewById(id.txt_Desconto);
        imageView = findViewById(id.imgFundo);
        dao = new ProdutosDAO(this);

        Resources res = getResources();
        Bitmap src = BitmapFactory.decodeResource(res, drawable.xseed);
        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(res, src);
        dr.setCornerRadius(Math.max(src.getWidth(), src.getHeight()) / 2.0f);
        imageView.setImageDrawable(dr);


        Intent intent = getIntent();
        if(intent.hasExtra("produto")){
            insert = false;
            produtos = (Produtos) intent.getSerializableExtra("produto");
            nome.setText(produtos.getNome());
            preco.setText(produtos.getPreco());
            departamento.setText(produtos.getDepartamento());
            precodesconto.setText(produtos.getPrecoDesconto());
            File photo;
            photo = new File((getExternalFilesDir(filePath))+"/"+produtos.getId());
            if(photo.exists()){
                Bitmap photoBitmap = BitmapFactory.decodeFile(photo.toString());
                //Bitmap photoAux = Bitmap.createScaledBitmap(photoBitmap,photoBitmap.getWidth(),photoBitmap.getWidth(),false);
                //RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), photoAux);
                //roundedBitmapDrawable.setTargetDensity(photo.getDensity());
                imageView.setImageBitmap(photoBitmap);
            }
        }

    }

    public void tirarFoto(View view){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            Bitmap photoAux = Bitmap.createScaledBitmap(photo,photo.getWidth(),photo.getWidth(),false);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), photoAux);
            roundedBitmapDrawable.setCircular(true);
            //roundedBitmapDrawable.setTargetDensity(photo.getDensity());
            imageView.setImageDrawable(roundedBitmapDrawable);
        }
    }

    public void salvar(View view){
        if (insert) {
            produtos = new Produtos();
            produtos.setNome(nome.getText().toString());
            produtos.setDepartamento(departamento.getText().toString());
            produtos.setPreco(preco.getText().toString());
            produtos.setPrecoDesconto(precodesconto.getText().toString());
            if(verificaString(produtos.getNome())||verificaString(produtos.getDepartamento())||verificaString(produtos.getPreco())){
                Toast.makeText(this, "Campo obrigatório não preenchido", Toast.LENGTH_SHORT).show();
            }
            else {
                long id = dao.inserirProduto(produtos);
                produtos.setId((int) id);
                transacaoConcluida = true;
                Toast.makeText(this, "produto inserido de id: " + produtos.getId(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            produtos.setNome(nome.getText().toString());
            produtos.setDepartamento(departamento.getText().toString());
            produtos.setPreco(preco.getText().toString());
            produtos.setPrecoDesconto(precodesconto.getText().toString());
            if(verificaString(produtos.getNome())||verificaString(produtos.getDepartamento())||verificaString(produtos.getPreco())){
                Toast.makeText(this, "Campo obrigatório não preenchido", Toast.LENGTH_SHORT).show();
            }
            else {
                dao.atualizar(produtos);
                transacaoConcluida = true;
                Toast.makeText(this, "produto atualizado de id: " + produtos.getId(), Toast.LENGTH_SHORT).show();
            }
        }


        if (photo != null && transacaoConcluida){
            createDirectoryAndSaveFile(photo,Long.toString(produtos.getId()));
            finish();
        }
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {

        try {
            myExternalFile = new File(getExternalFilesDir(filePath), fileName);
            FileOutputStream out = new FileOutputStream(myExternalFile);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verificaString(String s){
        if(s.trim().length()==0){
            return true;
        }
        else return false;
    }

}

