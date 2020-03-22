package com.gerenciador.pessoas.rest.fabrica;

import com.gerenciador.pessoas.dtos.PessoaRequest;
import com.gerenciador.pessoas.entitys.Pessoa;
import com.gerenciador.pessoas.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class PessoaFabrica {

    @Autowired
    private PessoaRepository pessoaRepository;

    public static PessoaRequest criarPessoaRequestDefault(String cpf) {
        PessoaRequest pessoaRequest = new PessoaRequest();

        if(cpf == null) {
            pessoaRequest.setCpf("508.837.318-00");
        } else {
            pessoaRequest.setCpf(cpf);
        }
        pessoaRequest.setNome("alguem");
        pessoaRequest.setEmail("alguem@mail.com");
        pessoaRequest.setFoto("testestestestes");
        pessoaRequest.setDataNascimento(LocalDate.now());
        return pessoaRequest;
    }

    public Pessoa salvarPessoaRandomBD(String cpf) {
        Pessoa pessoa = new Pessoa();

        pessoa.setCpf(cpf.replace(".", "").replace("-", ""));
        pessoa.setNome("alguem");
        pessoa.setEmail("alguem@mail.com");
        pessoa.setFoto("testestestestes");
        pessoa.setDataNascimento(LocalDate.now());

        return pessoaRepository.save(pessoa);
    }

}
