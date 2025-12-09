package com.jdc.clinica.Mapper;

import com.jdc.clinica.DTO.ClienteDTO;
import com.jdc.clinica.models.ClienteEntity;



import java.util.stream.Collectors;

public class ClienteMapper {

    public static ClienteDTO toDTO(ClienteEntity c) {

        ClienteDTO dto = new ClienteDTO();

        dto.setIdcliente(c.getIdcliente());
        dto.setNombre(c.getNombre());
        dto.setTelefono(c.getTelefono());

        // Email viene desde Usuario
        dto.setEmail(c.getUsuario() != null ? c.getUsuario().getEmail() : null);

        // Convertimos órdenes lista de IDs
        if (c.getOrdenes() != null)
            dto.setOrdenesIds(
                    c.getOrdenes()
                            .stream()
                            .map(o -> o.getIdorden())
                            .collect(Collectors.toList())
            );

        return dto;
    }
}