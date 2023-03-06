package br.com.escola.admin.service;

import br.com.escola.admin.model.Aluno;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AlunoService {
    private List<Aluno> alunos = new ArrayList<>(
            Arrays.asList(
                    new Aluno("João", "12345678900"),
                    new Aluno("Maria", "12345678901"),
                    new Aluno("José", "12345678902")
            )
    );
    public Aluno consultarAlunoPorCpf(String cpf) {
        Optional<Aluno> alunoSelecionado = alunos.stream().filter(
                a -> a.getCpf().equals(cpf)
        ).findFirst();

        if(alunoSelecionado.isEmpty()) {
            throw new RuntimeException("Aluno não encontrado");
        }

        return alunoSelecionado.get();
    }

    public List<Aluno> consultarAlunos() {
        return alunos;
    }

    public Aluno criarAluno(Aluno aluno) {
        alunos.add(aluno);
        return aluno;
    }

    public Aluno atualizarAluno(String cpf, Aluno aluno) {
        Aluno alunoAtualizado = consultarAlunoPorCpf(cpf);

        alunoAtualizado.setNome(aluno.getNome());
        alunoAtualizado.setCpf(aluno.getCpf());
        return alunoAtualizado;
    }

    public void removerAlunoPorCpf(String cpf) {
        Aluno aluno = consultarAlunoPorCpf(cpf);
        alunos.remove(aluno);
    }
}
