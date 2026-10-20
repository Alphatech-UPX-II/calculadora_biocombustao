package br.com.alphatech.agrowatt.servico;

import br.com.alphatech.agrowatt.calculo.Premissas;
import br.com.alphatech.agrowatt.dominio.Premissa;
import br.com.alphatech.agrowatt.dominio.Residuo;
import br.com.alphatech.agrowatt.repositorio.PremissaRepository;
import br.com.alphatech.agrowatt.repositorio.ResiduoRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CatalogoService {

    private final ResiduoRepository residuos;
    private final PremissaRepository premissas;

    public CatalogoService(ResiduoRepository residuos, PremissaRepository premissas) {
        this.residuos = residuos;
        this.premissas = premissas;
    }

    public List<Residuo> residuos() {
        return residuos.findAllByOrderByIdAsc();
    }

    public boolean existeResiduo(Integer id) {
        return id != null && residuos.existsById(id);
    }

    public List<Premissa> listarPremissas() {
        return premissas.findAll(Sort.by("chave"));
    }

    public Premissas premissas() {
        Map<String, Double> valores = premissas.findAll().stream()
                .collect(Collectors.toMap(Premissa::getChave, Premissa::getValor));
        return new Premissas(
                obter(valores, "PCI_METANO_KWH_M3"),
                obter(valores, "EFICIENCIA_ELETRICA_MIN"),
                obter(valores, "EFICIENCIA_ELETRICA_MAX"),
                obter(valores, "DIESEL_L_POR_M3_BIOGAS"),
                obter(valores, "DENSIDADE_METANO_KG_M3"),
                obter(valores, "GWP_METANO"),
                (int) obter(valores, "DIAS_MES"));
    }

    private static double obter(Map<String, Double> valores, String chave) {
        Double valor = valores.get(chave);
        if (valor == null) {
            throw new IllegalStateException("Premissa não cadastrada: " + chave);
        }
        return valor;
    }
}
