package com.jdc.clinica.repository;

import com.jdc.clinica.models.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmpresaRepository extends JpaRepository<EmpresaEntity, String> {
}
