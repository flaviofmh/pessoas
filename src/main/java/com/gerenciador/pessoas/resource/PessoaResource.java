package com.gerenciador.pessoas.resource;

import com.gerenciador.pessoas.dtos.PessoaRequest;
import com.gerenciador.pessoas.dtos.PessoaResponse;
import com.gerenciador.pessoas.dtos.Response;
import com.gerenciador.pessoas.entitys.Pessoa;
import com.gerenciador.pessoas.services.PessoaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;

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

    @GetMapping(value = "{id}")
    public ResponseEntity<Response<PessoaResponse>> findById(@PathVariable("id") Long id) {
        Response<PessoaResponse> response = new Response<PessoaResponse>();
        Pessoa pessoa = pessoaService.findById(id);
        if(pessoa == null) {
            response.getErrors().add("Register not found id: " + id);
            return ResponseEntity.badRequest().body(response);
        }
        PessoaResponse pessoaResponse = modelMapper.map(pessoa, PessoaResponse.class);
        response.setData(pessoaResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<PessoaResponse>> salvar(@RequestBody @Valid PessoaRequest pessoaRequest) {
        return ResponseEntity.ok(salvarPessoa(pessoaRequest, null));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {
        Response<String> response = new Response<String>();
        pessoaService.deletarPessoa(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Response<PessoaResponse>> atualizar(@RequestBody @Valid PessoaRequest pessoaRequest, @PathVariable("id") Long id) {
        return ResponseEntity.ok(salvarPessoa(pessoaRequest, id));
    }

    public Response<PessoaResponse> salvarPessoa(PessoaRequest pessoaRequest, Long id) {
        Response<PessoaResponse> response = new Response<PessoaResponse>();

        Pessoa pessoa = modelMapper.map(pessoaRequest, Pessoa.class);
        try {
            pessoaService.salvarPessoa(pessoa, id);
        } catch (Exception ex) {
            response.getErrors().add(ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa invalida", ex);
        }

        PessoaResponse pessoaResponse = modelMapper.map(pessoa, PessoaResponse.class);
        response.setData(pessoaResponse);

        return response;
    }

}
