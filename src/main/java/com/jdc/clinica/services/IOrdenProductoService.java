package com.jdc.clinica.services;

import com.jdc.clinica.DTO.CategoriaDTO;
import com.jdc.clinica.DTO.OrdenProductoDTO;
import com.jdc.clinica.models.CategoriaEntity;
import com.jdc.clinica.models.OrdenProductoEntity;

import java.util.List;

public interface IOrdenProductoService {
    public List<OrdenProductoEntity> findAll();
    public OrdenProductoEntity findById(Long id);
    public OrdenProductoEntity save(OrdenProductoDTO ordenProductoDTO);
    void delete(Long id);
}
