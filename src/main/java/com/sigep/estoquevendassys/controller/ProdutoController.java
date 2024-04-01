package com.sigep.estoquevendassys.controller;

import com.sigep.estoquevendassys.model.Produto;
import com.sigep.estoquevendassys.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoRepository) {
        this.produtoService = produtoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listAll() {

        List<Produto> produtos = produtoService.list();

        return ResponseEntity.ok().body(produtos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProduto(@PathVariable int id) {

        Optional<Produto> produtoOptional = produtoService.get(id);
        return produtoOptional.map(produto -> ResponseEntity.ok().body(produto))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/venda/{vendaId}")
    public ResponseEntity<List<Produto>> getProdutoByVendaId(@PathVariable int vendaId) {

        List<Produto> produtos = produtoService.listProductByVendaId(vendaId);
        if (produtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produtos);

    }

    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {

        produtoService.create(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@RequestBody Produto produto, @PathVariable int id) {

        Optional<Produto> existingProdutoOptional = produtoService.get(id);
        if (existingProdutoOptional.isPresent()) {
            produtoService.update(produto, id);
            return ResponseEntity.ok().body(produto);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable int id) {

        Optional<Produto> produtoOptional = produtoService.get(id);
        if (produtoOptional.isPresent()) {
            produtoService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
