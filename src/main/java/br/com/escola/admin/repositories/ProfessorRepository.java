package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Professor;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository {
    List<Professor> obterProfessores();

    Optional<Professor> obterProfessorPorId(Long id);

    Optional<Professor> obterProfessorPorCpf(String cpf);

    void salvarProfessor(Professor professor);

    boolean existeProfessorComEsseCpf(String cpf);

    void removerProfessor(Professor professor);
}
