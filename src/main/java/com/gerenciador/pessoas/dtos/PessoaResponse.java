package com.gerenciador.pessoas.dtos;

import com.gerenciador.pessoas.entitys.Pessoa;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PessoaResponse {

    private String nome;

    private String cpf;

    private String email;

    private String foto;

    private LocalDate dataNascimento;

    public PessoaResponse(String nome, String cpf, String email, String foto, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.foto = foto;
        this.dataNascimento = dataNascimento;
    }

    public PessoaResponse() {
        super();
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getFoto() {
        return foto;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

}
