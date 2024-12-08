CREATE TABLE tb_aluno_curso(
                                 CD_CURSO INT,
                                 CD_ALUNO INT,
                                 NR_NOTA INT,
                                 CONSTRAINT FK_CURSO_ALUNO_NOTA_ALUNO
                                     FOREIGN KEY (cd_aluno)
                                         REFERENCES tb_aluno(cd_aluno),
                                 CONSTRAINT FK_CURSO_ALUNO_NOTA_CURSO
                                     FOREIGN KEY (CD_CURSO)
                                         REFERENCES tb_curso(CD_CURSO),
                                 CONSTRAINT PK_CURSO_ALUNO_NOTA
                                     PRIMARY KEY (CD_CURSO,CD_ALUNO)
);