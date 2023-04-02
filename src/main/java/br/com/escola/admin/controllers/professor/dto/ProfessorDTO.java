package br.com.escola.admin.controllers.professor.dto;

import br.com.escola.admin.models.Professor;

public record ProfessorDTO(
        String nome,
        String cpf,
        String especialidade
) {
    public static ProfessorDTO from(Professor professor) {
        return new ProfessorDTO(professor.getNome(), professor.getCpf(), professor.getEspecialidade());
    }

    public static Professor to(ProfessorDTO professorDTO) {
        return new Professor(professorDTO.nome(), professorDTO.cpf(), professorDTO.especialidade());
    }
}
