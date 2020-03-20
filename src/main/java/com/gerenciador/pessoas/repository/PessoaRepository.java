package com.gerenciador.pessoas.repository;

import com.gerenciador.pessoas.entitys.Pessoa;
import org.springframework.data.repository.CrudRepository;

public interface PessoaRepository extends CrudRepository<Pessoa, Long> {
}
