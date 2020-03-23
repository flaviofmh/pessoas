package com.gerenciador.pessoas.services;

import com.gerenciador.pessoas.BusinessException;
import com.gerenciador.pessoas.MessagesExceptions;
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
import java.util.concurrent.ExecutionException;

@Service
public class PessoaService {

    @Autowired
    public PessoaRepository pessoaRepository;

    public Page<Pessoa> listarPessoas(int page, int count, String nome, String cpf, LocalDate dataNascimento, String email) {
        if(count > 50) {
            count = 50;
        }
        Pageable pages = PageRequest.of(page, count);
        if(nome != null || cpf != null || dataNascimento != null || email != null) {
            return this.pessoaRepository.findByNomeAndCpfAndDataNascimentoAndEmail(nome, cpf, dataNascimento, email, pages);
        } else {
            return this.pessoaRepository.findByAtivoIsTrue(pages);
        }
    }

    public Pessoa salvarPessoa(Pessoa pessoa, Long id) throws Exception {

        if(pessoa.getCpf() == null) {
            BusinessException.execptionCustom(MessagesExceptions.EXCEPTION_NULL_FIELD);
        }
        pessoa.setCpf(pessoa.getCpf().replace(".", "").replace("-", ""));

        if(id != null) {
            if(pessoaRepository.existsById(id)) {
                pessoa.setId(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, MessagesExceptions.PESSOA_INEXISTENTE);
            }
        }

        if(id == null && pessoaRepository.existsByCpf(pessoa.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessagesExceptions.CPF_EXISTENTE);
        }

        return this.pessoaRepository.save(pessoa);
    }

    public void deletarPessoa(Long id) {
        if(id == null) BusinessException.execptionCustom(MessagesExceptions.EXCEPTION_NULL_FIELD);
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(id);

        if(pessoaOptional.isPresent() && pessoaOptional.get().getAtivo() == Boolean.TRUE) {
            pessoaOptional.get().setAtivo(Boolean.FALSE);
            pessoaRepository.save(pessoaOptional.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, MessagesExceptions.PESSOA_INVALIDA);
    }

    public Pessoa findById(Long id) {
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(id);
        if(pessoaOpt.isPresent()) {
            return pessoaOpt.get();
        }
        return null;
    }
}
