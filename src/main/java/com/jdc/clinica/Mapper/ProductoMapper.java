package com.jdc.clinica.Mapper;

import com.jdc.clinica.DTO.ProductoDTO;
import com.jdc.clinica.models.ProductoEntity;

import java.util.stream.Collectors;

public class ProductoMapper {

    public static ProductoDTO toDTO(ProductoEntity p) {
        ProductoDTO dto = new ProductoDTO();

        dto.setIdproducto(p.getIdproducto());
        dto.setCodigo(p.getCodigo());
        dto.setNombre(p.getNombre());
        dto.setCaracteristicas(p.getCaracteristicas());
        dto.setPrecioCop(p.getPrecio_cop());
        dto.setPrecioUsd(p.getPrecio_usd());
        dto.setPrecioEur(p.getPrecio_eur());

       
        dto.setEmpresaNit(p.getEmpresa().getNit());

       
        if (p.getCategorias() != null)
            dto.setCategoriasIds(
                    p.getCategorias().stream()
                            .map(c -> c.getCategoria().getIdcategoria())
                            .collect(Collectors.toList())
            );

        return dto;
    }
}
