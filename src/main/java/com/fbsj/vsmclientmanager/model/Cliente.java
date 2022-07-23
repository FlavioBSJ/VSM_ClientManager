package com.fbsj.vsmclientmanager.model;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cliente")
public class Cliente{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder
    public Cliente(Long id, String nome, String cpf, String email, String telefone, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.ativo = ativo;
    }

    @NotBlank
    private String nome;

    @CPF
    private String cpf;

    @Email
    private String email;

    private String telefone;

    @OneToOne
    @JoinColumn(name = "cliente_endereco")
    private Endereco endereco;

    private Boolean ativo;
}
