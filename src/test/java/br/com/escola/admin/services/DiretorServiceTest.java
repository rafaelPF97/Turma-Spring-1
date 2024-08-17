package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.repositories.DiretorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class DiretorServiceTest {
    @Mock
    private DiretorRepository diretorRepositoryMock;
    @InjectMocks
    private DiretorService diretorService;

    @BeforeEach
    void setUp() {

    }

    //---------CONSULTA DE LISTA DE DIRETORES---------
    @Test
    @DisplayName("Deve retornar uma lista de diretores")
    public void deveRetornarTodosOsDiretores() {
        // given
        List<Diretor> diretores = List.of(new Diretor("Paulo", "12345678911"));
        // when
        Mockito.when(diretorRepositoryMock.findAll()).thenReturn(diretores);

        List<Diretor> diretoresRetornados = diretorService.obterDiretores();
        // then

        Assertions.assertThat(diretoresRetornados)
                .isNotEmpty()
                .hasSize(1)
                .isNotNull();
        Assertions.assertThat(diretoresRetornados.get(0).getNome()).isEqualTo("Paulo");
        Assertions.assertThat(diretoresRetornados.get(0).getCpf()).isEqualTo("12345678911");
    }

    //---------CONSULTA DE DIRETOR---------
    @Test
    @DisplayName("Deve retornar ResourceNotFoundException quando consultar id inexistente")
    void deveRetornarExceptionQuandoConsultarIdInexistente() {
        // given
        long id = 1L;
        // when
        Mockito.when(diretorRepositoryMock.findById(id))
                .thenReturn(Optional.empty());
        //then
        Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> diretorService.obterDiretorPorId(1L));
    }

    @Test
    @DisplayName("Deve retornar um diretor por id")
    public void deveRetornarDiretorPorId() {
        // given
        Diretor diretor1 = new Diretor("Paulo", "12345678911");
        diretor1.setId(1L);
        // when
        Mockito.when(diretorRepositoryMock.findById(diretor1.getId())).thenReturn(Optional.of(diretor1));
        Diretor diretorRetornado = diretorService.obterDiretorPorId(1L);
        // then
        verify(diretorRepositoryMock, times(1)).findById(diretor1.getId());
        Assertions.assertThat(diretorRetornado).isNotNull();
        Assertions.assertThat(diretor1).isEqualTo(diretorRetornado);
    }

    //---------CRIAÇÃO DE DIRETOR---------

    @Test
    @DisplayName("Deve lançar BusinessRuleException quando tentar criar com o mesmo cpf")
    public void deveFalharAoCriarDiretorComMesmoCpf() {
        // given
        Diretor diretor = new Diretor("Gustavo", "47970322000");
        // when
        when(diretorRepositoryMock.findByCpf(diretor.getCpf())).thenReturn(Optional.of(diretor));
        doThrow(BusinessRuleException.class).when(diretorRepositoryMock).save(diretor);
        // then
        assertThrows(BusinessRuleException.class, () -> diretorService.criarDiretor(diretor));
        verify(diretorRepositoryMock, never()).save(diretor);
    }

    @Test
    @DisplayName("Deve criar o diretor com sucesso")
    public void deveCriarDiretor() {
        //given
        Diretor diretor = new Diretor("Paulo", "47970322000");
        //when
        Diretor diretorRetornado = diretorService.criarDiretor(diretor);
        //then
        verify(diretorRepositoryMock, times(1)).save(diretor);
        Assertions.assertThat(diretorRetornado).isNotNull().isEqualTo(diretor);
    }

    //---------ATUALIZAÇÃO DE DIRETOR---------

    @Test
    @DisplayName("Deve lançar BusinessRuleException ao tentar atulizar com o mesmo cpf")
    public void deveFalharAoAtualizarDiretorComMesmoCpf() {
        //given
        Diretor diretor1 = new Diretor("Paulo", "12345678911");
        diretor1.setId(1L);
        Diretor diretor2 = new Diretor("Gustavo", "12345678911");
        //when
        Mockito.when(diretorRepositoryMock.findById(diretor1.getId())).thenReturn(Optional.of(diretor1));
        doThrow(BusinessRuleException.class).when(diretorRepositoryMock).findByCpf(diretor2.getCpf());
        //then
        Assertions.assertThatThrownBy(() -> this.diretorService.atualizarDiretorPorId(diretor1.getId(), diretor2))
                .isInstanceOf(BusinessRuleException.class);
        verify(diretorRepositoryMock, never()).save(diretor2);
    }

    @Test
    @DisplayName("Deve atulizar o diretor com sucesso")
    public void deveAtualizarDiretor() {
        //given
        Diretor diretor1 = new Diretor("Paulo", "47970322000");
        diretor1.setId(1L);
        Diretor diretor2 = new Diretor("Gustavo", "47970322000");
        //when
        Mockito.when(diretorRepositoryMock.findById(diretor1.getId())).thenReturn(Optional.of(diretor1));
        Diretor diretorRetornado = diretorService.atualizarDiretorPorId(diretor1.getId(), diretor2);
        //then
        verify(diretorRepositoryMock, times(1)).save(diretor1);

        Assertions.assertThat(diretor2.getNome()).isEqualTo(diretorRetornado.getNome());
    }

    //---------REMOÇÃO DE DIRETOR---------

    @Test
    @DisplayName("Deve remover o diretor com sucesso")
    public void deveRemoverDiretorPorId() {
        //given
        Diretor diretor = new Diretor();
        diretor.setId(1L);
        //when
        Mockito.when(diretorRepositoryMock.findById(diretor.getId())).thenReturn(Optional.of(diretor));
        //then
        Assertions.assertThatCode(() -> diretorService.removerDiretorPorId(diretor.getId()))
                .doesNotThrowAnyException();
    }

}