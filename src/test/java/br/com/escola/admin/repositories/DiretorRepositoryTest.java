package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Diretor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class DiretorRepositoryTest {
    @Autowired
    private DiretorRepository diretorRepository;

    @Test
    @DisplayName("Deve salvar diretor com sucesso")
    void deveSalarDiretorComSucesso(){
        Diretor diretor = new Diretor("Pedro","12345678911");

        Diretor diretorSalvo = diretorRepository.save(diretor);

        Assertions.assertThat(diretorSalvo).isNotNull();
        Assertions.assertThat(diretorSalvo.getNome()).isEqualTo("Pedro");
        Assertions.assertThat(diretorSalvo.getCpf()).isEqualTo("12345678911");

    }

    @Test
    @DisplayName("Deve deletar diretor com sucesso")
    void deveDeletarDiretorComSucesso(){
        Diretor diretor = new Diretor("Pedro","12345678911");
        diretor.setId(1L);

        Diretor diretorSalvo = diretorRepository.save(diretor);

        diretorRepository.delete(diretorSalvo);

        Optional<Diretor> diretorOptional = diretorRepository.findById(diretorSalvo.getId());

        Assertions.assertThat(diretorOptional).isEmpty();
    }

    @Test
    @DisplayName("Deve retonar um diretor por id com sucesso")
    void deveRetornarDiretorPorId(){
        Diretor diretor = new Diretor("Pedro","12345678911");
        diretor.setId(1L);

        Diretor diretorSalvo = diretorRepository.save(diretor);

        Optional<Diretor> diretorOptional = diretorRepository.findById(diretorSalvo.getId());

        Assertions.assertThat(diretorOptional)
                .isNotEmpty();
    }
}