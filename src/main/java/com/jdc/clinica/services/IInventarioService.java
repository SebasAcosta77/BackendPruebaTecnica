package com.jdc.clinica.services;

import com.jdc.clinica.DTO.EmpresaDTO;
import com.jdc.clinica.DTO.InventarioDTO;
import com.jdc.clinica.DTO.ProductoDTO;
import com.jdc.clinica.models.EmpresaEntity;
import com.jdc.clinica.models.InventarioEntity;

import java.util.List;

public interface IInventarioService {
    List<InventarioEntity> findAll();

    InventarioEntity findById(Long id);

    InventarioEntity save(InventarioDTO inventarioDTO);

    InventarioEntity actualizarPorId(Long id, InventarioDTO inventarioDTO);

    void delete(Long id);

    List<InventarioEntity> findByEmpresa(String empresaNit);

    Long countByEmpresa_Nit(String nit);

}
