package br.com.alphatech.agrowatt.servico;

import br.com.alphatech.agrowatt.calculo.CalculadoraBiogas;
import br.com.alphatech.agrowatt.calculo.Grandezas;
import br.com.alphatech.agrowatt.calculo.ResultadoCalculo;
import br.com.alphatech.agrowatt.dominio.Calculo;
import br.com.alphatech.agrowatt.dominio.Residuo;
import br.com.alphatech.agrowatt.dominio.Resultado;
import br.com.alphatech.agrowatt.repositorio.CalculoRepository;
import br.com.alphatech.agrowatt.repositorio.ResiduoRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalculoService {

    private final CalculoRepository calculos;
    private final ResiduoRepository residuos;
    private final CatalogoService catalogo;

    public CalculoService(CalculoRepository calculos, ResiduoRepository residuos, CatalogoService catalogo) {
        this.calculos = calculos;
        this.residuos = residuos;
        this.catalogo = catalogo;
    }

    @Transactional
    public Integer calcular(Integer idResiduo, double massaKg) {
        Residuo residuo = residuos.findById(idResiduo)
                .orElseThrow(() -> new IllegalArgumentException("Resíduo inexistente: " + idResiduo));
        Grandezas dia = calculadora().calcular(residuo.paraParametros(), massaKg).dia();

        Calculo calculo = new Calculo(residuo, massaKg, LocalDateTime.now());
        calculo.registrarResultado(dia.massaStKg(), dia.massaSvKg(), dia.metanoM3(), dia.biogasM3(), dia.energiaKwh());
        return calculos.save(calculo).getId();
    }

    @Transactional(readOnly = true)
    public DetalheCalculo buscar(Integer id) {
        Calculo c = calculos.findById(id)
                .filter(calculo -> calculo.getResultado() != null)
                .orElseThrow(() -> new CalculoNaoEncontradoException(id));
        return new DetalheCalculo(c.getId(), c.getResiduo().getNome(), c.getMassaKg(), c.getDataCalculo(),
                reconstruir(calculadora(), c.getResultado()));
    }

    @Transactional(readOnly = true)
    public List<LinhaHistorico> listarHistorico() {
        // Linhas de calculos sem resultado (inseridas fora da aplicação, ex.: no Workbench) não entram no histórico.
        return calculos.findAllByOrderByDataCalculoDescIdDesc().stream()
                .filter(c -> c.getResultado() != null)
                .map(c -> new LinhaHistorico(c.getId(), c.getDataCalculo(), c.getResiduo().getNome(),
                        c.getMassaKg(), c.getResultado().getVolumeMetanoM3()))
                .toList();
    }

    private CalculadoraBiogas calculadora() {
        return new CalculadoraBiogas(catalogo.premissas());
    }

    private static ResultadoCalculo reconstruir(CalculadoraBiogas calculadora, Resultado r) {
        return calculadora.aPartirDoArmazenado(r.getMassaStKg(), r.getMassaSvKg(), r.getVolumeMetanoM3(),
                r.getVolumeBiogasM3(), r.getEnergiaKwh());
    }
}
