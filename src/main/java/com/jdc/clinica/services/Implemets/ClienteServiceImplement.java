package com.jdc.clinica.services.Implemets;

import com.jdc.clinica.DTO.ClienteDTO;
import com.jdc.clinica.Mapper.ClienteMapper;
import com.jdc.clinica.models.CategoriaEntity;
import com.jdc.clinica.models.ClienteEntity;
import com.jdc.clinica.repository.IClienteRepository;
import com.jdc.clinica.services.IClienteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImplement implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

  
    // LISTAR TODOS LOS CLIENTES

    @Override
    public List<ClienteEntity> findAll() {
        return clienteRepository.findAll();
    }
    @Override
    @Transactional
    public List<ClienteDTO> listar() {
        return clienteRepository.findAll()
                .stream()
                .map(ClienteMapper::toDTO)
                .toList();
    }

 
    // BUSCAR POR ID

    @Override
    public ClienteEntity findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }


    // CREAR 
  
    @Override
    public ClienteEntity save(ClienteDTO dto) {

        ClienteEntity entity;

    
        if (dto.getIdcliente() != null) {
            entity = clienteRepository.findById(dto.getIdcliente())
                    .orElseThrow(() -> new RuntimeException("No existe cliente con ID: " + dto.getIdcliente()));
        } else {
            // CREAR
            entity = new ClienteEntity();
        }

        entity.setNombre(dto.getNombre());
        entity.setTelefono(dto.getTelefono());

        return clienteRepository.save(entity);
    }

    @Override
    public ClienteEntity actualizarPorId(Long id, ClienteDTO clienteDTO) {
        ClienteEntity entity = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe cliente con ID: " + id));

        entity.setNombre(clienteDTO.getNombre());

        entity.setTelefono(clienteDTO.getTelefono());

        return clienteRepository.save(entity);
    }


    // ELIMINAR CLIENTE
  
    @Override
    public void delete(Long id) {
        ClienteEntity entity = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe cliente con ID: " + id));

        clienteRepository.delete(entity);
    }
}
