package com.jdc.clinica.services;

import com.jdc.clinica.DTO.CategoriaDTO;
import com.jdc.clinica.DTO.ProductoDTO;
import com.jdc.clinica.models.CategoriaEntity;

import java.util.List;

public interface ICategoriaService {
    public List<CategoriaEntity> findAll();
    List<CategoriaDTO> listar();
    public CategoriaEntity findById(Long id);
    public CategoriaEntity save(CategoriaDTO categoriaDTO);
    public CategoriaEntity actualizarPorId(Long id, CategoriaDTO categoriaDTO);
    public void delete(Long id);
}
