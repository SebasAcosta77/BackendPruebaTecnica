package com.jdc.clinica.services;


import com.jdc.clinica.DTO.CategoriaDTO;
import com.jdc.clinica.DTO.ClienteDTO;
import com.jdc.clinica.models.CategoriaEntity;
import com.jdc.clinica.models.ClienteEntity;

import java.util.List;

public interface IClienteService {
    public List<ClienteEntity> findAll();
    List<ClienteDTO> listar();
    public ClienteEntity findById(Long id);
    public ClienteEntity save(ClienteDTO clienteDTO);
    public ClienteEntity actualizarPorId(Long id, ClienteDTO clienteDTO);
    void delete(Long id);
}
