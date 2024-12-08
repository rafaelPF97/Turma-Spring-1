package br.com.escola.admin.controllers.curso;

import br.com.escola.admin.controllers.curso.dto.CursoAlunoNotaDTO;
import br.com.escola.admin.controllers.curso.dto.CursoDTO;
import br.com.escola.admin.models.Curso;
import br.com.escola.admin.models.CursoAluno;
import br.com.escola.admin.services.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cursos")
public class CursoController {
    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Curso> obterCursos() {
        return service.obterCursos();
    }

    @GetMapping("/{id}")
    public Curso obterCursoPorId(@PathVariable Long id) {
        return service.obterCursoPorId(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Curso criarCurso(@RequestBody @Valid CursoDTO curso) {
        return service.criarCurso(curso);
    }

    @PutMapping("/{id}")
    public Curso atualizarCurso(@PathVariable Long id, @RequestBody @Valid CursoDTO curso) {
        return service.atualizarCursoPorId(id, curso);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletarCursoPorId(@PathVariable Long id) {
        service.removerCursoPorId(id);
    }

    @PostMapping("/{idCurso}/aluno/{idAluno}")
    public void adicionaAlunoAoCurso(@PathVariable Long idCurso, @PathVariable Long idAluno) {
        service.adicionaAlunoAoCurso(idCurso, idAluno);
    }

    @PatchMapping("/{idCurso}/aluno/{idAluno}/nota")
    public void adicionarNotaAoAlunoCurso(@PathVariable Long idCurso, @PathVariable Long idAluno, @RequestBody @Valid CursoAlunoNotaDTO dto) {
        service.adicionaNotaAoAlunoCurso(idCurso, idAluno, dto.nota());
    }

    @GetMapping("/{idCurso}/aluno/{idAluno}")
    public CursoAluno obterCursoAluno(@PathVariable Long idCurso, @PathVariable Long idAluno) {
        return service.obterCursoAluno(idCurso, idAluno);
    }
}
