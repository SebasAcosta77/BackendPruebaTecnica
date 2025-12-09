package com.jdc.clinica.repository;

import com.jdc.clinica.models.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<ProductoEntity, Long> {
    long countByEmpresa_Nit(String nit);
    boolean existsByCodigo(String codigo);
}
