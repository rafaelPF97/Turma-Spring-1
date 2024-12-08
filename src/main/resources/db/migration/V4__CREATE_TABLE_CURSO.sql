CREATE TABLE TB_CURSO (
                         cd_curso INT AUTO_INCREMENT NOT NULL,
                         ds_descricao VARCHAR(255) NOT NULL,
                         ds_imgURL VARCHAR(255) NOT NULL,
                         nm_curso VARCHAR(255) NOT NULL,
                         cd_professor INT NOT NULL,
                         constraint PK_CURSO
                             PRIMARY KEY (cd_curso),
                         constraint FK_CURSO_PROFESSOR
                             FOREIGN KEY (cd_professor)
                                 REFERENCES TB_PROFESSOR(cd_professor)
);
