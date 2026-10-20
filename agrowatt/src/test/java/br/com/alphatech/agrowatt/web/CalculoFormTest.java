package br.com.alphatech.agrowatt.web;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class CalculoFormTest {

    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    CalculoForm form(Integer idResiduo, String massa, String unidade) {
        CalculoForm f = new CalculoForm();
        f.setIdResiduo(idResiduo);
        f.setMassa(massa);
        f.setUnidade(unidade);
        return f;
    }

    Set<String> camposComErro(CalculoForm f) {
        return validator.validate(f).stream()
                .map(v -> v.getPropertyPath().toString())
                .collect(Collectors.toSet());
    }

    @Test
    void aceitaPadraoBrasileiro() {
        assertThat(CalculoForm.converter("1,5")).isEqualTo(1.5);
        assertThat(CalculoForm.converter("1.000")).isEqualTo(1000.0);
        assertThat(CalculoForm.converter("1.234,5")).isEqualTo(1234.5);
        assertThat(CalculoForm.converter("2.5")).isEqualTo(2.5);
        assertThat(CalculoForm.converter("abc")).isNull();
    }

    @Test
    void toneladasViramQuilos() {
        assertThat(form(1, "1,5", "t").massaEmKg()).isEqualTo(1500.0);
        assertThat(form(1, "250", "kg").massaEmKg()).isEqualTo(250.0);
    }

    @Test
    void formularioValido() {
        assertThat(camposComErro(form(1, "1.000", "kg"))).isEmpty();
        assertThat(camposComErro(form(1, "1.000", "t"))).isEmpty();
    }

    @Test
    void residuoEMassaObrigatorios() {
        assertThat(camposComErro(form(null, "", "kg"))).contains("idResiduo", "massa");
    }

    @Test
    void massaInvalidaOuForaDaFaixa() {
        assertThat(camposComErro(form(1, "abc", "kg"))).contains("massaValida");
        assertThat(camposComErro(form(1, "0", "kg"))).contains("massaValida");
        assertThat(camposComErro(form(1, "0,001", "kg"))).contains("massaValida");
        assertThat(camposComErro(form(1, "1.001", "t"))).contains("massaValida");
        assertThat(camposComErro(form(1, "NaN", "kg"))).contains("massaValida");
    }

    @Test
    void unidadeInvalida() {
        assertThat(camposComErro(form(1, "10", "lb"))).contains("unidade");
    }
}
