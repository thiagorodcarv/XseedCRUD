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
    private Produtos produto = null;
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
            produto = (Produtos) intent.getSerializableExtra("produto");
            nome.setText(produto.getNome());
            preco.setText(produto.getPreco());
            departamento.setText(produto.getDepartamento());
            precodesconto.setText(produto.getPrecoDesconto());
            File photo;
            photo = new File((getExternalFilesDir(filePath))+"/"+ produto.getId());
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

    @Override
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
            produto = new Produtos();
            produto.setNome(nome.getText().toString());
            produto.setDepartamento(departamento.getText().toString());
            produto.setPreco(preco.getText().toString());
            produto.setPrecoDesconto(precodesconto.getText().toString());
            if(verificaString(produto.getNome())||verificaString(produto.getDepartamento())||verificaString(produto.getPreco())){
                Toast.makeText(this, "Campo obrigatório não preenchido", Toast.LENGTH_SHORT).show();
            }
            else if ((produto.getPrecoDesconto()!=null)&&(verificaDesconto(produto.getPreco(),produto.getPrecoDesconto()))){
                Toast.makeText(this, "Valor de desconto deve ser menor que o de preço", Toast.LENGTH_SHORT).show();
            }
            else {
                long id = dao.inserirProduto(produto);
                produto.setId((int) id);
                transacaoConcluida = true;
                Toast.makeText(this, "produto inserido de id: " + produto.getId(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            produto.setNome(nome.getText().toString());
            produto.setDepartamento(departamento.getText().toString());
            produto.setPreco(preco.getText().toString());
            produto.setPrecoDesconto(precodesconto.getText().toString());
            if(verificaString(produto.getNome())||verificaString(produto.getDepartamento())||verificaString(produto.getPreco())){
                Toast.makeText(this, "Campo obrigatório não preenchido", Toast.LENGTH_SHORT).show();
            }
            else if (verificaDesconto(produto.getPreco(),produto.getPrecoDesconto())){
                Toast.makeText(this, "Valor de desconto deve ser menor que o de preço", Toast.LENGTH_SHORT).show();
            }
            else {
                dao.atualizar(produto);
                transacaoConcluida = true;
                Toast.makeText(this, "produto atualizado de id: " + produto.getId(), Toast.LENGTH_SHORT).show();
            }
        }


        if (transacaoConcluida){
            if (photo != null){
                createDirectoryAndSaveFile(photo,Long.toString(produto.getId()));
            }
            if (insert){
                finish();
            }
            else {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", produto);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
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

    public boolean verificaDesconto(String preco, String desconto){
        if ((preco==null)||(desconto.equals(""))){
            return false;
        }
        else if(Double.valueOf(preco)<Double.valueOf(desconto)){
            return true;
        }
        else return false;
    }

}

