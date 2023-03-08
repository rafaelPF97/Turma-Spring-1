package br.com.escola.admin.models;

import java.util.Random;

public class Professor {
    private Long id;
    private String name;
    private String cpf;
    private String especialidade;

    public Professor(String name, String cpf, String especialidade) {
        var random = new Random();
        this.id = random.nextLong(1,100_000);
        this.name = name;
        this.cpf = cpf;
        this.especialidade = especialidade;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
