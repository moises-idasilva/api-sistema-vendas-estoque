package com.sigep.estoquevendassys.service;

import com.sigep.estoquevendassys.model.Produto;
import com.sigep.estoquevendassys.repository.ProdutoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private static final Logger log = LoggerFactory.getLogger(ProdutoService.class);

    private final ProdutoDao produtoDao;

    public ProdutoService(ProdutoDao produtoDao) {
        this.produtoDao = produtoDao;
    }

    public List<Produto> list() {
        return produtoDao.list();
    }

    public Optional<Produto> get(int id) {
        return produtoDao.get(id);
    }

    public void create(Produto produto) {

        validarQuantidade(produto);

        produtoDao.create(produto);

    }

    public void update(Produto produto, int id) {
        produtoDao.update(produto, id);
    }

    public void delete(int id) throws DataIntegrityViolationException{

        try {
            produtoDao.delete(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("O produto está vinculado a uma ou mais vendas e não pode ser " +
                    "excluído. Por favor, remova as vendas associadas antes de tentar excluir o produto.");
        }
    }

    public List<Produto> listProductByVendaId(int vendaId) {
        return produtoDao.listProductByVendaId(vendaId);
    }

    private void validarQuantidade(Produto produto) throws IllegalArgumentException {

        log.info("Validando quantidade positiva...");

        if (produto.getQuantidadeDisponivel() <= 0) {
            throw new IllegalArgumentException("A quantidade de produto deve ser maior que zero.");
        }
    }

}
