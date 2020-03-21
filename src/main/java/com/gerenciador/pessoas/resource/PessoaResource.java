package com.gerenciador.pessoas.resource;

import com.gerenciador.pessoas.dtos.PessoaResponse;
import com.gerenciador.pessoas.dtos.Response;
import com.gerenciador.pessoas.entitys.Pessoa;
import com.gerenciador.pessoas.services.PessoaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<Response<Page<Pessoa>>> listar(@RequestParam("page") int page, @RequestParam("count") int count,
                                                         @RequestParam(required = false) String nome,
                                                         @RequestParam(required = false) String cpf,
                                                         @RequestParam(required = false) LocalDate dataNascimento,
                                                         @RequestParam(required = false) String email) {

        Response<Page<Pessoa>> response = new Response<Page<Pessoa>>();

        Page<Pessoa> pessoas = pessoaService.listarPessoas(page, count, nome, cpf, dataNascimento, email);
//        Type listType = new TypeToken<List<PessoaResponse>>(){}.getType();
//        modelMapper.map(pessoas, listType);

        response.setData(pessoas);

        return ResponseEntity.ok(response);
    }

}
