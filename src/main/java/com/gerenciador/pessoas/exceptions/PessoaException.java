package com.gerenciador.pessoas.exceptions;

public class PessoaException extends RuntimeException {

    public PessoaException(Long id) {
        super("Pessoa nao encontrada de codigo " + id);
    }

}
