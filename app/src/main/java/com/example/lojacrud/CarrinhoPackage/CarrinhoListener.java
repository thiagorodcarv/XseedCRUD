package com.example.lojacrud.CarrinhoPackage;

import android.view.View;

public interface CarrinhoListener {
//    void quantidadeChanged(int position, String s);
    void deleteItem(int position);
    void selectQtd(int position, View view);
}
