package com.jdc.clinica.rest;

import com.jdc.clinica.DTO.ClienteDTO;
import com.jdc.clinica.models.ClienteEntity;
import com.jdc.clinica.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteRest {

    @Autowired
    private IClienteService clienteService;


    // CREAR 
   
    @PostMapping("/guardar")
    public ResponseEntity<ClienteEntity> save(@Validated @RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteEntity cliente = clienteService.save(clienteDTO);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            System.out.println("Error al guardar cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    
    // LISTAR CLIENTES
   
    @GetMapping("/list")
    public ResponseEntity<List<ClienteEntity>> list() {
        return ResponseEntity.ok(clienteService.findAll());
    }
    public List<ClienteDTO> listar() {
        return clienteService.listar();
    }

  
    // BUSCAR POR ID
   
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ClienteEntity> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clienteService.findById(id));
        } catch (Exception e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
   
    // ACTUALIZAR POR ID

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ClienteEntity> actualizarPorId(
            @PathVariable Long id,
            @Validated @RequestBody ClienteDTO clienteDTO) {

        try {
            ClienteEntity clienteActualizado = clienteService.actualizarPorId(id, clienteDTO);
            return ResponseEntity.ok(clienteActualizado);
        } catch (Exception e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

 
    // ELIMINAR CLIENTE
   
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            clienteService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
