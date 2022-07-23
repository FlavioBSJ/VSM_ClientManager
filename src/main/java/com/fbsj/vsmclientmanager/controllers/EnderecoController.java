package com.fbsj.vsmclientmanager.controllers;

import com.fbsj.vsmclientmanager.model.Cidade;
import com.fbsj.vsmclientmanager.model.Cliente;
import com.fbsj.vsmclientmanager.model.Endereco;
import com.fbsj.vsmclientmanager.services.EnderecoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public ResponseEntity<List<Endereco>> getEndereco(@RequestParam(required = false) Long idEndereco,
                                                    @RequestParam(required = false) String rua,
                                                    @RequestParam(required = false) String bairro,
                                                    @RequestParam(required = false) Cidade cidade) {
        if(idEndereco == null && rua == null && bairro == null && cidade == null){
            List<Endereco> enderecos = enderecoService.listarTodos();
            return ResponseEntity.ok(enderecos);
        }


        Endereco endereco = Endereco.builder().Id(idEndereco).rua(rua).bairro(bairro).cidade(cidade).build();

        List<Endereco> enderecos = enderecoService.getByExample(endereco);

        if(idEndereco != null){
            if(enderecos.isEmpty()){
                return ResponseEntity.ok(Collections.emptyList());
            }
            Endereco retorno = enderecos.get(0);
            if(retorno.getId() == null) {
                return ResponseEntity.ok(Collections.emptyList());
            }
            return ResponseEntity.ok(Arrays.asList(retorno));
        }
        return ResponseEntity.ok(enderecos);

    }

    @PutMapping
    public ResponseEntity<Endereco> putEndereco(@RequestBody @Valid Endereco endereco){
        return ResponseEntity.ok(enderecoService.modificarEndereco(endereco));
    }
}
