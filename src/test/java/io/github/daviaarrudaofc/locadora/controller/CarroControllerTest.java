package io.github.daviaarrudaofc.locadora.controller;

import io.github.daviaarrudaofc.locadora.entities.CarroEntity;
import io.github.daviaarrudaofc.locadora.exceptions.EntityNotFoundException;
import io.github.daviaarrudaofc.locadora.service.CarroService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarroController.class)// cria um contexto para testar somente aql parte da API, ele n vai subir a API completa
public class CarroControllerTest {       // so vai subir nesse test, o que ce quer testar

    @Autowired
    MockMvc mvc; // ele serve como um client Http para fazer os teste na nossa API

    @MockitoBean// Cria um mock do serviço para o Spring passar pro controller.
    CarroService carroService;


    @Test
    void deveSalvarUmCarro() throws Exception{
        //1. Cenário
        CarroEntity carro = new CarroEntity(1L, "Honda Civic", 150.0, 2027);

        // antes nesse mOckito.any tava a variavel carro, por isso que tava dando erro
        Mockito.when(carroService.salvar(Mockito.any())).thenReturn(carro);

        String json = """
                {
                    "modelo" : "Honda Civic",
                    "valorDiaria":  150.0,
                    "ano" : 2027
                }
                """;

        //2. Execução
        ResultActions result = mvc.perform(
                post("/carros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)

        );
        //3. Verificação
        result
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelo").value("Honda Civic"));

    }


    @Test
    void deveObterDetalhesCarro() throws Exception{
        Mockito.when(carroService.buscarPorId(Mockito.any()))
                .thenReturn(new CarroEntity(1L, "Civic", 250.0, 2028));

        mvc.perform(
                MockMvcRequestBuilders.get("/carros/1")
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.modelo").value("Civic"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorDiaria").value(250.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ano").value(2028));
    }

    @Test
    void deveRetornarNotFoundAoTentarObterDetalhesCarroInexistente() throws Exception{
        Mockito.when(carroService.buscarPorId(Mockito.any()))
                        .thenThrow(EntityNotFoundException.class);
        mvc.perform(
                        MockMvcRequestBuilders.get("/carros/1")
                ).andExpect(status().isNotFound());
    }


    @Test
    void deveListarCarros()throws Exception{
        var listagem = List.of(
                new CarroEntity(1L, "Argo", 150.0, 2025),
                new CarroEntity(2L, "Celta", 80.0, 2015)
        );

        Mockito.when(carroService.listarTodos()).thenReturn(listagem);

        mvc.perform(
                MockMvcRequestBuilders.get("/carros")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].modelo").value("Argo"))
                .andExpect(jsonPath("$[1].modelo").value("Celta"));
    }

}
