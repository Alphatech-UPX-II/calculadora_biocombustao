package br.com.alphatech.agrowatt.servico;

import java.time.LocalDateTime;

public record LinhaHistorico(
        Integer id,
        LocalDateTime dataCalculo,
        String residuo,
        double massaKg,
        double metanoM3Dia) {
}
