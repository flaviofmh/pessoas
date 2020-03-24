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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

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
        pessoa.setCpf("68141751778");
        pessoa.setNome("alguem");
        pessoa.setEmail("alguem@mail.com");
        pessoa.setFoto("asdasdasdasdasd");
    }

    @Test
    public void savePessoa() {
        pessoaRepository.save(pessoa);
        Assert.assertNotNull(pessoa.getId());
    }

    @Test
    public void listarPessoasAtivoFalse() {
        Pageable pages = PageRequest.of(0, 10);

        pessoa.setAtivo(Boolean.FALSE);
        pessoaRepository.save(pessoa);

        Page<Pessoa> pessoasPage = pessoaRepository.findByAtivoIsTrue(pages);
        Assert.assertTrue(pessoasPage.getTotalElements() == 0);
    }

    @Test
    public void listarPessoaPaginadoFiltro() {
        Pageable pages = PageRequest.of(0, 10);

        Pessoa pessoaTemp = new Pessoa();
        pessoaTemp.setCpf("05875868503");
        pessoaTemp.setEmail("asddsf@mail.com.br");
        pessoaTemp.setDataNascimento(LocalDate.now());
        pessoaTemp.setNome("teste teste");
        pessoaTemp.setFoto("ooooooooooo");
        pessoaRepository.save(pessoaTemp);

        Page<Pessoa> pessoasPage = pessoaRepository.findByNomeAndCpfAndDataNascimentoAndEmail(pessoaTemp.getNome(), null, null, null, pages);
        Assert.assertNotNull(pessoasPage);
        Assert.assertTrue(pessoasPage.getTotalElements() > 0);
    }

}
