package com.jdc.clinica.services;

import com.jdc.clinica.DTO.CategoriaDTO;
import com.jdc.clinica.DTO.ProductoCategoriaDTO;
import com.jdc.clinica.DTO.ProductoDTO;
import com.jdc.clinica.models.CategoriaEntity;
import com.jdc.clinica.models.ProductoCategoriaEntity;
import com.jdc.clinica.models.ProductoEntity;

import java.util.List;

public interface IProductoService {
    public List<ProductoEntity> findAll();
    List<ProductoDTO> listar();
    public ProductoEntity findById(Long id);
    public ProductoDTO save(ProductoDTO productoDTO);
    public ProductoEntity actualizarPorId(Long id, ProductoDTO productoDTO);
    void delete(Long id);

}
