package com.sigep.estoquevendassys.dto;

import com.sigep.estoquevendassys.model.Produto;
import com.sigep.estoquevendassys.model.Venda;

import java.util.List;

public class VendaProdutoDTO {
    private Venda venda;
    private List<Produto> produtos;

    public VendaProdutoDTO(Venda venda, List<Produto> produtos) {
        this.venda = venda;
        this.produtos = produtos;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
