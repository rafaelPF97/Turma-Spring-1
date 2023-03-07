package br.com.escola.admin.services;

import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.repositories.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AlunoService {

    //Implementação
    //Abstração
    private final AlunoRepository repository;

    public AlunoService(
            AlunoRepository repository) {
        this.repository = repository;
    }

    public Aluno consultarAlunoPorCpf(String cpf) {

        Optional<Aluno> alunoSelecionado = repository.obterAlunoPorCpf(cpf);

        if (alunoSelecionado.isEmpty()) {
            throw new RuntimeException("Aluno não existe");
        }

        return alunoSelecionado.get();
    }

    public List<Aluno> consultarAlunos() {
        System.out.println("Service");
        return repository.obterAlunos();
    }

    public Aluno criarAluno(Aluno aluno) {
        // nao pode add um aluno com mesmo cpf
        //saber se existe um aluno com o cpf informado
        boolean existeAlunoComCpf = repository.existeAlunoComCpf(aluno.getCpf());

        if (existeAlunoComCpf) {
            throw new RuntimeException("Já existe um aluno com esse cpf");
        }

        repository.salvarAluno(aluno);
        return aluno;
    }

    public Aluno atualizarAluno(String cpf, Aluno aluno) {

        //TODO: Arrumar bug com teste unitario
        var alunoSalvo = consultarAlunoPorCpf(cpf);

        alunoSalvo.setNome(aluno.getNome());
        alunoSalvo.setCpf(aluno.getCpf());


        repository.salvarAluno(alunoSalvo);

        return alunoSalvo;
    }

    public void removerAlunoPorCpf(String cpf) {

        // Agt já validou se o aluno existe na base.
        Aluno aluno = consultarAlunoPorCpf(cpf);

        repository.removerAluno(aluno);

    }
}
