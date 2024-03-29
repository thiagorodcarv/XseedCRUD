package com.example.lojacrud.BancoPackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Conexao extends SQLiteOpenHelper {
    public static final String name = "loja.db";
    public static final int version = 3;

    public Conexao(@Nullable Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table produtos(id integer primary key autoincrement," +
                "nome varchar(50),preco varchar(10),precodesconto varchar(10)," +
                "departamento varchar(70))");
        db.execSQL("create table vendas(id_produto integer," +
                "data datetime default current_timestamp,preco_cada varchar(10),quantidade integer," +
                "primary key (id_produto, data))");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table usuario(id integer primary key autoincrement," +
                "nome varchar(50) not null, login varchar(10) unique not null, email varchar(40) unique not null," +
                " senha varchar(20) not null, tipo_de_usuario integer not null)");
    }
}
