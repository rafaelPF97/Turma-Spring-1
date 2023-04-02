package br.com.escola.admin.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_curso_aluno")
public class CursoAluno {
    @EmbeddedId
    private CursoAlunoID id;
    @Column(name = "nr_nota")
    private Double nota;

    public CursoAluno(CursoAlunoID id, double nota) {
        this.id = id;
        this.nota = nota;
    }

    public CursoAluno() {
    }

    public CursoAlunoID getId() {
        return id;
    }

    public void setId(CursoAlunoID id) {
        this.id = id;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}
