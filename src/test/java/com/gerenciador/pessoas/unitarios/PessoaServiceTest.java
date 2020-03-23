package com.gerenciador.pessoas.unitarios;

import com.gerenciador.pessoas.entitys.Pessoa;
import com.gerenciador.pessoas.repository.PessoaRepository;
import com.gerenciador.pessoas.services.PessoaService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepositoryMock;

    @InjectMocks
    private PessoaService pessoaService;

    private Pessoa pessoaSalva;

    private Pessoa pessoaInstanciada;

    private List<Pessoa> pessoas;

    private Random random;

    @Before
    public void init() {
        random = new Random();
        pessoas = new ArrayList<>();

        pessoaSalva = new Pessoa();
        pessoaSalva.setId(1L);
        pessoaSalva.setCpf("000.000.000-00");
        pessoaSalva.setEmail("asddsf@mail.com.br");
        pessoaSalva.setDataNascimento(LocalDate.now());
        pessoaSalva.setNome("teste teste");
        pessoaSalva.setFoto("ooooooooooo");

        pessoaInstanciada = new Pessoa();
        pessoaInstanciada.setCpf("000.000.000-00");
        pessoaInstanciada.setEmail("asddsf@mail.com.br");
        pessoaInstanciada.setDataNascimento(LocalDate.now());
        pessoaInstanciada.setNome("teste teste");
        pessoaInstanciada.setFoto("ooooooooooo");

        for (int i = 0; i < 3; i++) {
            Pessoa pessoaTemp = new Pessoa();
            pessoaTemp.setId(random.nextLong());
            pessoaTemp.setCpf("000.000.000-00");
            pessoaTemp.setEmail("asddsf@mail.com.br");
            pessoaTemp.setDataNascimento(LocalDate.now());
            pessoaTemp.setNome("teste teste");
            pessoaTemp.setFoto("ooooooooooo");
            pessoas.add(pessoaTemp);
        }

        random = new Random();
    }

    @Test
    public void salvarPessoa() throws Exception {
        Mockito.when(pessoaRepositoryMock.save(Mockito.any(Pessoa.class))).thenReturn(pessoaSalva);
        Mockito.when(pessoaRepositoryMock.existsByCpf(Mockito.anyString())).thenReturn(Boolean.FALSE);

        Pessoa pessoaPersistida = pessoaService.salvarPessoa(pessoaInstanciada, null);

        Assert.assertNotNull(pessoaPersistida);
        Assert.assertNotNull(pessoaPersistida.getId());
    }

    @Test(expected = Exception.class)
    public void salvarPessoaCFPExistente() throws Exception {
        Mockito.when(pessoaRepositoryMock.save(Mockito.any(Pessoa.class))).thenReturn(pessoaSalva);
        Mockito.when(pessoaRepositoryMock.existsByCpf(Mockito.anyString())).thenReturn(Boolean.TRUE);

        pessoaService.salvarPessoa(pessoaInstanciada, null);
    }

    @Test(expected = Exception.class)
    public void salvarPessoaCFPNull() throws Exception {
        pessoaInstanciada.setCpf(null);

        Mockito.when(pessoaRepositoryMock.save(Mockito.any(Pessoa.class))).thenReturn(pessoaSalva);
        Mockito.when(pessoaRepositoryMock.existsByCpf(Mockito.anyString())).thenReturn(Boolean.TRUE);

        pessoaService.salvarPessoa(pessoaInstanciada, null);
    }

    @Test
    public void salvarPessoaUpdate() throws Exception {
        pessoaSalva.setNome("TESTETTTEEEEE");

        Mockito.when(pessoaRepositoryMock.save(Mockito.any(Pessoa.class))).thenReturn(pessoaSalva);
        Mockito.when(pessoaRepositoryMock.existsByCpf(Mockito.anyString())).thenReturn(Boolean.FALSE);
        Mockito.when(pessoaRepositoryMock.existsById(Mockito.anyLong())).thenReturn(Boolean.TRUE);

        Pessoa pessoaPersistida = pessoaService.salvarPessoa(pessoaInstanciada, pessoaSalva.getId());
        Assert.assertNotNull(pessoaPersistida);
        Assert.assertEquals(pessoaSalva.getNome(), pessoaPersistida.getNome());
    }

    @Test
    public void deletarPessoa() {

        Optional<Pessoa> pessoaOptional = Optional.of(pessoaSalva);

        Mockito.when(pessoaRepositoryMock.findById(Mockito.anyLong())).thenReturn(pessoaOptional);

        pessoaService.deletarPessoa(random.nextLong());
    }

    @Test(expected = Exception.class)
    public void deletarPessoaInexistente() {

        Mockito.when(pessoaRepositoryMock.findById(Mockito.anyLong())).thenReturn(null);

        pessoaService.deletarPessoa(random.nextLong());
    }

    @Test(expected = Exception.class)
    public void deletarPessoaIdNull() {

        Mockito.when(pessoaRepositoryMock.findById(Mockito.anyLong())).thenReturn(null);

        pessoaService.deletarPessoa(null);
    }

    @Test
    public void listarPessoas() {
        Page<Pessoa> pessoasPage = new PageImpl<>(pessoas);

        Mockito.when(pessoaRepositoryMock.findByNomeAndCpfAndDataNascimentoAndEmail(Mockito.any(Pageable.class), Mockito.anyString(), Mockito.anyString(), Mockito.any(LocalDate.class), Mockito.anyString())).thenReturn(pessoasPage);

        Page<Pessoa> pessoas = pessoaService.listarPessoas(1, random.nextInt(), "Teste", "teste", LocalDate.now(), "teste");

        Assert.assertNotNull(pessoas);
        Assert.assertTrue(pessoas.getTotalElements() > 0);
    }

}
