package br.com.escola.admin.controllers;

import br.com.escola.admin.models.Professor;
import br.com.escola.admin.services.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("professores")
public class ProfessorController {
    private final ProfessorService service;

    public ProfessorController(ProfessorService service) {

        this.service = service;
    }

    @GetMapping
    public List<Professor> obterProfessores(){

        return service.obterProfessores();
    }

    @GetMapping("/{id}")
    public Professor obterProfessorPorId(@PathVariable Long id){

        return service.obterProfessorPorId(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Professor criarProfessor(@RequestBody @Valid Professor professor){
        Professor professorSalvo = service.criarProfessor(professor);
        return professorSalvo;
    }

    @PutMapping("/{id}")
    public Professor atualizarProfessor(@PathVariable Long id, @RequestBody @Valid Professor professor){
        var professorAtulizado = service.atualizarProfessorPorId(id, professor);
        return professorAtulizado;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletarProfessor(@PathVariable Long id){

        service.removerProfessorPorId(id);
    }
}
