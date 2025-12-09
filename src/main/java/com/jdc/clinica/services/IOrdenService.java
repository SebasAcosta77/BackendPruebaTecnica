package com.jdc.clinica.services;

import com.jdc.clinica.DTO.CategoriaDTO;
import com.jdc.clinica.DTO.OrdenDTO;
import com.jdc.clinica.models.CategoriaEntity;
import com.jdc.clinica.models.OrdenEntity;

import java.util.List;

public interface IOrdenService {
    public List<OrdenEntity> findAll();
    public OrdenEntity findById(Long id);
    public OrdenEntity save(OrdenDTO ordenDTO);
    public OrdenEntity actualizarPorId(Long id, OrdenDTO ordenDTO);
    void delete(Long id);
}
