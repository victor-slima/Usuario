package com.victor.usuario.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    @JsonProperty("endereco")
    private List<EnderecoDTO> enderecos;
    @JsonProperty("telefone")
    private List<TelefoneDTO> telefones;
}
