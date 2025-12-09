package com.jdc.clinica.services;

import com.jdc.clinica.DTO.CategoriaDTO;
import com.jdc.clinica.DTO.ProductoCategoriaDTO;
import com.jdc.clinica.models.CategoriaEntity;
import com.jdc.clinica.models.ProductoCategoriaEntity;

import java.util.List;

public interface IProductoCategoriaService {
    public List<ProductoCategoriaEntity> findAll();
    public ProductoCategoriaEntity findById(Long id);
    public ProductoCategoriaEntity save(ProductoCategoriaDTO productoCategoriaDTO);
    void delete(Long id);
}
