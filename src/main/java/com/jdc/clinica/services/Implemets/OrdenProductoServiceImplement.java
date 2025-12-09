package com.jdc.clinica.services.Implemets;

import com.jdc.clinica.DTO.OrdenProductoDTO;
import com.jdc.clinica.models.OrdenEntity;
import com.jdc.clinica.models.OrdenProductoEntity;
import com.jdc.clinica.models.ProductoEntity;
import com.jdc.clinica.repository.IOrdenProductoRepository;
import com.jdc.clinica.repository.IOrdenRepository;
import com.jdc.clinica.repository.IProductoRepository;
import com.jdc.clinica.services.IOrdenProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenProductoServiceImplement implements IOrdenProductoService {

    @Autowired
    private IOrdenProductoRepository ordenProductoRepository;

    @Autowired
    private IOrdenRepository ordenRepository;

    @Autowired
    private IProductoRepository productoRepository;

    // =========================================
    // LISTAR TODOS
    // =========================================
    @Override
    public List<OrdenProductoEntity> findAll() {
        return ordenProductoRepository.findAll();
    }

    // =========================================
    // BUSCAR POR ID
    // =========================================
    @Override
    public OrdenProductoEntity findById(Long id) {
        return ordenProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrdenProducto no encontrado con ID: " + id));
    }

    // =========================================
    // CREAR / EDITAR
    // =========================================
    @Override
    public OrdenProductoEntity save(OrdenProductoDTO dto) {

        OrdenProductoEntity entity;

        // EDITAR
        if (dto.getIdordenproducto() != null) {
            entity = ordenProductoRepository.findById(dto.getIdordenproducto())
                    .orElseThrow(() ->
                            new RuntimeException("No existe OrdenProducto con ID: " + dto.getIdordenproducto()));
        } else {
            // CREAR
            entity = new OrdenProductoEntity();
        }

        // ASOCIAR ORDEN
        OrdenEntity orden = ordenRepository.findById(dto.getFkorden())
                .orElseThrow(() -> new RuntimeException("No existe ORDEN con ID: " + dto.getFkorden()));
        entity.setOrden(orden);

        // ASOCIAR PRODUCTO
        ProductoEntity producto = productoRepository.findById(dto.getFkproducto())
                .orElseThrow(() -> new RuntimeException("No existe PRODUCTO con ID: " + dto.getFkproducto()));
        entity.setProducto(producto);

        // CANTIDAD
        entity.setCantidad(dto.getCantidad());

        return ordenProductoRepository.save(entity);
    }

    // =========================================
    // ELIMINAR
    // =========================================
    @Override
    public void delete(Long id) {
        OrdenProductoEntity entity = ordenProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe OrdenProducto con ID: " + id));

        ordenProductoRepository.delete(entity);
    }
}
