package br.com.escola.admin.services;

import br.com.escola.admin.exceptions.BusinessRuleException;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.ProfessorRepository;
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

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProfessorServiceTest {
    @Mock
    private ProfessorRepository professorRepositoryMock;
    @InjectMocks
    private ProfessorService professorService;

    @BeforeEach
    void setUp() {

    }

    //---------CONSULTA DE LISTA DE PROFESSORES---------
    @Test
    @DisplayName("Deve retornar uma lista de professores")
    public void deveRetornarTodosOsProfessores() {
        // given
        List<Professor> professores = List.of(new Professor("Paulo", "12345678911", "Matematica"));

        // when
        Mockito.when(professorRepositoryMock.findAll()).thenReturn(professores);

        List<Professor> professoresRetornados = professorService.obterProfessores();

        // then
        Assertions.assertThat(professoresRetornados).isNotNull();
        Assertions.assertThat(professoresRetornados)
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(professoresRetornados.get(0).getNome()).isEqualTo("Paulo");
        Assertions.assertThat(professoresRetornados.get(0).getCpf()).isEqualTo("12345678911");
    }

    //---------CONSULTA DE PROFESSOR---------
    @Test
    @DisplayName("Deve retornar ResourceNotFoundException quando consultar id inexistente")
    void deveRetornarExceptionQuandoConsultarIdInexistente() {
        // given
        long id = 1L;
        // when
        Mockito.when(professorRepositoryMock.findById(id))
                .thenReturn(Optional.empty());
        //then
        Assertions.assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> professorService.obterProfessorPorId(1L));
    }

    @Test
    @DisplayName("Deve retornar um professor por id")
    public void deveRetornarProfessorPorId() {
        // given
        Professor professor = new Professor("Paulo", "12345678911", "Matematica");
        professor.setId(1L);
        // when
        when(professorRepositoryMock.findById(professor.getId())).thenReturn(Optional.of(professor));
        Professor professorRetornado = professorService.obterProfessorPorId(1L);
        // then
        verify(professorRepositoryMock, times(1)).findById(professor.getId());
        Assertions.assertThat(professorRetornado).isNotNull();
        Assertions.assertThat(professor).isEqualTo(professorRetornado);
    }

    //---------CRIAÇÃO DE PROFESSOR---------

    @Test
    @DisplayName("Deve lançar BusinessRuleException quando tentar criar com o mesmo cpf")
    public void deveFalharAoCriarProfessorComMesmoCpf() {
        // given
        Professor professor = new Professor("Paulo", "12345678911", "Matematica");

        // when
        when(professorRepositoryMock.save(professor)).thenReturn(professor);
        doThrow(BusinessRuleException.class).when(professorRepositoryMock).save(professor);

        // then
        verify(professorRepositoryMock, never()).save(professor);
        Assertions.assertThatThrownBy(() -> professorService.criarProfessor(professor))
                .isInstanceOf(BusinessRuleException.class);
    }

    @Test
    @DisplayName("Deve lançar BusinessRuleException quando tentar criar com cpf invalido")
    public void deveFalharAoCriarProfessorComMesmoCpfInvalido() {
        // given
        Professor professor = new Professor("Paulo", "12345678911", "Matematica");
        // when
        when(professorRepositoryMock.save(professor)).thenReturn(professor);
        doThrow(BusinessRuleException.class).when(professorRepositoryMock).save(professor);

        // then
        Assertions.assertThatThrownBy(() -> professorService.criarProfessor(professor))
                .isInstanceOf(BusinessRuleException.class);
        verify(professorRepositoryMock, never()).save(professor);
    }

    @Test
    @DisplayName("Deve criar o professor com sucesso")
    public void deveCriarProfessor() {
        //given
        Professor professor = new Professor("Paulo", "47970322000", "Matematica");
        //when
        Professor professorRetornado = professorService.criarProfessor(professor);
        //then
        verify(professorRepositoryMock, times(1)).save(professor);
        Assertions.assertThat(professorRetornado).isNotNull().isEqualTo(professor);
    }

    //---------ATUALIZAÇÃO DE PROFESSOR---------

    @Test
    @DisplayName("Deve lançar BusinessRuleException ao tentar atulizar com o mesmo cpf")
    public void deveFalharAoAtualizarProfessorComMesmoCpf() {
        //given
        Professor professor1 = new Professor("Paulo", "12345678911", "Matematica");
        professor1.setId(1L);
        Professor professor2 = new Professor("Pedro", "12345678911", "Matematica");
        //when
        Mockito.when(professorRepositoryMock.findById(professor1.getId())).thenReturn(Optional.of(professor1));
        doThrow(BusinessRuleException.class).when(professorRepositoryMock).findByCpf(professor2.getCpf());
        //then
        verify(professorRepositoryMock, never()).save(professor2);
        Assertions.assertThatThrownBy(() -> this.professorService.atualizarProfessorPorId(professor1.getId(), professor2))
                .isInstanceOf(BusinessRuleException.class);
    }

    @Test
    @DisplayName("Deve atulizar o professor com sucesso")
    public void deveAtualizarProfessor() {
        //given
        Professor professor1 = new Professor("Paulo", "47970322000", "Matematica");
        professor1.setId(1L);
        Professor professor2 = new Professor("Pedro", "47970322000", "Matematica");
        //when
        Mockito.when(professorRepositoryMock.findById(professor1.getId())).thenReturn(Optional.of(professor1));
        Professor professorRetornado = professorService.atualizarProfessorPorId(professor1.getId(), professor2);
        //then
        verify(professorRepositoryMock, times(1)).save(professor1);

        Assertions.assertThat(professor2.getNome()).isEqualTo(professorRetornado.getNome());
    }

    //---------REMOÇÃO DE PROFESSOR---------

    @Test
    @DisplayName("Deve remover o professor com sucesso")
    public void deveRemoverProfessorPorId() {
        //given
        Professor professor = new Professor();
        professor.setId(1L);
        //when
        Mockito.when(professorRepositoryMock.findById(professor.getId())).thenReturn(Optional.of(professor));
        //then
        Assertions.assertThatCode(() -> professorService.removerProfessorPorId(professor.getId()))
                .doesNotThrowAnyException();
    }

}