package com.gerenciador.pessoas.services;

import com.gerenciador.pessoas.entitys.Pessoa;
import com.gerenciador.pessoas.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

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

    public Pessoa salvarPessoa(Pessoa pessoa, Long id) throws Exception {

        pessoa.setCpf(pessoa.getCpf().replace(".", "").replace("-", ""));

        if(id != null) {
            if(pessoaRepository.existsById(id)) {
                pessoa.setId(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa inexistente");
            }
        }

        if(id == null && pessoaRepository.existsByCpf(pessoa.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF duplicado");
        }

        return this.pessoaRepository.save(pessoa);
    }

    public void deletarPessoa(Long id) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);

        if(pessoaOptional.isPresent() && pessoaOptional.get().getAtivo() == Boolean.TRUE) {
            pessoaOptional.get().setAtivo(Boolean.FALSE);
            pessoaRepository.save(pessoaOptional.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa invalida");
    }

}
