package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Professor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EmMemoriaProfessorRepository implements ProfessorRepository {
    private static List<Professor> professores = new ArrayList<>(
            Arrays.asList(
                    new Professor("Carlos", "12312345685", "Geografia"),
                    new Professor("Pedro", "78998712365", "Matemática")
            )
    );

    @Override
    public List<Professor> obterProfessores() {
        return professores;
    }

    @Override
    public Optional<Professor> obterProfessorPorId(Long id) {
        return professores
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Professor> obterProfessorPorCpf(String cpf) {
        return professores.stream().filter(
                p -> p.getCpf().equals(cpf)).findFirst();
    }

    @Override
    public void salvarProfessor(Professor professor) {
        var professorOptional = obterProfessorPorCpf(professor.getCpf());

        if (professorOptional.isPresent()) {
            professorOptional.get().setName(professor.getName());
            professorOptional.get().setCpf(professor.getCpf());
            professorOptional.get().setEspecialidade(professor.getEspecialidade());
        } else {
            professores.add(professor);
        }
    }

    @Override
    public boolean existeProfessorComEsseCpf(String cpf) {
        return obterProfessorPorCpf(cpf).isPresent();
    }

    @Override
    public void removerProfessor(Professor professor) {
        professores.remove(professor);
    }


}
