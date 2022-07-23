package com.fbsj.vsmclientmanager.services;

import com.fbsj.vsmclientmanager.errors.DuplicateException;
import com.fbsj.vsmclientmanager.model.Cidade;
import com.fbsj.vsmclientmanager.repositories.CidadeRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    public List<Cidade> listarTodos(){
        return cidadeRepository.findAll();
    }

    public List<Cidade> getByExample(Cidade cidadeFiltro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Cidade> exemplo = Example.of(cidadeFiltro, matcher);
        return cidadeRepository.findAll(exemplo);
    }

    public Cidade persistirCidade(Cidade cidade){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Cidade> exemplo = Example.of(cidade, matcher);
        if(!cidadeRepository.findAll(exemplo).isEmpty()){
            throw new DuplicateException();
        }

        return cidadeRepository.save(cidade);
    }

    public Cidade modificarCidade(Cidade cidade){
        cidadeRepository.findById(cidade.getId()).map(cidadeCandidata -> {
            cidadeCandidata = Cidade.builder().Id(cidadeCandidata.getId())
                    .nomeCidade(cidadeCandidata.getNomeCidade())
                    .uf(cidadeCandidata.getUf())
                    .build();
            return cidadeRepository.save(cidade);
        }).orElseGet(() -> {
            return persistirCidade(cidade);
        });

        return cidadeRepository.getReferenceById(cidade.getId());
    }
}
