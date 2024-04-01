package com.sigep.estoquevendassys.controller;

import com.sigep.estoquevendassys.dto.VendaProdutoDTO;
import com.sigep.estoquevendassys.model.Produto;
import com.sigep.estoquevendassys.model.Venda;
import com.sigep.estoquevendassys.service.VendaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/venda")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listAll() {

        List<Venda> vendaList = vendaService.list();

        return ResponseEntity.ok().body(vendaList);


    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> getVenda(@PathVariable int id) {

        Optional<Venda> vendaOptional = vendaService.get(id);
        return vendaOptional.map(venda -> ResponseEntity.ok().body(venda))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<Venda> createVenda(@RequestBody VendaProdutoDTO vendaProdutoDTO) {
        List<Produto> produtos = vendaProdutoDTO.getProdutos();
        Venda venda = vendaProdutoDTO.getVenda();

        vendaService.create(venda, produtos);

        return ResponseEntity.status(HttpStatus.CREATED).body(venda);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venda> updateVenda(@RequestBody Venda venda, @PathVariable int id) {

        Optional<Venda> existingVendaOptional = vendaService.get(id);
        if (existingVendaOptional.isPresent()) {
            vendaService.update(venda, id);
            return ResponseEntity.ok().body(venda);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenda(@PathVariable int id) {

        Optional<Venda> produtoOptional = vendaService.get(id);
        if (produtoOptional.isPresent()) {
            vendaService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
