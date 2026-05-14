-- 1. Criação das tabelas que não dependem de outras
CREATE TABLE tb_editora (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            cnpj VARCHAR(255) NOT NULL,
                            email VARCHAR(255) NOT NULL UNIQUE,
                            endereco VARCHAR(255) NOT NULL
);

CREATE TABLE tb_autor (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          nacionalidade VARCHAR(255) NOT NULL,
                          biografia VARCHAR(255) NOT NULL
);

CREATE TABLE tb_aluno (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,
                          serie VARCHAR(255)
);

CREATE TABLE tb_professor (
                              id BIGSERIAL PRIMARY KEY,
                              name VARCHAR(255) NOT NULL,
                              email VARCHAR(255) NOT NULL UNIQUE,
                              password VARCHAR(255) NOT NULL,
                              disciplina VARCHAR(255),
                              serie VARCHAR(255)
);

-- 2. Criação da tabela Book (repare que o nome está tb_book e ela tem o editora_id)
CREATE TABLE tb_book (
                         isbn BIGSERIAL PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         genero VARCHAR(255) NOT NULL,
                         descricao VARCHAR(255) NOT NULL,
                         ano_lancamento INTEGER NOT NULL,
                         pdf_key VARCHAR(255),
                         editora_id BIGINT,

                         CONSTRAINT fk_book_editora FOREIGN KEY (editora_id) REFERENCES tb_editora(id)
);

-- 3. Criação da tabela de junção exigida pelo @ManyToMany do Autor
CREATE TABLE tb_autor_livro (
                                autor_id BIGINT NOT NULL,
                                livro_isbn BIGINT NOT NULL,

                                CONSTRAINT fk_autor FOREIGN KEY (autor_id) REFERENCES tb_autor(id),
                                CONSTRAINT fk_livro FOREIGN KEY (livro_isbn) REFERENCES tb_book(isbn)
);