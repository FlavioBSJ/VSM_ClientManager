package com.fbsj.vsmclientmanager.services;

import com.fbsj.vsmclientmanager.model.Endereco;
import com.fbsj.vsmclientmanager.repositories.CidadeRepository;
import com.fbsj.vsmclientmanager.repositories.EnderecoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final CidadeService cidadeService;
    private final CidadeRepository cidadeRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, CidadeService cidadeService, CidadeRepository cidadeRepository) {
        this.enderecoRepository = enderecoRepository;
        this.cidadeService = cidadeService;
        this.cidadeRepository = cidadeRepository;
    }

    public List<Endereco> listarTodos(){
        return enderecoRepository.findAll();
    }

    public List<Endereco> getByExample(Endereco enderecoFiltro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Endereco> exemplo = Example.of(enderecoFiltro, matcher);
        List<Endereco> enderecos = enderecoRepository.findAll(exemplo);

        return enderecos;
    }

    public Endereco persistirEndereco(Endereco endereco) {
        if(endereco.getCidade() != null){
            if(cidadeService.getByExample(endereco.getCidade()).isEmpty()){
                cidadeRepository.save(endereco.getCidade());
            }
        }
        return enderecoRepository.save(endereco);
    }

    public Endereco modificarEndereco(Endereco endereco){

        enderecoRepository.findById(endereco.getId()).map(enderecoCandidato -> {
            enderecoCandidato = Endereco.builder().Id(enderecoCandidato.getId())
                    .numero(enderecoCandidato.getNumero()).rua(enderecoCandidato.getRua())
                    .bairro(enderecoCandidato.getBairro())
                    .CEP(enderecoCandidato.getCEP())
                    .cidade(enderecoCandidato.getCidade())
                    .build();
            return enderecoRepository.save(enderecoCandidato);
        }).orElseGet(() -> {
            return persistirEndereco(endereco);
        });

        return enderecoRepository.getReferenceById(endereco.getId());
    }
}
