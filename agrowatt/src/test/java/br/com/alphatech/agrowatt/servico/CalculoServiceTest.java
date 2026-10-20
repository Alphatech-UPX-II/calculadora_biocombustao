package br.com.alphatech.agrowatt.servico;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

import br.com.alphatech.agrowatt.dominio.Calculo;
import br.com.alphatech.agrowatt.repositorio.CalculoRepository;
import br.com.alphatech.agrowatt.repositorio.ResiduoRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CalculoServiceTest {

    @Autowired CalculoService service;
    @Autowired CatalogoService catalogo;
    @Autowired ResiduoRepository residuos;
    @Autowired CalculoRepository calculos;
    @Autowired EntityManager em;

    Integer id(String nome) {
        return residuos.findByNome(nome).orElseThrow().getId();
    }

    @Test
    void premissasVemDoBanco() {
        var p = catalogo.premissas();

        assertThat(p.pciMetanoKwhM3()).isEqualTo(10.0);
        assertThat(p.gwpMetano()).isEqualTo(27.0);
        assertThat(p.diasMes()).isEqualTo(30);
    }

    @Test
    void calculaGravaEReconstroiOResultado() {
        Integer calculoId = service.calcular(id("Esterco de gado"), 1000);
        em.flush();
        em.clear();

        DetalheCalculo d = service.buscar(calculoId);
        assertThat(d.residuo()).isEqualTo("Esterco de gado");
        assertThat(d.massaKg()).isEqualTo(1000.0);
        assertThat(d.resultado().dia().metanoM3()).isCloseTo(29.44, within(1e-3));
        assertThat(d.resultado().dia().biogasM3()).isCloseTo(49.067, within(1e-3));
        assertThat(d.resultado().dia().eletricaMaxKwh()).isCloseTo(111.872, within(1e-3));
        assertThat(d.resultado().mes().energiaKwh()).isCloseTo(8832.0, within(1e-2));
    }

    @Test
    void historicoTrazMaisRecentePrimeiro() {
        service.calcular(id("Frutas e vegetais"), 500);
        Integer segundo = service.calcular(id("Esterco de frango"), 1000);

        var historico = service.listarHistorico();
        assertThat(historico.get(0).id()).isEqualTo(segundo);
        assertThat(historico.get(0).residuo()).isEqualTo("Esterco de frango");
        assertThat(historico.get(0).metanoM3Dia()).isCloseTo(41.44, within(1e-3));
    }

    @Test
    void residuoInexistenteNaoExiste() {
        assertThat(catalogo.existeResiduo(999_999)).isFalse();
        assertThat(catalogo.existeResiduo(null)).isFalse();
        assertThat(catalogo.existeResiduo(id("Esterco de gado"))).isTrue();
    }

    @Test
    void calculoSemResultadoInseridoForaDaAplicacaoNaoDerrubaAsTelas() {
        Calculo semResultado = calculos.saveAndFlush(new Calculo(
                residuos.findByNome("Esterco de gado").orElseThrow(), 10, LocalDateTime.of(2026, 10, 19, 9, 0)));
        Integer valido = service.calcular(id("Esterco de gado"), 1000);
        em.flush();
        em.clear();

        assertThat(service.listarHistorico()).extracting(LinhaHistorico::id).contains(valido)
                .doesNotContain(semResultado.getId());
        assertThatThrownBy(() -> service.buscar(semResultado.getId()))
                .isInstanceOf(CalculoNaoEncontradoException.class);
    }

    @Test
    void buscarInexistenteLancaExcecao() {
        assertThatThrownBy(() -> service.buscar(999_999))
                .isInstanceOf(CalculoNaoEncontradoException.class);
    }
}
