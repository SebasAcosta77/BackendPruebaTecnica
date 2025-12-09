package com.jdc.clinica.repository;

import com.jdc.clinica.models.ProductoCategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoCategoriaRepository extends JpaRepository<ProductoCategoriaEntity, Long> {
}
