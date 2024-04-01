-- Inserindo dados na tabela Produto (Produtos de uma loja de celulares)
INSERT INTO Produto (id, nome, descricao, quantidade_disponivel, valor_unitario)
VALUES
    (1, 'iPhone 13', 'Smartphone da Apple com câmera dupla e chip A15 Bionic', 50, 999.99),
    (2, 'Samsung Galaxy S21', 'Smartphone Android com tela Dynamic AMOLED 2X e câmera de 108 MP', 40, 899.99),
    (3, 'Google Pixel 6', 'Smartphone com câmera de 50 MP e processador Google Tensor', 30, 799.99),
    (4, 'OnePlus 9 Pro', 'Smartphone com tela Fluid AMOLED e carregamento rápido de 65W', 20, 899.99),
    (5, 'Xiaomi Mi 11', 'Smartphone com processador Snapdragon 888 e tela AMOLED de 6.81 polegadas', 35, 699.99);

-- Inserindo dados na tabela Venda
INSERT INTO Venda (id, cliente, valor_total, quantidade)
VALUES
    (1, 'João', 89.97, 3),
    (2, 'Maria', 169.98, 2),
    (3, 'Pedro', 129.97, 2),
    (4, 'Ana', 99.98, 2),
    (5, 'Carlos', 149.97, 3);

-- Inserindo dados na tabela VendaProduto (relacionando vendas com produtos)
INSERT INTO VendaProduto (id_venda, id_produto, quantidade)
VALUES
    (1, 1, 2),
    (1, 3, 1),
    (2, 2, 2),
    (3, 1, 1),
    (3, 4, 1),
    (4, 5, 2),
    (5, 1, 1),
    (5, 2, 1),
    (5, 3, 1);