package br.com.escola.admin.controllers.curso.dto;

import br.com.escola.admin.models.Curso;
import jakarta.validation.constraints.NotBlank;

public record CursoDTO(
        @NotBlank
        String nome,
        @NotBlank
        String descricao,
        @NotBlank
        String imgUrl,
        @NotBlank
        Long profId
) {

}
