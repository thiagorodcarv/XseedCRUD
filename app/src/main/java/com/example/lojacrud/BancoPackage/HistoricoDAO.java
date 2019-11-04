package com.example.lojacrud.BancoPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lojacrud.Produtos;

import java.util.ArrayList;
import java.util.List;

public class HistoricoDAO {
    Conexao conexao;
    SQLiteDatabase banco;

    public HistoricoDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public void inserirVenda(Produtos produtos){
        ContentValues values = new ContentValues();
        values.put("id_produto",(Integer) produtos.getId());
        if ((produtos.getPrecoDesconto()==null)||produtos.getPrecoDesconto().equals("")){
            values.put("preco_cada",produtos.getPreco().toString());
        }
        else {
            Integer precoTotal = Integer.valueOf(produtos.getPreco()) - Integer.valueOf(produtos.getPrecoDesconto());
            values.put("preco_cada",precoTotal.toString());
        }

        values.put("quantidade",(Integer) produtos.getQuantidade());
        banco.insert("vendas",null,values);
    }

    public List<Produtos> recuperarHistorico(){
        List<Produtos> produtos = new ArrayList<Produtos>();
        Cursor cursor = banco.query("vendas",new String[]{"id_produto","data","preco_cada","quantidade"},null,
                null,null,null,null);
        while (cursor.moveToNext()){
            Produtos p = new Produtos();
            p.setId(cursor.getInt(0));
            p.setDataCompra(cursor.getString(1));
            p.setPreco(cursor.getString(2));
            p.setQuantidade(cursor.getInt(3));
            produtos.add(p);
        }
        return produtos;
    }

    public List<Produtos> recuperarProdutosHistorico(){
        Cursor cursor = banco.rawQuery("SELECT p.nome as produto_nome, p.id as produto_id, p.departamento as produto_departamento," +
                "v.data as data_venda, v.preco_cada as preco_venda, v.quantidade as quantidade_venda" +
                " FROM vendas v LEFT JOIN produtos p ON p.id = v.id_produto ORDER BY data_venda DESC",null);
        List<Produtos> produtos = new ArrayList<Produtos>();
        while (cursor.moveToNext()){
            Produtos p = new Produtos();
            p.setNome(cursor.getString(cursor.getColumnIndex("produto_nome")));
            p.setId(cursor.getInt(cursor.getColumnIndex("produto_id")));
            p.setDepartamento(cursor.getString(cursor.getColumnIndex("produto_departamento")));
            p.setDataCompra(cursor.getString(cursor.getColumnIndex("data_venda")));
            p.setPreco(cursor.getString(cursor.getColumnIndex("preco_venda")));
            p.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade_venda")));
            produtos.add(p);
        }
        return produtos;
    }

}
