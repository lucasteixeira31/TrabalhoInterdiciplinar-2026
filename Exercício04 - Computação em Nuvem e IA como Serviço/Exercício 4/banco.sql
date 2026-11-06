-- Executar no Azure Database for PostgreSQL após estabelecer conexão.
CREATE TABLE IF NOT EXISTS livros (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    titulo VARCHAR(120) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    ano_publicacao INTEGER NOT NULL
);
INSERT INTO livros (titulo, autor, ano_publicacao) VALUES
('Dom Casmurro', 'Machado de Assis', 1899),
('O Cortico', 'Aluisio Azevedo', 1890),
('Vidas Secas', 'Graciliano Ramos', 1938);
SELECT id, titulo, autor, ano_publicacao FROM livros ORDER BY id;
