package com.example.lojacrud.BancoPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.lojacrud.Produtos;
import com.example.lojacrud.Usuario.Usuario;

public class UsuarioDAO {

    Conexao conexao;
    SQLiteDatabase banco;

    public UsuarioDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserirProduto(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put("nome",usuario.getNome());
        values.put("login",usuario.getLogin());
        values.put("email",usuario.getEmail());
        values.put("senha", usuario.getSenha());
        return banco.insert("produtos",null,values);
    }
}
