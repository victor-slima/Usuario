package com.victor.usuario.business.converter;

import com.victor.usuario.business.dto.EnderecoDTO;
import com.victor.usuario.business.dto.TelefoneDTO;
import com.victor.usuario.business.dto.UsuarioDTO;
import com.victor.usuario.infraestructure.entity.Endereco;
import com.victor.usuario.infraestructure.entity.Telefone;
import com.victor.usuario.infraestructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    // CONVERTENDO DE USUARIO PARA DTO:

    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .cpf(usuarioDTO.getCpf())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .endereco(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefone(paraListaTelefone(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecos){
        return enderecos.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOList){
        return telefoneDTOList.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .telefone(telefoneDTO.getTelefone())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    //--------------------------------------------------------------------------------//

    // CONVERTENDO DE DTO PARA USUARIO:

    public UsuarioDTO paraUsuarioDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .cpf(usuario.getCpf())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEndereco()))
                .telefones(paraListaTelefoneDTO(usuario.getTelefone()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecoDTOList){
        return enderecoDTOList.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco enderecoDTO){
        return EnderecoDTO.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefoneDTOList){
        return telefoneDTOList.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO){
        return TelefoneDTO.builder()
                .telefone(telefoneDTO.getTelefone())
                .ddd(telefoneDTO.getDdd())
                .build();
    }
}
