package com.fbsj.vsmclientmanager.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "endereco")
public class Endereco{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    private String rua;
    private String numero;
    private String bairro;
    private String CEP;

    @OneToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;


}
