CREATE TABLE IF NOT EXISTS produtos (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    descricao TEXT NOT NULL,
    preco NUMERIC(12, 2) NOT NULL CHECK (preco >= 0)
);

CREATE TABLE IF NOT EXISTS carrinhos (
    id UUID PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS carrinho_itens (
    carrinho_id UUID NOT NULL REFERENCES carrinhos(id) ON DELETE CASCADE,
    produto_id BIGINT NOT NULL REFERENCES produtos(id),
    quantidade INTEGER NOT NULL CHECK (quantidade > 0),
    PRIMARY KEY (carrinho_id, produto_id)
);

INSERT INTO produtos (id, nome, descricao, preco) VALUES
    (1, 'Notebook', 'Notebook para trabalho e estudos.', 3500.00),
    (2, 'Mouse', 'Mouse óptico USB.', 80.00),
    (3, 'Teclado Mecânico', 'Teclado mecânico para produtividade.', 250.00),
    (4, 'Monitor', 'Monitor para estação de trabalho.', 1200.00),
    (5, 'Headset', 'Headset para aulas e reuniões.', 180.00)
ON CONFLICT (id) DO NOTHING;
