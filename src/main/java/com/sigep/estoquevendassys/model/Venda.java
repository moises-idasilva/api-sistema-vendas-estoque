package com.sigep.estoquevendassys.model;

import java.math.BigDecimal;

public class Venda {

    private int id;
    private String cliente;
    private BigDecimal valorTotal;
    private int quantidade;

    public Venda() {
    }

    public Venda(int id, String cliente, BigDecimal valorTotal, int quantidade) {
        this.id = id;
        this.cliente = cliente;
        this.valorTotal = valorTotal;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


}
