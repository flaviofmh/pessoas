package com.gerenciador.pessoas.dtos;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class PessoaRequest {

    @NotNull
    private String nome;

    @NotNull
    private String cpf;

    @NotNull
    private String email;

    @NotNull
    private String foto;

    @NotNull
    private LocalDate dataNascimento;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
