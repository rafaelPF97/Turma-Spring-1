CREATE TABLE tb_aluno (
                         cd_aluno INT AUTO_INCREMENT NOT NULL,
                         nm_aluno VARCHAR(255) NOT NULL,
                         nr_cpf VARCHAR(11) NOT NULL,
                         CONSTRAINT PK_ALUNO
                             PRIMARY KEY (cd_aluno)
);