package com.jdc.clinica.repository;

import com.jdc.clinica.models.OrdenProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrdenProductoRepository extends JpaRepository<OrdenProductoEntity, Long> {
}
