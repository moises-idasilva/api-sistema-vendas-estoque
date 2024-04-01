package com.sigep.estoquevendassys.repository;

import com.sigep.estoquevendassys.model.Produto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProdutoDao implements DAO<Produto> {

    private static final Logger log = LoggerFactory.getLogger(ProdutoDao.class);
    private JdbcTemplate jdbcTemplate;

    RowMapper<Produto> produtoRowMapper = (rs, rowNum) -> {
        Produto produto = new Produto();
        produto.setId(rs.getInt("id"));
        produto.setNome(rs.getString("nome"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setQuantidadeDisponivel(rs.getInt("quantidade_disponivel"));
        produto.setValorUnitario(rs.getBigDecimal("valor_unitario"));

        return produto;
    };

    public ProdutoDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Produto> list() {

        String sql = "SELECT id, nome, descricao, quantidade_disponivel, valor_unitario FROM produto ORDER BY nome";
        return jdbcTemplate.query(sql, produtoRowMapper);

    }

    @Override
    public Optional<Produto> get(int id) {

        String sql = "SELECT * FROM produto WHERE id = ?";
        Produto produto = null;
        try {
            produto = jdbcTemplate.queryForObject(sql, new Object[]{id}, produtoRowMapper);
        } catch (Exception ex) {
            log.error("Error getting product with id {}: {}", id, ex.getMessage());
        }

        return Optional.ofNullable(produto);

    }

    public List<Produto> listProductByVendaId(int vendaId) {

        String sql = "SELECT p.* FROM Produto p INNER JOIN VendaProduto vp ON p.id = vp.id_produto WHERE vp.id_venda = ?";

        return jdbcTemplate.query(sql, new Object[]{vendaId}, new BeanPropertyRowMapper<>(Produto.class));
    }

    @Override
    public void create(Produto produto) {

        String sql = "INSERT INTO produto (nome, descricao, quantidade_disponivel, valor_unitario) VALUES (?, ?, ?, ?)";
        int insert = jdbcTemplate.update(sql, produto.getNome(), produto.getDescricao(), produto.getQuantidadeDisponivel(),
                produto.getValorUnitario());
        if (insert == 1) {
            log.info("Novo produto registrado: {}", produto.getNome());
        }
    }

    @Override
    public void update(Produto produto, int id) {

        String sql = "UPDATE produto SET nome = ?, descricao = ?, quantidade_disponivel = ?, valor_unitario = ? WHERE id = ?";
        int update = jdbcTemplate.update(sql, produto.getNome(), produto.getDescricao(), produto.getQuantidadeDisponivel(),
                produto.getValorUnitario(), id);
        if (update == 1) {
            log.info("Produto atualizado");
        }

    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM produto WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }

    public void addProductBack(int id, int n) {

        String sql = "UPDATE Produto SET quantidade_disponivel = quantidade_disponivel + ? WHERE id = ?";
        jdbcTemplate.update(sql, n, id);

    }

    public void subtractQuantityFromProduct(int id, int n) {

        String sql = "UPDATE Produto SET quantidade_disponivel = quantidade_disponivel - ? WHERE id = ?";
        jdbcTemplate.update(sql, n, id);

    }

    public int getQuatidadeDisponivel(int id) {
        String sql = "SELECT quantidade_disponivel FROM produto WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id);
    }

}
