package br.com.escola.admin.controllers.aluno.dto;

import br.com.escola.admin.models.Aluno;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record AlunoUpdateDTO(
        @NotBlank
        String nome,
        @NotBlank
        @CPF
        String cpf
) {
    public Aluno toEntity() {
        return new Aluno(this.nome, this.cpf);
    }
}