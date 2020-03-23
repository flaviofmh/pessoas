package com.gerenciador.pessoas.repository;

import com.gerenciador.pessoas.entitys.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

    Page<Pessoa> findByNomeAndCpfAndDataNascimentoAndEmail(Pageable pages, String nome, String cpf, LocalDate dataNascimento, String email);

    boolean existsByCpf(String cpf);

    Page<Pessoa> findAll(Pageable pages);
}
