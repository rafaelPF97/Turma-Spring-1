package br.com.escola.admin.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_professor")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_professor")
    private Long id;
    @Column(name = "nm_professor")
    private String nome;
    @Column(name = "nr_cpf", length = 11)
    private String cpf;
    @Column(name = "ds_especialidade", nullable = false)
    private String especialidade;

    public Professor(String nome, String cpf, String especialidade) {
        this.nome = nome;
        this.cpf = cpf;
        this.especialidade = especialidade;
    }

    public Professor() {
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void atualizaDados(Professor novoProfessor) {
        this.nome = novoProfessor.getNome();
        this.cpf = novoProfessor.getCpf();
        this.especialidade = novoProfessor.getEspecialidade();
    }

}
