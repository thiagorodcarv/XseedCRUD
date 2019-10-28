package com.example.lojacrud;

import java.io.Serializable;

public class Produtos implements Serializable {

    private Integer id;
    private String nome;
    private String preco;
    private String departamento;
    private String precoDesconto = null;
    private boolean checked = false;
    private Integer quantidade = 1;
    private String dataCompra;

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Produtos(Integer id, String nome, String preco, String departamento) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.departamento = departamento;
    }

    public Produtos(Integer id, String nome, String preco, String departamento, String precoDesconto) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.departamento = departamento;
        this.precoDesconto = precoDesconto;
    }

    public Produtos(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPrecoDesconto() {
        return precoDesconto;
    }

    public void setPrecoDesconto(String precoDesconto) {
        this.precoDesconto = precoDesconto;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
