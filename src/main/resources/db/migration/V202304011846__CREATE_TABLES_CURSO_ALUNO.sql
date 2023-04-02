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

CREATE TABLE TB_CURSO_ALUNO(
                                 CD_CURSO INT,
                                 CD_ALUNO INT,
                                 NR_NOTA INT,
                                 CONSTRAINT FK_CURSO_ALUNO_NOTA_ALUNO
                                     FOREIGN KEY (CD_ALUNO)
                                         REFERENCES TB_ALUNO(CD_ALUNO),
                                 CONSTRAINT FK_CURSO_ALUNO_NOTA_CURSO
                                     FOREIGN KEY (CD_CURSO)
                                         REFERENCES TB_CURSO(CD_CURSO),
                                 CONSTRAINT PK_CURSO_ALUNO_NOTA
                                     PRIMARY KEY (CD_CURSO,CD_ALUNO)
);
