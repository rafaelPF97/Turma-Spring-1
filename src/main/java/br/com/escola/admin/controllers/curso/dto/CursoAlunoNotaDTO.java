package br.com.escola.admin.controllers.curso.dto;

import jakarta.validation.constraints.NotBlank;

public record CursoAlunoNotaDTO(
        double nota
) {
}
