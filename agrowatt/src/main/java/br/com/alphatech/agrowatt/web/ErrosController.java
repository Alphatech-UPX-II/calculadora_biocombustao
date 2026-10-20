package br.com.alphatech.agrowatt.web;

import br.com.alphatech.agrowatt.servico.CalculoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ErrosController {

    @ExceptionHandler({CalculoNaoEncontradoException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String naoEncontrado() {
        return "erro/404";
    }
}
