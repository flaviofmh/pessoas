package com.gerenciador.pessoas.dtos;

import com.gerenciador.pessoas.entitys.Pessoa;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PessoaResponse {

    private Long id;

    private String nome;

    private String cpf;

    private String email;

    private String foto;

    private LocalDate dataNascimento;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
