package com.jdc.clinica.repository;

import com.jdc.clinica.models.OrdenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrdenRepository extends JpaRepository<OrdenEntity, Long> {
}
