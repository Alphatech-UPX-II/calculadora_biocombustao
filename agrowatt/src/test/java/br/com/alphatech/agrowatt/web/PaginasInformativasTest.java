package br.com.alphatech.agrowatt.web;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
class PaginasInformativasTest {

    @Autowired WebApplicationContext contexto;
    @Autowired ResiduoRepository residuos;
    @Autowired CalculoService service;
    MockMvc mvc;

    @BeforeEach
    void configurar() {
        mvc = MockMvcBuilders.webAppContextSetup(contexto).build();
    }

    @Test
    void historicoListaCalculoGravado() throws Exception {
        Integer frango = residuos.findByNome("Esterco de frango").orElseThrow().getId();
        Integer id = service.calcular(frango, 750);

        mvc.perform(get("/calculos"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Esterco de frango")))
                .andExpect(content().string(containsString("/calculos/" + id)));
    }

    @Test
    void comoCalculamosMostraParametrosEFontesDoBanco() throws Exception {
        mvc.perform(get("/como-calculamos"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Embrapa Documentos 195")))
                .andExpect(content().string(containsString("0,184")))
                .andExpect(content().string(containsString("IPCC AR6")));
    }
}
