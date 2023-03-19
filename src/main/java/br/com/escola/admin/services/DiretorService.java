package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.repositories.DiretorRepository;
import org.springframework.stereotype.Service;

import static br.com.escola.admin.utils.validators.CPFValidator.isNotValid;

import java.util.List;
import java.util.Optional;

@Service
public class DiretorService {
    private final DiretorRepository repository;

    public DiretorService(DiretorRepository repository) {
        this.repository = repository;
    }

    public List<Diretor> obterDiretores() {
        return repository.findAll();
    }

    public Diretor obterDiretorPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diretor não encontrado"));
    }

    public Diretor criarDiretor(Diretor diretor) {
        Optional<Diretor> existeDiretorComEsseCpf = repository.findByCpf(diretor.getCpf());
        if (diretor.getCpf() == null || diretor.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo");

        }if (diretor.getNome() == null || diretor.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo");
        }
        if(isNotValid(diretor.getCpf())){
            throw new IllegalArgumentException("CPF inválido");
        }
        if (existeDiretorComEsseCpf.isPresent()) {
            throw new BusinessRuleException("Já existe um diretor com esse cpf");
        }
        repository.save(diretor);
        return diretor;
    }

    public Diretor atulizarDiretorPorId(Long id, Diretor diretor) {
        var diretorSalvo = obterDiretorPorId(id);
        Optional<Diretor> existeDiretorComEsseCpf = repository.findByCpf(diretor.getCpf());
        if (diretor.getCpf() == null || diretor.getCpf().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser nulo");

        }if (diretor.getNome() == null || diretor.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo");
        }
        if(isNotValid(diretor.getCpf())){
            throw new IllegalArgumentException("CPF inválido");
        }
        if (existeDiretorComEsseCpf.isPresent()) {
            throw new BusinessRuleException("Já existe um diretor com esse cpf");
        }
        diretorSalvo.setNome(diretor.getNome());
        diretorSalvo.setCpf(diretor.getCpf());
        repository.save(diretorSalvo);
        return diretorSalvo;
    }

    public void removerDiretorPorId(Long id) {
        Diretor diretor = obterDiretorPorId(id);
        repository.delete(diretor);
    }
}
