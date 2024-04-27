-- Criando a tabela
CREATE TABLE users_example (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);

-- Inserindo registros
INSERT INTO users_example (name, email) VALUES
('João', 'joao@example.com'),
('Maria', 'maria@example.com'),
('Pedro', 'pedro@example.com');
