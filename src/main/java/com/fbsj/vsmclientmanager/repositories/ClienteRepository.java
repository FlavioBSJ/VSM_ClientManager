package com.fbsj.vsmclientmanager.repositories;

import com.fbsj.vsmclientmanager.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    public Optional<Cliente> findByNome(String nome);
    public Optional<Cliente> findByCpf(String cpf);

}
