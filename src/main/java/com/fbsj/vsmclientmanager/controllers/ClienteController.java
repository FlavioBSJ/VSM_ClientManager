package com.fbsj.vsmclientmanager.controllers;

import com.fbsj.vsmclientmanager.model.Cliente;
import com.fbsj.vsmclientmanager.services.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getCliente(@RequestParam(required = false) Long idCliente,
                                                    @RequestParam(required = false) String email,
                                                    @RequestParam(required = false) String cpf) {
        if(idCliente == null && email == null && cpf == null){
            List<Cliente> clientes = clienteService.listarTodos();
            return ResponseEntity.ok(clientes);
        }


        Cliente cliente = Cliente.builder().id(idCliente).email(email).cpf(cpf).build();

        List<Cliente> clientes = clienteService.getByExample(cliente);

        if(idCliente != null){
            if(clientes.isEmpty()){
                return ResponseEntity.ok(Collections.emptyList());
            }
            Cliente retorno = clientes.get(0);
            if(retorno.getId() == null) {
                return ResponseEntity.ok(Collections.emptyList());
            }
            return ResponseEntity.ok(Arrays.asList(retorno));
        }
        return ResponseEntity.ok(clientes);

    }

    @PostMapping("/{id}")
    public ResponseEntity<Cliente> toggleCliente(@PathVariable Long id, @RequestParam Boolean ativo){
        return ResponseEntity.ok(clienteService.clienteToggler(id, ativo));
    }

    @GetMapping("/integrationApi/{cpf}")
    public ResponseEntity<Cliente> getPorCPF(@PathVariable String cpf){
        return ResponseEntity.ok(clienteService.recuperarInfoCliente(cpf));
    }

    @PostMapping
    public ResponseEntity<Cliente> postCliente(@RequestBody @Valid Cliente cliente){
        return ResponseEntity.ok(clienteService.persistirCliente(cliente));
    }

    @PutMapping
    public ResponseEntity<Cliente> putCliente(@RequestBody @Valid Cliente cliente){
        return ResponseEntity.ok(clienteService.modificarCliente(cliente));
    }
}
