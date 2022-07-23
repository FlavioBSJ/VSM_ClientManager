package com.fbsj.vsmclientmanager.services;

import com.fbsj.vsmclientmanager.errors.ClientNotFoundException;
import com.fbsj.vsmclientmanager.errors.DuplicateException;
import com.fbsj.vsmclientmanager.model.Cliente;
import com.fbsj.vsmclientmanager.repositories.ClienteRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final EnderecoService enderecoService;

    public ClienteService(ClienteRepository clienteRepository, EnderecoService enderecoService) {
        this.clienteRepository = clienteRepository;
        this.enderecoService = enderecoService;
    }

    public List<Cliente> listarTodos(){
        return clienteRepository.findAll();
    }

    public List<Cliente> getByExample(Cliente clienteFiltro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Cliente> exemplo = Example.of(clienteFiltro, matcher);
        List<Cliente> clientes = clienteRepository.findAll(exemplo);

        return clientes;
    }


    public Cliente persistirCliente(Cliente cliente) {
        Optional<Cliente> optionalCliente = clienteRepository.findByCpf(cliente.getCpf());
        if(optionalCliente.isPresent()){
            throw new DuplicateException();
        }
        if(cliente.getAtivo() == null){
            cliente.setAtivo(true);
        }
        if(cliente.getEndereco() != null){
            enderecoService.persistirEndereco(cliente.getEndereco());
        }
        return clienteRepository.save(cliente);
    }

    public Cliente modificarCliente(Cliente cliente){

        if(cliente.getAtivo() == null){
            cliente.setAtivo(true);
        }
        clienteRepository.findById(cliente.getId()).map(clienteCandidato -> {

            clienteCandidato.setId(cliente.getId());
            clienteCandidato.setAtivo(cliente.getAtivo());
            clienteCandidato.setCpf(cliente.getCpf());
            clienteCandidato.setEmail(cliente.getEmail());
            clienteCandidato.setNome(cliente.getNome());
            clienteCandidato.setTelefone(cliente.getTelefone());
            return clienteRepository.save(clienteCandidato);
        }).orElseGet(() -> {
            return persistirCliente(cliente);
        });

        return clienteRepository.getReferenceById(cliente.getId());
    }

    public Cliente clienteToggler(Long id, Boolean estado) {
        if(estado == null){
            estado = true;
        }

        Cliente clienteEditado = clienteRepository.getReferenceById(id);

        if(clienteEditado == null){
            throw new ClientNotFoundException();
        }
        clienteEditado.setAtivo(estado);
        return clienteRepository.save(clienteEditado);
    }

    public Cliente recuperarInfoCliente(String cpf) {
        Optional<Cliente> cliente = clienteRepository.findByCpf(cpf);
        if(!cliente.isPresent()){
            throw new ClientNotFoundException();
        }
        Cliente clienteRetorno = cliente.get();
        return clienteRetorno;
    }
}
