package br.com.escola.admin.services;

import br.com.escola.admin.controllers.curso.dto.CursoDTO;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.models.Curso;
import br.com.escola.admin.models.CursoAluno;
import br.com.escola.admin.models.CursoAlunoID;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.AlunoRepository;
import br.com.escola.admin.repositories.CursoAlunoRepository;
import br.com.escola.admin.repositories.CursoRepository;
import br.com.escola.admin.repositories.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {
    private final CursoRepository repository;
    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final CursoAlunoRepository cursoAlunoRepository;

    public CursoService(CursoRepository repository,
                        ProfessorRepository professorRepository,
                        AlunoRepository alunoRepository,
                        CursoAlunoRepository cursoAlunoRepository) {
        this.repository = repository;
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
        this.cursoAlunoRepository = cursoAlunoRepository;
    }


    public List<Curso> obterCursos() {
        return repository.findAll();
    }

    public Curso obterCursoPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado"));
    }

    public Curso criarCurso(CursoDTO dto) {
        Professor professor = professorRepository.findById(dto.profId())
                .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado"));
        Curso curso = new Curso();
        curso.setNome(dto.nome());
        curso.setDescricao(dto.descricao());
        curso.setImgUrl(dto.imgUrl());
        curso.setProfessor(professor);
        repository.save(curso);
        return curso;
    }

    public Curso atualizarCursoPorId(Long id, CursoDTO dto) {
        Curso cursoSalvo = obterCursoPorId(id);
        Professor professor = professorRepository.findById(dto.profId())
                .orElseThrow(() -> new ResourceNotFoundException("Professor não encontrado"));

        cursoSalvo.setNome(dto.nome());
        cursoSalvo.setDescricao(dto.descricao());
        cursoSalvo.setImgUrl(dto.imgUrl());
        cursoSalvo.setProfessor(professor);
        repository.save(cursoSalvo);
        return cursoSalvo;
    }

    public void removerCursoPorId(Long id) {
        Curso curso = obterCursoPorId(id);
        repository.delete(curso);
    }

    public void adicionaAlunoAoCurso(Long idCurso, Long idAluno) {
        Curso curso = obterCursoPorId(idCurso);
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));

        CursoAlunoID cursoAlunoID = new CursoAlunoID(aluno, curso);
        CursoAluno cursoAluno = new CursoAluno(cursoAlunoID, 0.0);

        cursoAlunoRepository.save(cursoAluno);
    }

    public void adicionaNotaAoAlunoCurso(Long idCurso, Long idAluno, double nota) {
        Curso curso = obterCursoPorId(idCurso);
        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new ResourceNotFoundException("Aluno não encontrado"));

        var cursoAlunoId = new CursoAlunoID(aluno, curso);
        CursoAluno cursoAluno = cursoAlunoRepository.findById(cursoAlunoId)
                .orElseThrow(() -> new ResourceNotFoundException("Vinculo de aluno e curso não encontrado"));
        cursoAluno.setNota(nota);
        cursoAlunoRepository.save(cursoAluno);
    }

    public CursoAluno obterCursoAluno(Long idCurso, Long idAluno) {
        Curso curso = obterCursoPorId(idCurso);
        Aluno aluno = alunoRepository.findById(idAluno).orElseThrow(
                () -> new ResourceNotFoundException("Aluno não encontrado")
        );
        var cursoAlunoId = new CursoAlunoID(aluno, curso);
        return cursoAlunoRepository.findById(cursoAlunoId).orElseThrow(
                () -> new ResourceNotFoundException("Vinculo de aluno e curso não encontrado")
        );
    }

}
