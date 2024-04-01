package com.sigep.estoquevendassys.repository;

import com.sigep.estoquevendassys.model.Produto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VendaProdutoDao {

    private final JdbcTemplate jdbcTemplate;

    public VendaProdutoDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Produto> listProdutosByVendaID(int vendaId) {
        String sql = "SELECT p.id, p.nome, p.descricao, p.quantidade_disponivel, p.valor_unitario " +
                     "FROM Produto p " +
                     "JOIN VendaProduto vp ON p.id = vp.id_produto " +
                     "WHERE vp.id_venda = ?";
        
        return jdbcTemplate.query(sql, new Object[]{vendaId}, (rs, rowNum) -> {
            Produto produto = new Produto();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setDescricao(rs.getString("descricao"));
            produto.setQuantidadeDisponivel(rs.getInt("quantidade_disponivel"));
            produto.setValorUnitario(rs.getBigDecimal("valor_unitario"));
            return produto;
        });
    }
}
