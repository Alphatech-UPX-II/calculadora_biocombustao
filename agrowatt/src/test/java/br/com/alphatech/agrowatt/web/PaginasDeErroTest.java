package br.com.alphatech.agrowatt.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

/** Sobe o servidor de verdade: as páginas de erro do Spring Boot só aparecem com o container. */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PaginasDeErroTest {

    @Autowired Environment ambiente;

    HttpResponse<String> get(String caminho) throws Exception {
        String porta = ambiente.getRequiredProperty("local.server.port");
        HttpRequest requisicao = HttpRequest.newBuilder(URI.create("http://localhost:" + porta + caminho))
                .header("Accept", "text/html")
                .build();
        return HttpClient.newHttpClient().send(requisicao, HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void rotaInexistenteMostraPagina404EmPortugues() throws Exception {
        HttpResponse<String> resposta = get("/pagina-que-nao-existe");

        assertThat(resposta.statusCode()).isEqualTo(404);
        assertThat(resposta.body()).contains("Página não encontrada").doesNotContain("Whitelabel");
    }

    @Test
    void calculoInexistenteContinuaComPagina404() throws Exception {
        HttpResponse<String> resposta = get("/calculos/999999");

        assertThat(resposta.statusCode()).isEqualTo(404);
        assertThat(resposta.body()).contains("Cálculo não encontrado");
    }
}
