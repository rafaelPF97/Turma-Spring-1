package br.com.escola.admin.controllers.professor.dto;

import br.com.escola.admin.models.Professor;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record ProfessorUpdateDTO(
        @NotBlank
        String nome,
        @NotBlank
        @CPF
        String cpf,
        @NotBlank
        String especialidade
) {
    public Professor toEntity() {
        return new Professor(this.nome, this.cpf, this.especialidade);
    }
}