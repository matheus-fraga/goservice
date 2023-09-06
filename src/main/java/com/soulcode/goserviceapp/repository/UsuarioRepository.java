package com.soulcode.goserviceapp.repository;

import com.soulcode.goserviceapp.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Page<Usuario> findAll(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE usuarios u SET u.senha = ? WHERE u.email = ?", nativeQuery = true)
    void updatePasswordByEmail(String senha, String email);

    @Modifying
    @Query(value = "UPDATE usuarios u SET u.habilitado = ? WHERE u.id = ?", nativeQuery = true)
    void updateEnableById(boolean habilitado, Long id);

    @Query(value = "SELECT * FROM usuarios WHERE nome LIKE %?%", nativeQuery = true)
    Page<Usuario> nameFilter(String name, Pageable pageable);

}
