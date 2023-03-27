package br.com.escola.admin.controllers;

import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Professor;
import br.com.escola.admin.services.ProfessorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfessorController.class)
class ProfessorControllerTest {
    private static final String PROFESSOR_PATH = "/professores";
    @Autowired
    MockMvc mvc;
    @MockBean
    ProfessorService professorServiceMock;

    //---------CONSULTA DE LISTA DE PROFESSORES---------
    @Test
    @DisplayName("Deve retornar 200 quando não houver professores na lista")
    void deveRetornarListaVaziaDeProfessores() throws Exception {
        // given
        List<Professor> professores = List.of();
        BDDMockito.given(professorServiceMock.obterProfessores()).willReturn(professores);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROFESSOR_PATH)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("length()").value(0));
    }

    @Test
    @DisplayName("Deve retornar 200 quando houver professores")
    void deveRetornarListaDeProfessores() throws Exception {
        // given
        List<Professor> professores = List.of(new Professor("Paulo", "78945612388", "Geografia"),
                new Professor("Pedro", "12345678977", "Quimica"));
        BDDMockito.given(professorServiceMock.obterProfessores()).willReturn(professores);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROFESSOR_PATH)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("[0].nome").value("Paulo"))
                .andExpect(jsonPath("[0].cpf").value("78945612388"))
                .andExpect(jsonPath("[0].especialidade").value("Geografia"))
                .andExpect(jsonPath("length()").value(2));

    }

    //---------CONSULTA DE PROFESSOR---------

    @Test
    @DisplayName("Deve retornar 404 quando consultar um id inexistente")
    void deveRetornar404QuandoConsultarIdInexistente() throws Exception {
        // given
        long id = 1L;

        given(professorServiceMock.obterProfessorPorId(id))
                .willThrow(new ResourceNotFoundException("Professor não encontrado"));

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROFESSOR_PATH + "/" + id)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Professor não encontrado"))
                .andExpect(jsonPath("status").value("404"));

        verify(professorServiceMock, times(1)).obterProfessorPorId(id);

    }

    @Test
    @DisplayName("Deve retornar 200 quando consultar um id existente")
    void deveRetornarUsuariaConsultado() throws Exception {
        // given
        long id = 1L;

        given(professorServiceMock.obterProfessorPorId(id)).willReturn(
                new Professor("Pedro", "12345678911", "Matematica"));

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROFESSOR_PATH + "/" + id)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("nome").value("Pedro"))
                .andExpect(jsonPath("cpf").value("12345678911"))
                .andExpect(jsonPath("especialidade").value("Matematica"));

        verify(professorServiceMock, times(1)).obterProfessorPorId(id);

    }

    //---------CRIAÇÃO DE PROFESSOR---------

    @Test
    @DisplayName("Deve retornar 201 quando criar o professor")
    void deveCriarProfessorComSucesso() throws Exception {
        // given
        Professor professor = new Professor("Pedro", "12345678911", "Matematica");

        String json = new ObjectMapper().writeValueAsString(professor);

        BDDMockito.given(professorServiceMock.criarProfessor(any())).willReturn(professor);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PROFESSOR_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        // then
        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("nome").value("Pedro"))
                .andExpect(jsonPath("cpf").value("12345678911"))
                .andExpect(jsonPath("especialidade").value("Matematica"));

    }

    //---------ALTERAÇÃO DE PROFESSOR---------

    @Test
    @DisplayName("Deve retornar 200 quando alterar professor com sucesso")
    void deveAlterarProfessorComSucesso() throws Exception{
        // given
        Professor professorNovo = new Professor("Pedro","12345678911","Quimica");
        professorNovo.setId(1L);

        BDDMockito.given(professorServiceMock.atualizarProfessorPorId(any(),any())).willReturn(professorNovo);

        String json = new ObjectMapper().writeValueAsString(professorNovo);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(PROFESSOR_PATH + "/" + professorNovo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        // then
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("nome").value("Pedro"))
                .andExpect(jsonPath("cpf").value("12345678911"))
                .andExpect(jsonPath("especialidade").value("Quimica"));

    }

    //---------REMOÇÃO DE PROFESSOR---------

    @Test
    @DisplayName("Deve retornar 204 quando deletar professor com sucesso")
    void deveDeletarProfessorComSucesso() throws Exception {
        // given
        Long id = 1L;

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(PROFESSOR_PATH+ "/" + id)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andExpect(status().isNoContent());

        verify(professorServiceMock, times(1)).removerProfessorPorId(id);
    }

}