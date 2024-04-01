package com.sigep.estoquevendassys.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Produto {

    private int id;
    private String nome;
    private String descricao;
    private int quantidadeDisponivel;
    private BigDecimal valorUnitario;

    public Produto() {
    }

    public Produto(String nome, String descricao, int quantidadeDisponivel, BigDecimal valorUnitario) {
        this.nome = nome;
        this.descricao = descricao;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.valorUnitario = valorUnitario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", quantidadeDisponivel=" + quantidadeDisponivel +
                ", valorUnitario=" + valorUnitario +
                '}';
    }
}
