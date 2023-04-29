package br.com.escola.admin.controllers.aluno;

import br.com.escola.admin.controllers.aluno.dto.AlunoCreateDTO;
import br.com.escola.admin.controllers.aluno.dto.AlunoDTO;
import br.com.escola.admin.controllers.aluno.dto.AlunoUpdateDTO;
import br.com.escola.admin.models.Aluno;
import br.com.escola.admin.services.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Aluno> consultarAlunos() {
        System.out.println("Controller");
        return service.consultarAlunos();
    }

    @GetMapping("/{id}")
    public Aluno consultarAlunoPorId(@PathVariable Long id) {
        return service.consultarAlunoPorId(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AlunoDTO criarAluno(@RequestBody @Valid AlunoCreateDTO dto) {
        Aluno alunoSalvo = service.criarAluno(dto.toEntity());
        return AlunoDTO.from(alunoSalvo);
    }

    @PutMapping("/{id}")
    public AlunoDTO atualizarAluno(@PathVariable Long id, @RequestBody @Valid AlunoUpdateDTO dto) {
        //Buscando no meu banco de dados o aluno com o cpf informado
        var alunoAtualizado = service.atualizarAluno(id, dto.toEntity());
        return AlunoDTO.from(alunoAtualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletarAluno(@PathVariable Long id) {
        service.removerAlunoPorId(id);
    }

}