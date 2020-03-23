package com.gerenciador.pessoas.rest;

import com.gerenciador.pessoas.dtos.PessoaRequest;
import com.gerenciador.pessoas.entitys.Pessoa;
import com.gerenciador.pessoas.rest.fabrica.PessoaFabrica;
import org.hamcrest.Matchers;
import static io.restassured.RestAssured.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PessoaResourceTest extends ConfigureTestRestAssured {

    @Autowired
    private PessoaFabrica pessoaFabrica;

    @Test
    public void listarPessoas() {
        getRequestSpecification().given().params("page","0", "count", "10").get("/pessoas").then().statusCode(200);
    }

    @Test
    public void listarPessoasParametroNomePaginado() {
        Pessoa pessoa = pessoaFabrica.salvarPessoaRandomBD("648.471.743-56");
        getRequestSpecification().given()
                .params("page","0", "count", "10", "nome", pessoa.getNome())
                .get("/pessoas")
                .then()
                .statusCode(200)
                .and()
                .body("data.totalElements", Matchers.greaterThan(0));
    }

    @Test
    public void listarPessoasErrorRequiredParams() {
        getRequestSpecification().given().get("/pessoas").then().statusCode(400);
    }

    @Test
    public void salvarPessoa() {
        PessoaRequest pessoaRequest = PessoaFabrica.criarPessoaRequestDefault("646.507.483-48");

        getRequestSpecification().given().body(pessoaRequest).post("/pessoas").then().statusCode(200);
    }

    @Test
    public void salvarPessoaIdRetornado() {
        PessoaRequest pessoaRequest = PessoaFabrica.criarPessoaRequestDefault("457.624.462-04");

        getRequestSpecification().given().body(pessoaRequest).post("/pessoas").then().statusCode(200).body("data.id", Matchers.notNullValue());
    }

    @Test
    public void salvarPessoaCPFDuplicado() {
        PessoaRequest pessoaRequest = PessoaFabrica.criarPessoaRequestDefault(null);
        pessoaFabrica.salvarPessoaRandomBD(pessoaRequest.getCpf());

        getRequestSpecification().given().body(pessoaRequest).post("/pessoas").then().statusCode(400);
    }

    @Test
    public void salvarPessoaCpfNull() {
        PessoaRequest pessoaRequest = PessoaFabrica.criarPessoaRequestDefault(null);
        pessoaRequest.setCpf(null);

        getRequestSpecification().given().body(pessoaRequest).post("/pessoas").then().statusCode(400);
    }

    @Test
    public void salvarPessoaEmailInvalido() {
        PessoaRequest pessoaRequest = PessoaFabrica.criarPessoaRequestDefault(null);
        pessoaRequest.setEmail("TTTTTTTTTTTTTTTTTT");

        getRequestSpecification().given().body(pessoaRequest).post("/pessoas").then().statusCode(400);
    }

    @Test
    public void salvarPessoaCPFInvalido() {
        PessoaRequest pessoaRequest = PessoaFabrica.criarPessoaRequestDefault(null);
        pessoaRequest.setCpf("000.000.000-00");

        getRequestSpecification().given().body(pessoaRequest).post("/pessoas").then().statusCode(400);
    }

    @Test
    public void deletarPessoa() {
        Pessoa pessoa = pessoaFabrica.salvarPessoaRandomBD("800.402.032-12");

        getRequestSpecification().given().delete("/pessoas/{id}", pessoa.getId()).then().statusCode(204);
    }

    @Test
    public void deletarPessoaInexistente() {

        getRequestSpecification().given().delete("/pessoas/{id}", 999L).then().statusCode(404);
    }

    @Test
    public void alterarPessoa() {
        PessoaRequest pessoaRequest = PessoaFabrica.criarPessoaRequestDefault("306.753.868-45");
        Pessoa pessoa = pessoaFabrica.salvarPessoaRandomBD(pessoaRequest.getCpf());
        pessoaRequest.setNome("NOME MUDADO");

        getRequestSpecification().given().body(pessoaRequest).put("/pessoas/{id}", pessoa.getId()).then().statusCode(200).and().body("data.nome", Matchers.equalTo(pessoaRequest.getNome()));
    }

}
