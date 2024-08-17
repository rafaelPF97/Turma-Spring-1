CREATE TABLE TB_DIRETOR (
                            cd_diretor INT AUTO_INCREMENT NOT NULL,
                            nm_diretor VARCHAR(255) NOT NULL,
                            nr_cpf VARCHAR(11) NOT NULL,
                            CONSTRAINT PK_DIRETOR
                                PRIMARY KEY (cd_diretor)
)