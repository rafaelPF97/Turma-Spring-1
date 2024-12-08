CREATE TABLE IF NOT EXISTS tb_professor (
                             cd_professor INT AUTO_INCREMENT NOT NULL,
                             nm_professor VARCHAR(255) NOT NULL,
                             nr_cpf VARCHAR(11) NOT NULL,
                             ds_especialidade VARCHAR(255) NOT NULL,
                             CONSTRAINT PK_PROFESSOR
                                 PRIMARY KEY (cd_professor)
);