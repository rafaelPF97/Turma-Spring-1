package br.com.escola.admin.controllers.diretor.dto;

import br.com.escola.admin.models.Diretor;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record DiretorDTO(
        String nome,
        String cpf
) {
    public static DiretorDTO from(Diretor diretor) {
        return new DiretorDTO(diretor.getNome(), diretor.getCpf());
    }

    public static Diretor to(DiretorDTO diretorDTO) {
        return new Diretor(diretorDTO.nome(), diretorDTO.cpf());
    }
}
