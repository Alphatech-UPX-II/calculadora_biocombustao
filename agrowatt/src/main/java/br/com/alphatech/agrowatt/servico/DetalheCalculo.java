package br.com.alphatech.agrowatt.servico;

import br.com.alphatech.agrowatt.calculo.ResultadoCalculo;
import java.time.LocalDateTime;

public record DetalheCalculo(
        Integer id,
        String residuo,
        double massaKg,
        LocalDateTime dataCalculo,
        ResultadoCalculo resultado) {
}
