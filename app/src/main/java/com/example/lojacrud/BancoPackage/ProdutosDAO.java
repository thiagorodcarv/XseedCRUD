package com.example.lojacrud.BancoPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lojacrud.Produtos;

import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {
    Conexao conexao;
    SQLiteDatabase banco;

    public ProdutosDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserirProduto(Produtos produtos){
        ContentValues values = new ContentValues();
        values.put("nome",produtos.getNome());
        values.put("preco",produtos.getPreco());
        values.put("precodesconto",produtos.getPrecoDesconto());
        values.put("departamento", produtos.getDepartamento());
        return banco.insert("produtos",null,values);
    }

    public List<Produtos> recuperarProdutos(){
        List<Produtos> produtos = new ArrayList<>();
        Cursor cursor = banco.query("produtos",new String[]{"id","nome","preco","precodesconto","departamento"},
                null,null,null,null,null);
        while (cursor.moveToNext()){
            Produtos p = new Produtos();
            p.setId(cursor.getInt(0));
            p.setNome(cursor.getString(1));
            p.setPreco(cursor.getString(2));
            p.setPrecoDesconto(cursor.getString(3));
            p.setDepartamento(cursor.getString(4));
            produtos.add(p);
        }
        return produtos;
    }

    public void excluir(Produtos produtos){
        banco.delete("produtos","id = ?",new String[]{produtos.getId().toString()});
    }

    public void atualizar(Produtos produtos){
        ContentValues values = new ContentValues();
        values.put("nome",produtos.getNome());
        values.put("preco",produtos.getPreco());
        values.put("precodesconto",produtos.getPrecoDesconto());
        values.put("departamento", produtos.getDepartamento());
        banco.update("produtos",values,"id=?",new String[]{produtos.getId().toString()});
    }


    public Produtos selectProduto(Produtos produto){
        Produtos p = new Produtos();
        Cursor cursor = banco.rawQuery("SELECT id,nome,preco,precodesconto,departamento FROM " +
                "produtos WHERE id = ?",new String[]{produto.getId().toString()});
        cursor.moveToFirst();
        p.setId(cursor.getInt(0));
        p.setNome(cursor.getString(1));
        p.setPreco(cursor.getString(2));
        p.setPrecoDesconto(cursor.getString(3));
        p.setDepartamento(cursor.getString(4));
        return p;
    }


}

