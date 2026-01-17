package com.victor.usuario.business;

import com.victor.usuario.business.converter.UsuarioConverter;
import com.victor.usuario.business.dto.EnderecoDTO;
import com.victor.usuario.business.dto.TelefoneDTO;
import com.victor.usuario.business.dto.UsuarioDTO;
import com.victor.usuario.infraestructure.entity.Endereco;
import com.victor.usuario.infraestructure.entity.Telefone;
import com.victor.usuario.infraestructure.entity.Usuario;
import com.victor.usuario.infraestructure.exceptions.ConflictException;
import com.victor.usuario.infraestructure.exceptions.ResourceNotFoundException;
import com.victor.usuario.infraestructure.repository.EnderecoRepository;
import com.victor.usuario.infraestructure.repository.TelefoneRepository;
import com.victor.usuario.infraestructure.repository.UsuarioRepository;
import com.victor.usuario.infraestructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
// anotacao para injecao de dependencias
@RequiredArgsConstructor
public class UsuarioService {
    // aplicando as injecoes de dependencias.
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    // finalizada as injecoes de dependencias.

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public void emailExiste(String email){
        try{
            boolean existe = verificarEmailExistente(email);
            if (existe){
                throw new ConflictException("Email ja cadastrado" + email);
            }
        }catch (ConflictException e){
            throw new ConflictException("Email ja cadastrado." + e.getCause());
        }
    }

    public boolean verificarEmailExistente(String email){return usuarioRepository.existsByEmail(email);}

    public UsuarioDTO buscaUsuarioPorEmail(String email){
        try{

            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email).orElseThrow(() ->
                            new ResourceNotFoundException("Usuario nao encontrado: " + email)));

        }catch(ResourceNotFoundException e){

            throw new ResourceNotFoundException("Usuario nao encontrado: " + email);
        }
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    // Extrai o e-mail do usuario atraves do token
    public UsuarioDTO atualizarDadosUsuario(String token, UsuarioDTO usuarioDTO){
        // Aqui buscamos o email do usuario atraves do token (tirar a obrigatoriedade do email)
        String email = jwtUtil.extractEmailToken(token.substring(7));
        // Criptografia de senha.
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);
        // Aqui buscamos os dados do usuario no banco de dados.
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email nao existe no banco de dados: " + email));
        // Mesclou os dados que recebemos na requisicao DTO com os dados do banco de dados.
        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity);
        // Salvamos os dados do usuario convertido e depois pegamos o retorno e convertemos novamente para DTO.
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    // Metodo para atualizar a parte de endereco do usuario.
    public EnderecoDTO atualizarEndereco(Long idEndereco, EnderecoDTO enderecoDTO){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("Endereco nao encontrado: " + idEndereco));
        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO,entity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    // Metodo para atualizar a parte de telefone do usuario.
    public TelefoneDTO atualizarTelefone(Long idTelefone, TelefoneDTO telefoneDTO){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("Telefone nao encontrado: " + idTelefone));
        Telefone telefone = usuarioConverter.updateTelefone(telefoneDTO, entity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO enderecoDTO){

        String email = jwtUtil.extractEmailToken(token.substring(7));

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email nao encontrado: " + email));

        Endereco endereco = usuarioConverter.paraEndereco(enderecoDTO, usuario.getId());
        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        return usuarioConverter.paraEnderecoDTO(enderecoSalvo);

    }

    public TelefoneDTO cadastrarTelefone(String token, TelefoneDTO telefoneDTO){
        String email = jwtUtil.extractEmailToken(token.substring(7));

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email nao encontrado: " + email));

        Telefone telefone = usuarioConverter.paraTelefone(telefoneDTO, usuario.getId());
        Telefone telefoneSalvo = telefoneRepository.save(telefone);

        return usuarioConverter.paraTelefoneDTO(telefoneSalvo);
    }
}
