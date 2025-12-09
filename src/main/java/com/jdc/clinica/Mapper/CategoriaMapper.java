package com.jdc.clinica.Mapper;

import com.jdc.clinica.DTO.CategoriaDTO;
import com.jdc.clinica.models.CategoriaEntity;

public class CategoriaMapper {

    // Convertir Entity  DTO
    public static CategoriaDTO toDTO(CategoriaEntity categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getIdcategoria());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }

    // Convertir DTO  Entity (para guardar / actualizar)
    public static CategoriaEntity toEntity(CategoriaDTO dto) {
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setIdcategoria(dto.getId()); // opcional si es nuevo
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return categoria;
    }
}
