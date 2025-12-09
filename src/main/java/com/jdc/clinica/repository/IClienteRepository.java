package com.jdc.clinica.repository;

import com.jdc.clinica.models.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<ClienteEntity, Long> {
}
