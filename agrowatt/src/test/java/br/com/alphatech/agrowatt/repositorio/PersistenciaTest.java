package br.com.alphatech.agrowatt.repositorio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import br.com.alphatech.agrowatt.dominio.Calculo;
import br.com.alphatech.agrowatt.dominio.Residuo;
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
class PersistenciaTest {

    @Autowired ResiduoRepository residuos;
    @Autowired CalculoRepository calculos;
    @Autowired PremissaRepository premissas;
    @Autowired EntityManager em;

    @Test
    void catalogoDoRogerCarregadoPeloFlyway() {
        assertThat(residuos.findAllByOrderByIdAsc())
                .extracting(Residuo::getNome)
                .containsExactly("Frutas e vegetais", "Restos de alimentos (cozinhas)", "Resíduos domiciliares",
                        "Esterco de gado", "Esterco de frango");
        assertThat(premissas.count()).isEqualTo(7);
        assertThat(premissas.findById("GWP_METANO").orElseThrow().getValor()).isEqualTo(27.0);
    }

    @Test
    void residuoViraParametrosDeCalculo() {
        Residuo gado = residuos.findByNome("Esterco de gado").orElseThrow();
        var parametros = gado.paraParametros();

        assertThat(parametros.percentualSt()).isEqualTo(20.0);
        assertThat(parametros.percentualSv()).isEqualTo(80.0);
        assertThat(parametros.rendimentoMetano()).isEqualTo(0.184);
        assertThat(parametros.percentualMetano()).isEqualTo(60.0);
        assertThat(gado.getPpm()).isEqualTo(184.0);
        assertThat(gado.getPe()).isEqualTo(1.83);
    }

    @Test
    void gravaCalculoComResultadoERecarrega() {
        Calculo c = new Calculo(residuos.findByNome("Esterco de gado").orElseThrow(), 1000,
                LocalDateTime.of(2026, 10, 19, 10, 0));
        c.registrarResultado(200, 160, 29.44, 49.0666667, 294.4);
        Integer id = calculos.saveAndFlush(c).getId();
        em.clear();

        Calculo carregado = calculos.findById(id).orElseThrow();
        assertThat(carregado.getMassaKg()).isEqualTo(1000.0);
        assertThat(carregado.getResiduo().getNome()).isEqualTo("Esterco de gado");
        assertThat(carregado.getResultado().getVolumeBiogasM3()).isCloseTo(49.0667, within(1e-9));
    }
}
