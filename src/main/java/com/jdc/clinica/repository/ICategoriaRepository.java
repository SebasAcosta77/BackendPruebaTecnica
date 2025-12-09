package com.jdc.clinica.repository;

import com.jdc.clinica.models.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
}
