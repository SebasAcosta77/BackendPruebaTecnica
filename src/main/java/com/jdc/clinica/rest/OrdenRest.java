package com.jdc.clinica.rest;

import com.jdc.clinica.DTO.OrdenDTO;
import com.jdc.clinica.models.OrdenEntity;
import com.jdc.clinica.services.IOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orden")
public class OrdenRest {

    @Autowired
    private IOrdenService ordenService;


    // CREAR ORDEN
  
    @PostMapping("/guardar")
    public ResponseEntity<OrdenEntity> save(@Validated @RequestBody OrdenDTO ordenDTO) {
        try {
            OrdenEntity orden = ordenService.save(ordenDTO);
            return ResponseEntity.ok(orden);
        } catch (Exception e) {
            System.out.println("Error al guardar orden: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

   
    // LISTAR 
  
    @GetMapping("/list")
    public ResponseEntity<List<OrdenEntity>> list() {
        return ResponseEntity.ok(ordenService.findAll());
    }


    // BUSCAR POR ID
  
    @GetMapping("/buscar/{id}")
    public ResponseEntity<OrdenEntity> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ordenService.findById(id));
        } catch (Exception e) {
            System.out.println("Error al buscar orden: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // ACTUALIZAR ORDEN POR ID
   
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<OrdenEntity> actualizar(
            @PathVariable Long id,
            @Validated @RequestBody OrdenDTO ordenDTO) {

        try {
            OrdenEntity ordenActualizada = ordenService.actualizarPorId(id, ordenDTO);
            return ResponseEntity.ok(ordenActualizada);
        } catch (Exception e) {
            System.out.println("Error al actualizar orden: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // ELIMINAR ÓRDEN

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            ordenService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error al eliminar orden: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
