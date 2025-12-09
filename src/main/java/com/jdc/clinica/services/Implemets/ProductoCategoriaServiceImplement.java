package com.jdc.clinica.services.Implemets;

import com.jdc.clinica.DTO.ProductoCategoriaDTO;
import com.jdc.clinica.models.CategoriaEntity;
import com.jdc.clinica.models.ProductoCategoriaEntity;
import com.jdc.clinica.models.ProductoEntity;
import com.jdc.clinica.repository.ICategoriaRepository;
import com.jdc.clinica.repository.IProductoCategoriaRepository;
import com.jdc.clinica.repository.IProductoRepository;
import com.jdc.clinica.services.IProductoCategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoCategoriaServiceImplement implements IProductoCategoriaService {

    @Autowired
    private IProductoCategoriaRepository productoCategoriaRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private ICategoriaRepository categoriaRepository;

    // =========================================
    // LISTAR
    // =========================================
    @Override
    public List<ProductoCategoriaEntity> findAll() {
        return productoCategoriaRepository.findAll();
    }

    // =========================================
    // BUSCAR POR ID
    // =========================================
    @Override
    public ProductoCategoriaEntity findById(Long id) {
        return productoCategoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación Producto-Categoría no encontrada con ID: " + id));
    }

    // =========================================
    // CREAR
    // =========================================
    @Override
    public ProductoCategoriaEntity save(ProductoCategoriaDTO dto) {

        ProductoCategoriaEntity entity;

        // EDITAR
        if (dto.getId() != null) {
            entity = productoCategoriaRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("No existe la relación con ID: " + dto.getId()));
        } else {
            // CREAR
            entity = new ProductoCategoriaEntity();
        }


        ProductoEntity producto = productoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + dto.getProductoId()));

        entity.setProducto(producto);


        CategoriaEntity categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + dto.getCategoriaId()));

        entity.setCategoria(categoria);

        return productoCategoriaRepository.save(entity);
    }

    // =========================================
    // ELIMINAR RELACIÓN
    // =========================================
    @Override
    public void delete(Long id) {
        ProductoCategoriaEntity entity = productoCategoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe relación Producto-Categoría con ID: " + id));

        productoCategoriaRepository.delete(entity);
    }
}
