package br.com.alphatech.agrowatt.servico;

public class CalculoNaoEncontradoException extends RuntimeException {

    public CalculoNaoEncontradoException(Integer id) {
        super("Cálculo não encontrado: " + id);
    }
}
