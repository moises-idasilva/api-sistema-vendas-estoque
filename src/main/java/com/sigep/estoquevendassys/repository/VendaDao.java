package com.sigep.estoquevendassys.repository;

import com.sigep.estoquevendassys.model.Produto;
import com.sigep.estoquevendassys.model.Venda;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class VendaDao implements DAO<Venda> {

    private static final Logger log = LoggerFactory.getLogger(VendaDao.class);
    private JdbcTemplate jdbcTemplate;

    private final ProdutoDao produtoDao;

    RowMapper<Venda> vendaRowMapper = (rs, rowNum) -> {
        Venda venda = new Venda();
        venda.setId(rs.getInt("id"));
        venda.setCliente(rs.getString("cliente"));
        venda.setValorTotal(rs.getBigDecimal("valor_total"));
        venda.setQuantidade(rs.getInt("quantidade"));

        return venda;
    };

    public VendaDao(JdbcTemplate jdbcTemplate, ProdutoDao produtoDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.produtoDao = produtoDao;
    }

    @Override
    public List<Venda> list() {

        String sql = "SELECT id, cliente, valor_total, quantidade FROM venda ORDER BY cliente";

        return jdbcTemplate.query(sql, vendaRowMapper);

    }

    @Override
    public Optional<Venda> get(int id) {

        String sql = "SELECT id, cliente, valor_total, quantidade FROM venda WHERE id = ?";
        Venda venda = null;

        try {
            venda = jdbcTemplate.queryForObject(sql, new Object[]{id}, vendaRowMapper);
        } catch (DataAccessException ex) {
            log.error("Error getting product with id {}: {}", id, ex.getMessage());
        }

        return Optional.ofNullable(venda);
    }


    public Venda getVendaById(int id) {

        String sql = "SELECT id, cliente, valor_total, quantidade FROM venda WHERE id = ?";
        Venda venda = null;

        try {
            venda = jdbcTemplate.queryForObject(sql, new Object[]{id}, vendaRowMapper);
        } catch (DataAccessException ex) {
            log.error("Error getting product with id {}: {}", id, ex.getMessage());
        }

        return venda;
    }

    @Override
    public void create(Venda venda) {

    }
    
    public void create(Venda venda, List<Produto> produtos) {
        BigDecimal valorTotalVenda = BigDecimal.ZERO;
        int quantidadeProdutos = 0;

        // Calcular o valor total da venda e quantidade total de produtos e atualizar a quantidade disponível
        for (Produto produto : produtos) {
            BigDecimal subtotalProduto = produto.getValorUnitario().multiply(BigDecimal.valueOf(venda.getQuantidade()));
            valorTotalVenda = valorTotalVenda.add(subtotalProduto);
            quantidadeProdutos += venda.getQuantidade();

            produtoDao.subtractQuantityFromProduct(produto.getId(), venda.getQuantidade());

        }

        String sqlVenda = "INSERT INTO venda (cliente, valor_total, quantidade) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        BigDecimal valorTotalVendaFinal = valorTotalVenda;
        int quantidadeProdutosFinal = quantidadeProdutos;
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, venda.getCliente());
            ps.setBigDecimal(2, valorTotalVendaFinal);
            ps.setInt(3, quantidadeProdutosFinal);
            return ps;
        }, keyHolder);

        // Obtém o ID da venda inserida
        int idVenda = keyHolder.getKey().intValue();

        // Insere os produtos associados à venda na tabela VendaProduto
        String sqlVendaProduto = "INSERT INTO VendaProduto (id_venda, id_produto, quantidade) VALUES (?, ?, ?)";
        for (Produto produto : produtos) {
            jdbcTemplate.update(sqlVendaProduto, idVenda, produto.getId(), venda.getQuantidade());
        }

        log.info("Nova venda registrada: Valor total ${}", valorTotalVenda);
    }

    @Override
    public void update(Venda venda, int id) {

        String sql = "UPDATE Venda SET cliente = ?, valor_total = ?, quantidade = ? WHERE id = ?";
        int update = jdbcTemplate.update(sql, venda.getCliente(), venda.getValorTotal(), venda.getQuantidade(), id);
        if (update == 1) {
            log.info("Venda atualizada: ID {}", id);
        }
    }

    @Override
    public void delete(int id) {

        String deleteVendaProdutoSql = "DELETE FROM VendaProduto WHERE id_venda = ?";
        jdbcTemplate.update(deleteVendaProdutoSql, id);

        String deleteVendaSql = "DELETE FROM Venda WHERE id = ?";
        jdbcTemplate.update(deleteVendaSql, id);

        log.info("Venda excluída com sucesso: {}", id);

    }


}
