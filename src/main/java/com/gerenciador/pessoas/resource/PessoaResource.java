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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin(origins = "*")
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

            if(pessoa.getFoto() == null) {
                String base64Image = "";
                File file = new File("F:/home/projetosSpring/tiever/src/main/images/person.png");
                try (FileInputStream imageInFile = new FileInputStream(file)) {
                    // Reading a Image file from file system
                    byte imageData[] = new byte[(int) file.length()];
                    imageInFile.read(imageData);
                    base64Image = Base64.getEncoder().encodeToString(imageData);
                } catch (FileNotFoundException e) {
                    System.out.println("Image not found" + e);
                } catch (IOException ioe) {
                    System.out.println("Exception while reading the Image " + ioe);
                }
                pessoa.setFoto("data:image/png;base64,"+base64Image);
            }

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
