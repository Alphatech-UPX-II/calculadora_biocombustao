package br.com.alphatech.agrowatt.web;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import br.com.alphatech.agrowatt.repositorio.ResiduoRepository;
import br.com.alphatech.agrowatt.servico.CalculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CalculoControllerTest {

    @Autowired WebApplicationContext contexto;
    @Autowired ResiduoRepository residuos;
    @Autowired CalculoService service;
    MockMvc mvc;

    @BeforeEach
    void configurar() {
        mvc = MockMvcBuilders.webAppContextSetup(contexto).build();
    }

    Integer gado() {
        return residuos.findByNome("Esterco de gado").orElseThrow().getId();
    }

    @Test
    void inicio() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Novo cálculo")));
    }

    @Test
    void formularioListaOsResiduosDoBanco() throws Exception {
        mvc.perform(get("/calculos/novo"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Esterco de gado")))
                .andExpect(content().string(containsString("Restos de alimentos (cozinhas)")));
    }

    @Test
    void postValidoRedirecionaParaOResultado() throws Exception {
        mvc.perform(post("/calculos")
                        .param("idResiduo", gado().toString())
                        .param("massa", "1.000")
                        .param("unidade", "kg"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/calculos/*"));
    }

    @Test
    void postInvalidoVoltaAoFormularioMantendoOsDados() throws Exception {
        mvc.perform(post("/calculos")
                        .param("idResiduo", gado().toString())
                        .param("massa", "abc")
                        .param("unidade", "kg"))
                .andExpect(status().isOk())
                .andExpect(view().name("calculos/novo"))
                .andExpect(content().string(containsString("value=\"abc\"")))
                .andExpect(content().string(containsString("entre 0,01 kg e 1.000.000 kg")));
    }

    @Test
    void residuoInexistenteVoltaAoFormulario() throws Exception {
        mvc.perform(post("/calculos")
                        .param("idResiduo", "999999")
                        .param("massa", "100")
                        .param("unidade", "kg"))
                .andExpect(status().isOk())
                .andExpect(view().name("calculos/novo"))
                .andExpect(content().string(containsString("Escolha um resíduo da lista")));
    }

    @Test
    void resultadoMostraCasoDeReferenciaEmFormatoBrasileiro() throws Exception {
        Integer id = service.calcular(gado(), 1000);

        mvc.perform(get("/calculos/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Esterco de gado")))
                .andExpect(content().string(containsString("200,0")))
                .andExpect(content().string(containsString("29,4")))
                .andExpect(content().string(containsString("294,4")))
                .andExpect(content().string(containsString("73,6 – 111,9")));
    }

    @Test
    void calculoInexistenteDa404() throws Exception {
        mvc.perform(get("/calculos/999999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Cálculo não encontrado")));
    }

    @Test
    void idNaoNumericoDa404() throws Exception {
        mvc.perform(get("/calculos/abc"))
                .andExpect(status().isNotFound());
    }
}
