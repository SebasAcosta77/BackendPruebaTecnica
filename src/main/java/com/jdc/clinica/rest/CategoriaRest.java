package com.jdc.clinica.rest;

import com.jdc.clinica.DTO.CategoriaDTO;
import com.jdc.clinica.models.CategoriaEntity;
import com.jdc.clinica.services.ICategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
@Tag(name = "Categorías", description = "Gestión de categorías de productos")
public class CategoriaRest {

    @Autowired
    private ICategoriaService categoriaService;

    // =========================================
    // CREAR / EDITAR CATEGORÍA
    // =========================================
    @Operation(
            summary = "Crear o editar una categoría",
            description = "Registra una nueva categoría o actualiza una existente enviando un DTO."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría registrada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos enviados")
    })
    @PostMapping("/guardar")
    public ResponseEntity<CategoriaEntity> save(@Validated @RequestBody CategoriaDTO categoriaDTO) {
        try {
            CategoriaEntity categoria = categoriaService.save(categoriaDTO);
            return ResponseEntity.ok(categoria);
        } catch (Exception e) {
            System.out.println("Error al guardar categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // =========================================
    // LISTAR CATEGORÍAS
    // =========================================
    @Operation(
            summary = "Listar todas las categorías",
            description = "Devuelve un listado completo de todas las categorías registradas."
    )
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/list")
    public ResponseEntity<List<CategoriaEntity>> list() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    // =========================================
    // BUSCAR POR ID
    // =========================================
    @Operation(
            summary = "Buscar categoría por ID",
            description = "Devuelve los datos de una categoría a partir de su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
            @ApiResponse(responseCode = "404", description = "No existe la categoría con ese ID")
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<CategoriaEntity> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoriaService.findById(id));
        } catch (Exception e) {
            System.out.println("Error al buscar categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // =========================================
    // ACTUALIZAR POR ID
    // =========================================
    @Operation(
            summary = "Actualizar categoría por ID",
            description = "Permite actualizar una categoría específica enviando su ID y un DTO."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "No existe la categoría especificada")
    })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<CategoriaEntity> actualizarPorId(
            @PathVariable Long id,
            @Validated @RequestBody CategoriaDTO categoriaDTO) {

        try {
            CategoriaEntity categoriaActualizada = categoriaService.actualizarPorId(id, categoriaDTO);
            return ResponseEntity.ok(categoriaActualizada);
        } catch (Exception e) {
            System.out.println("Error al actualizar categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // =========================================
    // ELIMINAR
    // =========================================
    @Operation(
            summary = "Eliminar categoría",
            description = "Elimina una categoría existente por su ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "No existe la categoría con ese ID")
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            categoriaService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error al eliminar categoría: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
