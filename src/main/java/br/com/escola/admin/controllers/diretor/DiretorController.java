package br.com.escola.admin.controllers.diretor;

import br.com.escola.admin.controllers.diretor.dto.DiretorCreateDTO;
import br.com.escola.admin.controllers.diretor.dto.DiretorDTO;
import br.com.escola.admin.controllers.diretor.dto.DiretorUpdateDTO;
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
    public List<Diretor> obterDiretores() {
        return service.obterDiretores();
    }

    @GetMapping("/{id}")
    public Diretor obterDiretorPorId(@PathVariable Long id) {
        return service.obterDiretorPorId(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public DiretorDTO criarDiretor(@RequestBody @Valid DiretorCreateDTO dto) {
        Diretor diretorSalvo = service.criarDiretor(dto.toEntity());
        return DiretorDTO.from(diretorSalvo);
    }

    @PutMapping("/{id}")
    public DiretorDTO atulizarDiretor(@PathVariable Long id, @RequestBody @Valid DiretorUpdateDTO dto) {
        var diretorAtualizado = service.atulizarDiretorPorId(id,dto.toEntity());
        return DiretorDTO.from(diretorAtualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletarDiretor(@PathVariable Long id) {
        service.removerDiretorPorId(id);
    }

}
