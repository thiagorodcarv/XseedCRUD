package com.example.lojacrud;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListDataModel extends ViewModel {

    public List<Produtos> produtosBanco = new ArrayList<>();
    public List<Produtos> produtosSelecionados = new ArrayList<>();
    public static List<Produtos> produtosHistorico = new ArrayList<>();
    public Map<Integer, Boolean> mapaChecks = new HashMap<>();
}
