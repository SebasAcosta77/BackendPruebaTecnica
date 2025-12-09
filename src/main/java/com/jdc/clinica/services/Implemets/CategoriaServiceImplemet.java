package com.jdc.clinica.services.Implemets;

import com.jdc.clinica.DTO.CategoriaDTO;
import com.jdc.clinica.Mapper.CategoriaMapper;
import com.jdc.clinica.models.CategoriaEntity;
import com.jdc.clinica.repository.ICategoriaRepository;
import com.jdc.clinica.services.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImplemet implements ICategoriaService {

    @Autowired
    private ICategoriaRepository categoriaRepository;


    // LISTAR TODAS LAS CATEGORÍAS

    @Override
    public List<CategoriaEntity> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public List<CategoriaDTO> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaMapper::toDTO)
                .toList();
    }


    // BUSCAR POR ID

    @Override
    public CategoriaEntity findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
    }


    // GUARDAR --Pendiente conflicto con tabla intermedia Categoria-productos, revisar 10:00am

    @Override
    public CategoriaEntity save(CategoriaDTO dto) {

        CategoriaEntity entity;

        if (dto.getId() != null) {

            entity = categoriaRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("No existe categoría con ID: " + dto.getId()));
        } else {
            // CREAR
            entity = new CategoriaEntity();
        }

        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());

        return categoriaRepository.save(entity);
    }


    // ACTUALIZAR SOLO POR ID

    @Override
    public CategoriaEntity actualizarPorId(Long id, CategoriaDTO dto) {

        CategoriaEntity entity = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe categoría con ID: " + id));

        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());

        return categoriaRepository.save(entity);
    }


    // ELIMINAR CATEGORÍA

    @Override
    public void delete(Long id) {
        CategoriaEntity entity = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe categoría con ID: " + id));

        categoriaRepository.delete(entity);
    }
}
