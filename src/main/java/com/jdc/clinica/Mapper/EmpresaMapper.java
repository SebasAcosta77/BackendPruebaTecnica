package com.jdc.clinica.Mapper;

import com.jdc.clinica.DTO.EmpresaDTO;
import com.jdc.clinica.models.EmpresaEntity;

public class EmpresaMapper {

    
    // Entity 

    public static EmpresaDTO toDTO(EmpresaEntity empresa) {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setNit(empresa.getNit());
        dto.setNombre(empresa.getNombre());
        dto.setDireccion(empresa.getDireccion());
        dto.setTelefono(empresa.getTelefono());
        return dto;
    }

  
    public static EmpresaEntity toEntity(EmpresaDTO dto) {
        EmpresaEntity entity = new EmpresaEntity();
        entity.setNit(dto.getNit());
        entity.setNombre(dto.getNombre());
        entity.setDireccion(dto.getDireccion());
        entity.setTelefono(dto.getTelefono());
        entity.setEstado(dto.getEstado());
        return entity;
    }

    
    public static void updateEntity(EmpresaEntity entity, EmpresaDTO dto){
        entity.setNombre(dto.getNombre());
        entity.setDireccion(dto.getDireccion());
        entity.setTelefono(dto.getTelefono());
        entity.setEstado(dto.getEstado());
    }
}
