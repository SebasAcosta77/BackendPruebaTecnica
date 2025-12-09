package com.jdc.clinica.repository;

import com.jdc.clinica.models.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<UsuariosEntity, Long> {
    Optional<UsuariosEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
