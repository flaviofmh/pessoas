package com.gerenciador.pessoas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PessoaAdvice {

    @ResponseBody
    @ExceptionHandler(PessoaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String pessoaNotFoundHandler(PessoaException ex) {
        return ex.getMessage();
    }

}
