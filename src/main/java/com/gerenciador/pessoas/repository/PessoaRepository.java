package com.gerenciador.pessoas.repository;

import com.gerenciador.pessoas.entitys.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

    @Query(value = "SELECT * FROM PESSOA WHERE (NOME = ?1 OR ?1 IS NULL)" +
            " AND (CPF = ?2 OR ?2 IS NULL)" +
            " AND (DATA_NASCIMENTO = ?3 OR ?3 IS NULL) " +
            " AND (EMAIL = ?4 OR ?4 IS NULL)",
            countQuery = "SELECT count(*) FROM PESSOA WHERE (NOME = ?1 OR ?1 IS NULL) " +
                    " AND (CPF = ?2 OR ?2 IS NULL) " +
                    " AND (DATA_NASCIMENTO = ?3 OR ?3 IS NULL)" +
                    " AND (EMAIL = ?4 OR ?4 IS NULL)",
            nativeQuery = true)
    Page<Pessoa> findByNomeAndCpfAndDataNascimentoAndEmail(String nome, String cpf, LocalDate dataNascimento, String email, Pageable pages);

    boolean existsByCpf(String cpf);

    Page<Pessoa> findAll(Pageable pages);
}
