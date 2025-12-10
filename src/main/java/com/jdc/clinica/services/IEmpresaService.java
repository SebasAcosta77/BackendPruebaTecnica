package com.jdc.clinica.services;

import com.jdc.clinica.DTO.CategoriaDTO;
import com.jdc.clinica.DTO.EmpresaDTO;
import com.jdc.clinica.models.CategoriaEntity;
import com.jdc.clinica.models.EmpresaEntity;

import java.util.List;

public interface IEmpresaService {
    List<EmpresaEntity> findAll();

    List<EmpresaDTO> listar();

    EmpresaEntity findById(String nit);   

    EmpresaEntity save(EmpresaDTO empresaDTO);

    EmpresaEntity actualizarPorId(String nit, EmpresaDTO empresaDTO);

    void delete(String nit);  

    EmpresaEntity desactivar(String nit);

    EmpresaEntity activar(String nit);

    boolean eliminarSiNoTieneDependencias(String nit);



}
