package br.com.escola.admin.controllers;

import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.services.DiretorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("diretores")
public class DiretorController {
    private final DiretorService service;

    public DiretorController(DiretorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Diretor> obterDiretores(){
        return service.obterDiretores();
    }

    @GetMapping("/{id}")
    public Diretor obterDiretorPorId(@PathVariable Long id){
        return service.obterDiretorPorId(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Diretor criarDiretor(@RequestBody @Valid Diretor diretor){
        return service.criarDiretor(diretor);
    }

    @PutMapping("/{id}")
    public Diretor atulizarDiretor(@PathVariable Long id,@RequestBody @Valid Diretor diretor){
       return service.atulizarDiretorPorId(id,diretor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletarDiretor(@PathVariable Long id){
        service.removerDiretorPorId(id);
    }

}
