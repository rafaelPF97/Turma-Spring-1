package br.com.escola.admin.controllers;

import br.com.escola.admin.models.Professor;
import br.com.escola.admin.repositories.ProfessorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("dev")
class ProfessorControllerTest {
    private static final String PROFESSOR_PATH = "/professores";
    @Autowired
    MockMvc mvc;
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    ObjectMapper objectMapper;

    //---------CONSULTA DE LISTA DE PROFESSORES---------
    @Test
    @DisplayName("Deve retornar 200 quando não houver professores na lista")
    void deveRetornarListaVaziaDeProfessores() throws Exception {

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROFESSOR_PATH)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("length()").value(0))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("Deve retornar 200 quando houver professores")
    void deveRetornarListaDeProfessores() throws Exception {
        // given
        List<Professor> professores = List.of(new Professor("Paulo", "78945612388", "Geografia"),
                new Professor("Pedro", "31939353017", "Quimica"));

        professorRepository.saveAll(professores);
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
                .andExpect(jsonPath("[1].nome").value("Pedro"))
                .andExpect(jsonPath("[1].cpf").value("31939353017"))
                .andExpect(jsonPath("[1].especialidade").value("Quimica"))
                .andExpect(jsonPath("length()").value(2));

    }

    //---------CONSULTA DE PROFESSOR---------

    @Test
    @DisplayName("Deve retornar 404 quando consultar um id inexistente")
    void deveRetornar404QuandoConsultarIdInexistente() throws Exception {
        // given
        long id = 1L;

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROFESSOR_PATH + "/" + id)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Professor não encontrado"))
                .andExpect(jsonPath("status").value("404"))
                .andDo(print());

    }

    @Test
    @DisplayName("Deve retornar 200 quando consultar um id existente")
    void deveRetornarProfessorConsultado() throws Exception {
        // given
        Professor professor = new Professor("Pedro","31939353017","Matematica");
        long id = 1L;

        professorRepository.save(professor);
        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(PROFESSOR_PATH + "/" + id)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("nome").value("Pedro"))
                .andExpect(jsonPath("cpf").value("31939353017"))
                .andExpect(jsonPath("especialidade").value("Matematica"));

    }

    //---------CRIAÇÃO DE PROFESSOR---------

    @Test
    @DisplayName("Deve retornar 201 quando criar o professor")
    void deveCriarProfessorComSucesso() throws Exception {
        // given
        Professor professor = new Professor("Pedro", "31939353017", "Matematica");

        String json = new ObjectMapper().writeValueAsString(professor);


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
                .andExpect(jsonPath("cpf").value("31939353017"))
                .andExpect(jsonPath("especialidade").value("Matematica"));

    }

    //---------ALTERAÇÃO DE PROFESSOR---------

    @Test
    @DisplayName("Deve retornar 200 quando alterar professor com sucesso")
    void deveAlterarProfessorComSucesso() throws Exception{
        // given
        Professor professorNovo = new Professor("Pedro","41116487039","Quimica");

        Professor professorSalvo = new Professor("Elvira","31939353017","Matematica");

        professorRepository.save(professorSalvo);

        String json = new ObjectMapper().writeValueAsString(professorNovo);

        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(PROFESSOR_PATH + "/" + professorSalvo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        // then
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("nome").value("Pedro"))
                .andExpect(jsonPath("cpf").value("41116487039"))
                .andExpect(jsonPath("especialidade").value("Quimica"));

    }

    //---------REMOÇÃO DE PROFESSOR---------

    @Test
    @DisplayName("Deve retornar 204 quando deletar professor com sucesso")
    void deveDeletarProfessorComSucesso() throws Exception {
        // given
        Professor professor = new Professor("Paulo","41116487039","Spring");
        Long id = 1L;
        professorRepository.save(professor);
        // when
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(PROFESSOR_PATH+ "/" + id)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mvc.perform(request)
                .andExpect(status().isNoContent());


    }

}