package com.jdc.clinica.services.Implemets;

import com.jdc.clinica.DTO.InventarioDTO;
import com.jdc.clinica.models.EmpresaEntity;
import com.jdc.clinica.models.InventarioEntity;
import com.jdc.clinica.models.ProductoEntity;
import com.jdc.clinica.repository.IEmpresaRepository;
import com.jdc.clinica.repository.IInventarioRepository;
import com.jdc.clinica.repository.IProductoRepository;
import com.jdc.clinica.services.IInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioServiceImplement implements IInventarioService {

    @Autowired
    private IInventarioRepository inventarioRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IEmpresaRepository empresaRepository;

    // =========================================
    // LISTAR TODO EL INVENTARIO
    // =========================================
    @Override
    public List<InventarioEntity> findAll() {
        return inventarioRepository.findAll();
    }

    // =========================================
    // BUSCAR POR ID
    // =========================================
    @Override
    public InventarioEntity findById(Long id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + id));
    }

    // =========================================
    // CREAR
    // =========================================
    @Override
    public InventarioEntity save(InventarioDTO inventarioDTO) {

        InventarioEntity entity;

        // EDITAR
        if (inventarioDTO.getIdinventario() != null) {
            entity = inventarioRepository.findById(inventarioDTO.getIdinventario())
                    .orElseThrow(() ->
                            new RuntimeException("No existe inventario con ID: " + inventarioDTO.getIdinventario()));
        } else {
            // CREAR
            entity = new InventarioEntity();
        }

        // ===========================
        // VALIDAR EMPRESA
        // ===========================
        EmpresaEntity empresa = empresaRepository.findById(inventarioDTO.getEmpresaNit())
                .orElseThrow(() ->
                        new RuntimeException("La empresa con NIT " + inventarioDTO.getEmpresaNit() + " no existe"));

        entity.setEmpresa(empresa);

        // ===========================
        // VALIDAR PRODUCTO
        // ===========================
        ProductoEntity producto = productoRepository.findById(inventarioDTO.getProductoId())
                .orElseThrow(() ->
                        new RuntimeException("El producto con ID " + inventarioDTO.getProductoId() + " no existe"));

        entity.setProducto(producto);

        // ===========================
        // CAMPOS DEL INVENTARIO
        // ===========================
        entity.setStock(inventarioDTO.getStock());
        entity.setStockMinimo(inventarioDTO.getStockMinimo());
        entity.setEstado(
                inventarioDTO.getEstado() != null
                        ? inventarioDTO.getEstado()
                        : "ACTIVO"
        );

        return inventarioRepository.save(entity);
    }

    // =========================================
    // ACTUALIZAR INVENTARIO POR ID
    // =========================================
    @Override
    public InventarioEntity actualizarPorId(Long id, InventarioDTO inventarioDTO) {

        InventarioEntity entity = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + id));


        if (inventarioDTO.getEmpresaNit() != null) {
            EmpresaEntity empresa = empresaRepository.findById(inventarioDTO.getEmpresaNit())
                    .orElseThrow(() ->
                            new RuntimeException("La empresa con NIT " + inventarioDTO.getEmpresaNit() + " no existe"));
            entity.setEmpresa(empresa);
        }


        if (inventarioDTO.getProductoId() != null) {
            ProductoEntity producto = productoRepository.findById(inventarioDTO.getProductoId())
                    .orElseThrow(() ->
                            new RuntimeException("El producto con ID " + inventarioDTO.getProductoId() + " no existe"));
            entity.setProducto(producto);
        }

        // ===========================
        // ACTUALIZAR CAMPOS
        // ===========================
        entity.setStock(inventarioDTO.getStock());
        entity.setStockMinimo(inventarioDTO.getStockMinimo());

        if (inventarioDTO.getEstado() != null) {
            entity.setEstado(inventarioDTO.getEstado());
        }

        return inventarioRepository.save(entity);
    }

    // =========================================
    // ELIMINAR
    // =========================================
    @Override
    public void delete(Long id) {
        InventarioEntity entity = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe inventario con ID: " + id));

        inventarioRepository.delete(entity);
    }

    @Override
    public List<InventarioEntity> findByEmpresa(String empresaNit) {
        // 1. Validar que la empresa exista
        EmpresaEntity empresa = empresaRepository.findById(empresaNit)
                .orElseThrow(() ->
                        new RuntimeException("La empresa con NIT " + empresaNit + " no existe"));

        // 2. Retornar lista de inventario filtrado por empresa
        return inventarioRepository.findByEmpresa(empresa);
    }

    @Override
    public Long countByEmpresa_Nit(String nit) {
        return inventarioRepository.countByEmpresa_Nit(nit);
    }
}
