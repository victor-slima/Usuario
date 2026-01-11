package com.victor.usuario.infraestructure.repository;

import com.victor.usuario.infraestructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Metodo que verifica se existe um usuario com o email informado.
    // Nao precisa ser public
    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);


    @Transactional
    void deleteByEmail(String email);
}
