package br.com.escola.admin.controllers.diretor.dto;

import br.com.escola.admin.models.Diretor;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record DiretorCreateDTO(
        @NotBlank
        String nome,
        @NotBlank
        @CPF
        String cpf
) {
    public Diretor toEntity() {
        return new Diretor(this.nome, this.cpf);
    }
}
