package com.jdc.clinica.repository;

import com.jdc.clinica.models.EmpresaEntity;
import com.jdc.clinica.models.InventarioEntity;
import com.jdc.clinica.models.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IInventarioRepository extends JpaRepository<InventarioEntity, Long> {
    List<InventarioEntity> findByEmpresa(EmpresaEntity empresa);

    Optional<InventarioEntity> findByEmpresaAndProducto(EmpresaEntity empresa, ProductoEntity producto);

    long countByEmpresa_Nit(String empresaNit);
}
