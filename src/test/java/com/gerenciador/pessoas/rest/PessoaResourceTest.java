package com.gerenciador.pessoas.rest;

import com.gerenciador.pessoas.entitys.Pessoa;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class PessoaResourceTest extends ConfigureTestRestAssured {

    @Test
    public void listarPessoas() {
        getRequestSpecification().given().params("page","0", "count", "10").get("/pessoas").then().statusCode(200);
    }

    @Test
    public void listarPessoasErrorRequiredParams() {
        getRequestSpecification().given().get("/pessoas").then().statusCode(400);
    }

}
