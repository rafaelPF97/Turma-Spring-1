package br.com.escola.admin.controllers.professor;

import br.com.escola.admin.controllers.professor.dto.ProfessorCreateDTO;
import br.com.escola.admin.controllers.professor.dto.ProfessorDTO;
import br.com.escola.admin.controllers.professor.dto.ProfessorUpdateDTO;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.services.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("professores")
public class ProfessorController {
    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {

        this.service = service;
    }

    @GetMapping
    public List<Professor> obterProfessores() {
        return service.obterProfessores();
    }

    @GetMapping("/{id}")
    public Professor obterProfessorPorId(@PathVariable Long id) {
        return service.obterProfessorPorId(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProfessorDTO criarProfessor(@RequestBody @Valid ProfessorCreateDTO dto) {
        Professor professorSalvo = service.criarProfessor(dto.toEntity());
        return ProfessorDTO.from(professorSalvo);
    }

    @PutMapping("/{id}")
    public ProfessorDTO atualizarProfessor(@PathVariable Long id, @RequestBody @Valid ProfessorUpdateDTO dto) {
        var professorAtulizado = service.atualizarProfessorPorId(id, dto.toEntity());
        return ProfessorDTO.from(professorAtulizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletarProfessor(@PathVariable Long id) {
        service.removerProfessorPorId(id);
    }
}
