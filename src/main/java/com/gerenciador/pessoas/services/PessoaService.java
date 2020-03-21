package com.gerenciador.pessoas.services;

import com.gerenciador.pessoas.entitys.Pessoa;
import com.gerenciador.pessoas.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PessoaService {

    @Autowired
    public PessoaRepository pessoaRepository;

    public Page<Pessoa> listarPessoas(int page, int count, String nome, String cpf, LocalDate dataNascimento, String email) {
        if(count > 50) {
            count = 50;
        }
        Pageable pages = PageRequest.of(page, count);
        return this.pessoaRepository.findByNomeAndCpfAndDataNascimentoAndEmail(pages, nome, cpf, dataNascimento, email);
    }

}
