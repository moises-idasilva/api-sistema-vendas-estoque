CREATE TABLE Venda (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    cliente VARCHAR(255) NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    quantidade INTEGER NOT NULL
);

CREATE TABLE Produto (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    quantidade_disponivel INTEGER NOT NULL,
    valor_unitario DECIMAL(10, 2) NOT NULL
);

CREATE TABLE VendaProduto (
    id_venda INTEGER,
    id_produto INTEGER,
    quantidade INTEGER,
    FOREIGN KEY (id_venda) REFERENCES Venda (id),
    FOREIGN KEY (id_produto) REFERENCES Produto (id),
    PRIMARY KEY (id_venda, id_produto)
);

ALTER TABLE VendaProduto
    ADD CONSTRAINT fk_id_venda FOREIGN KEY (id_venda) REFERENCES Venda (id) ON DELETE CASCADE;




