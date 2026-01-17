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

    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefoneDTOList){
        return telefoneDTOList.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .telefone(telefone.getTelefone())
                .ddd(telefone.getDdd())
                .build();
    }

    //--------------------------------------------------------------------------------//

    // Fazendo selecao de dados na parte de usuario.
    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity){
        return Usuario.builder()
                .id(entity.getId())
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .cpf(usuarioDTO.getCpf() != null ? usuarioDTO.getCpf() : entity.getCpf())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .endereco(entity.getEndereco())
                .telefone(entity.getTelefone())
                .build();
    }

    // Fazendo selecao de dados na parte de endereco.
    public Endereco updateEndereco(EnderecoDTO enderecoDTO, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(enderecoDTO.getRua() != null ? enderecoDTO.getRua() : entity.getRua())
                .numero(enderecoDTO.getNumero() != null ? enderecoDTO.getNumero() : entity.getNumero())
                .complemento(enderecoDTO.getComplemento() != null ? enderecoDTO.getComplemento() : entity.getComplemento())
                .cidade(enderecoDTO.getCidade() != null ? enderecoDTO.getCidade() : entity.getCidade())
                .estado(enderecoDTO.getEstado() != null ? enderecoDTO.getEstado() : entity.getEstado())
                .cep(enderecoDTO.getCep() != null ? enderecoDTO.getCep() : entity.getCep())
                .build();
    }

    // Fazendo a selecao de dados na parte telefone.
    public Telefone updateTelefone(TelefoneDTO telefoneDTO, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .telefone(telefoneDTO.getTelefone() != null ? telefoneDTO.getTelefone() : entity.getTelefone())
                .ddd(telefoneDTO.getDdd() != null ? telefoneDTO.getDdd() : entity.getDdd())
                .build();
    }

    //--------------------------------------------------------------------------------//

    // ENDERECO DTO PARA ENDERECO ENTITY

    public Endereco paraEndereco(EnderecoDTO enderecoDTO, Long idUsuario){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .usuarioId(idUsuario)
                .build();
    }

    // TELEFONE DTO PARA TELEFONE ENTITY:

    public Telefone paraTelefone(TelefoneDTO telefoneDTO, Long idUsuario){
        return Telefone.builder()
                .telefone(telefoneDTO.getTelefone())
                .ddd(telefoneDTO.getDdd())
                .usuarioId(idUsuario)
                .build();
    }
}
