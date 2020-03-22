package com.gerenciador.pessoas.data;

import com.gerenciador.pessoas.entitys.Pessoa;
import com.gerenciador.pessoas.repository.PessoaRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PessoaTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    private Pessoa pessoa;

    @Before
    public void init() {
        pessoa = new Pessoa();
        pessoa.setCpf("00000000000");
        pessoa.setNome("alguem");
        pessoa.setEmail("alguem@mail.com");
    }

    @Test
    public void savePessoa() {
        pessoaRepository.save(pessoa);
        Assert.assertNotNull(pessoa.getId());
    }

}
