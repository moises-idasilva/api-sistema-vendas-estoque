package com.sigep.estoquevendassys.service;

import com.sigep.estoquevendassys.model.Produto;
import com.sigep.estoquevendassys.model.Venda;
import com.sigep.estoquevendassys.repository.ProdutoDao;
import com.sigep.estoquevendassys.repository.VendaDao;
import com.sigep.estoquevendassys.repository.VendaProdutoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    private static final Logger log = LoggerFactory.getLogger(VendaService.class);
    private final VendaDao vendaDao;
    private final ProdutoDao produtoDao;
    private final VendaProdutoDao vendaProdutoDao;

    public VendaService(VendaDao vendaDao, ProdutoDao produtoDao, VendaProdutoDao vendaProdutoDao) {
        this.vendaDao = vendaDao;
        this.produtoDao = produtoDao;
        this.vendaProdutoDao = vendaProdutoDao;
    }

    public List<Venda> list() {
        return vendaDao.list();
    }

    public Optional<Venda> get(int id) {
        return vendaDao.get(id);
    }

    public void create(Venda venda, List<Produto> produtos) throws IllegalArgumentException{

        validarQuantidade(venda);
        validarQuantidadeProdutoDisponivel(venda, produtos);
        validarListaDeProdutos(produtos);

        vendaDao.create(venda, produtos);
    }

    public void update(Venda venda, int id) {

        vendaDao.update(venda, id);

    }

    public void delete(int id) {

        Optional<Venda> venda = vendaDao.get(id);

        if (venda.isPresent()) {
            List<Produto> produtoList = produtoDao.listProductByVendaId(id);

            for (Produto produto : produtoList) {
                produtoDao.addProductBack(produto.getId(), (venda.get().getQuantidade()) / produtoList.size());
            }

            vendaDao.delete(id);
        }

    }

    private void validarQuantidade(Venda venda) throws IllegalArgumentException {

        log.info("Validando quantidade positiva...");

        if (venda.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade da venda deve ser maior que zero.");
        }
    }

    private void validarQuantidadeProdutoDisponivel(Venda venda, List<Produto> produtos) throws IllegalArgumentException {

        log.info("Validando quantidade disponível do produto...");

        for (Produto produto : produtos) {
            int quantidadeDesejada = venda.getQuantidade();
            int quantidadeDisponivel = produtoDao.getQuatidadeDisponivel(produto.getId());

            if (quantidadeDisponivel < quantidadeDesejada) {
                throw new IllegalArgumentException("Quantidade indisponível do produto: " + produto.getNome());
            }
        }

    }

    private void validarListaDeProdutos(List<Produto> produtos) throws IllegalArgumentException {
        if (produtos.isEmpty()) {
            log.info("Validando lista de produtos...");
            throw new IllegalArgumentException("Precisa selecionar um ou mais produtos.");
        }
    }

}
