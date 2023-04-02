package br.com.escola.admin.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class CursoAlunoID implements Serializable {
    @ManyToOne
    @JoinColumn(name = "cd_aluno")
    private Aluno aluno;
    @ManyToOne
    @JoinColumn(name = "cd_curso")
    private Curso curso;

    public CursoAlunoID(Aluno aluno, Curso curso) {
        this.aluno = aluno;
        this.curso = curso;
    }

    public CursoAlunoID() {
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
