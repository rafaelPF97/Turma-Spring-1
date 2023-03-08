package br.com.escola.admin.services;

import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {


    private final ProfessorRepository repository;

    public ProfessorService(ProfessorRepository repository) {
        this.repository = repository;
    }

    public List<Professor> obterProfessores() {
        return repository.obterProfessores();
    }

    public Professor obterProfessorPorId(Long id) {
        Optional<Professor> professorOptional = repository.obterProfessorPorId(id);
        if(professorOptional.isEmpty()){
            throw new RuntimeException("Esse professor não existe");
        }
        return professorOptional.get();
    }
    public Professor criarProfessor(Professor professor){
        boolean existeProfessorComEsseCpf = repository.existeProfessorComEsseCpf(professor.getCpf());
        if(existeProfessorComEsseCpf){
            throw new RuntimeException("Já existe um professor com esse cpf");
        }
        repository.salvarProfessor(professor);
        return professor;
    }

    public Professor atualizarProfessor(Long id, Professor professor){
        var professorSalvo = obterProfessorPorId(id);
        boolean existeProfessorComEsseCpf = repository.existeProfessorComEsseCpf(professor.getCpf());
        if(existeProfessorComEsseCpf){
            throw new RuntimeException("Já existe um professor com esse cpf");
        }

        professorSalvo.setName(professor.getName());
        professorSalvo.setCpf(professor.getCpf());
        professorSalvo.setEspecialidade(professor.getEspecialidade());

        repository.salvarProfessor(professorSalvo);
        return professorSalvo;
    }
    public void removerProfessorPorId(Long id){
        Professor professor = obterProfessorPorId(id);

        repository.removerProfessor(professor);
    }

}
