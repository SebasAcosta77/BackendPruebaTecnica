package com.jdc.clinica.rest;

import com.jdc.clinica.DTO.UsuarioDTO;
import com.jdc.clinica.DTO.UsuarioListDTO;
import com.jdc.clinica.models.UsuariosEntity;
import com.jdc.clinica.services.IUsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")

@CrossOrigin(origins = "*")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

   
    // LISTAR TODOS


    @GetMapping("/list")
    public ResponseEntity<List<UsuarioListDTO>> listar() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

   
    // BUSCAR POR ID
   
    @GetMapping("/{id}")
    public ResponseEntity<UsuariosEntity> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    
    // CREAR

    @PostMapping
    public ResponseEntity<UsuariosEntity> guardar(@RequestBody UsuarioDTO dto) {
        UsuariosEntity nuevo = usuarioService.save(dto);
        return ResponseEntity.ok(nuevo);
    }

 
    // ACTUALIZAR POR ID

    @PutMapping("/{id}")
    public ResponseEntity<UsuariosEntity> actualizar(
            @PathVariable Long id,
            @RequestBody UsuarioDTO dto) {

        UsuariosEntity actualizado = usuarioService.actualizarPorId(id, dto);
        return ResponseEntity.ok(actualizado);
    }

  
    // ELIMINAR
   
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        usuarioService.delete(id);
        return ResponseEntity.ok("Usuario eliminado con éxito");
    }
}
