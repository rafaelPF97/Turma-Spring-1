CREATE TABLE TB_CURSO_ALUNO(
                                 CD_CURSO INT,
                                 CD_ALUNO INT,
                                 NR_NOTA INT,
                                 CONSTRAINT FK_CURSO_ALUNO_NOTA_ALUNO
                                     FOREIGN KEY (cd_aluno)
                                         REFERENCES TB_ALUNO(cd_aluno),
                                 CONSTRAINT FK_CURSO_ALUNO_NOTA_CURSO
                                     FOREIGN KEY (CD_CURSO)
                                         REFERENCES TB_CURSO(CD_CURSO),
                                 CONSTRAINT PK_CURSO_ALUNO_NOTA
                                     PRIMARY KEY (CD_CURSO,CD_ALUNO)
);