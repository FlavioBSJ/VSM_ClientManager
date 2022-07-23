package com.fbsj.vsmclientmanager.controllers;

import com.fbsj.vsmclientmanager.model.Cidade;
import com.fbsj.vsmclientmanager.model.Cliente;
import com.fbsj.vsmclientmanager.services.CidadeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/cidade")
public class CidadeController {


    private final CidadeService cidadeService;

    public CidadeController(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    @GetMapping
    public ResponseEntity<List<Cidade>> getCidade(@RequestParam(required = false) Long idCidade,
                          @RequestParam(required = false) String uf ,
                          @RequestParam(required = false) String nomeCidade) {

        if(idCidade == null && uf == null && nomeCidade == null){
            List<Cidade> cidades = cidadeService.listarTodos();
            return ResponseEntity.ok(cidades);
        }


       Cidade cidade = Cidade.builder().Id(idCidade).nomeCidade(nomeCidade).uf(uf).build();

        List<Cidade> cidades = cidadeService.getByExample(cidade);

        if(idCidade != null){
            if(cidades.isEmpty()){
                return ResponseEntity.ok(Collections.emptyList());
            }
            Cidade retorno = cidades.get(0);
            if(retorno.getId() == null) {
                return ResponseEntity.ok(Collections.emptyList());
            }
            return ResponseEntity.ok(Arrays.asList(retorno));
        }
        return ResponseEntity.ok(cidades);

    }

    @PostMapping
    public ResponseEntity<Cidade> postCidade(@RequestBody @Valid Cidade cidade){
        return ResponseEntity.ok(cidadeService.persistirCidade(cidade));
    }

    @PutMapping
    public ResponseEntity<Cidade> putCidade(@RequestBody @Valid Cidade cidade){
        return ResponseEntity.ok(cidadeService.modificarCidade(cidade));
    }

}
