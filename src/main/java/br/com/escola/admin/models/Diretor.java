package br.com.escola.admin.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_diretor")
public class Diretor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_id")
    private Long id;
    @Column(name = "nm_nome",nullable = false)
    private String nome;
    @Column(name = "nr_cpf", nullable = false, length = 11)
    private String cpf;

    public Diretor(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public Diretor() {

    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }
}
