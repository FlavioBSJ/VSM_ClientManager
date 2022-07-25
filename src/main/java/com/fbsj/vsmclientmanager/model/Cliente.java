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

    @NotBlank(message = "O nome e obrigatorio.")
    private String nome;

    @CPF(message = "O cpf informado e invalido.")
    private String cpf;

    @Email(message = "O Email informado e invalido.")
    private String email;

    @Pattern(message = "O numero de telefone precisa estar no formato (xx)xxxxx-xxxx.", regexp = "^\\([1-9]{2}\\)(9[1-9])[0-9]{3}\\-[0-9]{4}$")
    private String telefone;

    @OneToOne
    @JoinColumn(name = "cliente_endereco")
    private Endereco endereco;

    private Boolean ativo;
}
