package br.com.escola.admin.controllers;

import br.com.escola.admin.controllers.diretor.DiretorController;
import br.com.escola.admin.exceptions.ResourceNotFoundException;
import br.com.escola.admin.models.Diretor;
import br.com.escola.admin.services.DiretorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(DiretorController.class)
class DiretorControllerTest {
    private static final String DIRETOR_PATH = "/diretores";
    @Autowired
    MockMvc mvc;
    @MockBean
    DiretorService diretorServiceMock;

    //Criação de diretor
    //Cenario 1: Erro ao criar diretores sem dados suficientes
    //Cenario 2: Erro ao passar o cpf invalido
    //Cenario 3: Erro ao criar diretor com cpf existente
    //Cenario 4: Sucesso

    //---------CONSULTA DE LISTA DE DIRETORES---------
    @Test
    @DisplayName("Deve retornar 200 quando não houver diretores na lista")
    void deveRetornarListaVaziaDeDiretores() throws Exception {
        // given
        List<Diretor> diretores = List.of();
        given(diretorServiceMock.obterDiretores()).willReturn(diretores);
        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(DIRETOR_PATH)
                .accept(MediaType.APPLICATION_JSON);
        // then
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }

    @Test
    @DisplayName("Deve retornar 200 quando houver diretores")
    void deveRetornarListaDeDiretores() throws Exception {
        // given
        List<Diretor> diretores = List.of(new Diretor("Paulo", "12345678911"),
                new Diretor("Gustavo", "789456123654"));
        given(diretorServiceMock.obterDiretores()).willReturn(diretores);
        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(DIRETOR_PATH)
                .accept(MediaType.APPLICATION_JSON);
        // then
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].nome").value("Paulo"))
                .andExpect(jsonPath("[0].cpf").value("12345678911"))

                .andExpect(jsonPath("[1].nome").value("Gustavo"))
                .andExpect(jsonPath("[1].cpf").value("789456123654"))
                .andExpect(jsonPath("length()").value(2));
    }

    //---------CONSULTA DE DIRETOR---------

    @Test
    @DisplayName("Deve retornar 404 quando consultar um id inexistente")
    void deveRetornar404QuandoConsultarIdInexistente() throws Exception {
        // given
        long id = 1L;

        given(diretorServiceMock.obterDiretorPorId(id))
                .willThrow(new ResourceNotFoundException("Diretor não encontrado"));
        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(DIRETOR_PATH + "/" + id)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Diretor não encontrado"))
                .andExpect(jsonPath("status").value("404"));

        verify(diretorServiceMock, times(1)).obterDiretorPorId(id);

    }
    @Test
    @DisplayName("Deve retornar 200 quando consultar um id existente")
    void deveRetornarUsuariaConsultado() throws Exception {
        // given
        long id = 1L;

        given(diretorServiceMock.obterDiretorPorId(id)).willReturn(
                new Diretor("Pedro","68322050070"));

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(DIRETOR_PATH + "/" + id)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("nome").value("Pedro"))
                .andExpect(jsonPath("cpf").value("68322050070"));

    }

    //---------CRIAÇÃO DE DIRETOR---------

    @Test
    @DisplayName("Deve retornar 201 quando criar o diretor")
    void deveCriarDiretorComSucesso() throws Exception {
        // given
        Diretor diretor = new Diretor("Pedro","68322050070");

        String json = new ObjectMapper().writeValueAsString(diretor);

        given(diretorServiceMock.criarDiretor(any())).willReturn(diretor);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(DIRETOR_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        // then
        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("nome").value("Pedro"))
                .andExpect(jsonPath("cpf").value("68322050070"));

    }

    //---------ALTERAÇÃO DE DIRETOR---------
    @Test
    @DisplayName("Deve retornar 200 quando alterar diretor")
    void deveAlterarDiretorComSucesso() throws Exception {
        // given
        Diretor diretorSalvo = new Diretor("Caio","1234");
        Diretor diretorPassado = new Diretor("Pedro","68322050070");
        diretorPassado.setId(1L);
        diretorSalvo.setId(diretorPassado.getId());
        diretorSalvo.setNome(diretorPassado.getNome());
        diretorSalvo.setCpf(diretorPassado.getCpf());
        given(diretorServiceMock.atualizarDiretorPorId(any(),any())).willReturn(diretorSalvo);

        String json = new ObjectMapper().writeValueAsString(diretorSalvo);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
              .put(DIRETOR_PATH + "/" + diretorPassado.getId())
              .contentType(MediaType.APPLICATION_JSON)
              .accept(MediaType.APPLICATION_JSON)
              .content(json);

        // then
        mvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(jsonPath("nome").value("Pedro"))
               .andExpect(jsonPath("cpf").value("68322050070"));
    }

    //---------REMOÇÃO DE DIRETOR---------

    @Test
    @DisplayName("Deve retornar 204 quando deletar diretor")
    void deveDeletarDiretorComSucesso() throws Exception {
        // given
        Diretor diretor = new Diretor();
        diretor.setId(1L);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
               .delete(DIRETOR_PATH + "/" + diretor.getId())
               .accept(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andExpect(status().isNoContent());

        verify(diretorServiceMock, times(1)).removerDiretorPorId(diretor.getId());
    }
}