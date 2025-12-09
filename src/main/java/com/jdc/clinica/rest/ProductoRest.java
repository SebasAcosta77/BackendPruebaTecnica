package com.jdc.clinica.rest;

import com.jdc.clinica.DTO.ProductoDTO;
import com.jdc.clinica.models.ProductoEntity;
import com.jdc.clinica.services.IProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class ProductoRest {

    private static final Logger log = LoggerFactory.getLogger(ProductoRest.class);

    @Autowired
    private IProductoService productoService;


    // CREAR PRODUCTO

    @Operation(
            summary = "Crear nuevo producto",
            description = "Crea un producto y devuelve el registro creado.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ProductoDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Producto creado correctamente"),
                    @ApiResponse(responseCode = "409", description = "Conflicto por datos duplicados o inválidos"),
                    @ApiResponse(responseCode = "400", description = "Datos incorrectos"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public ResponseEntity<?> save(@Validated @RequestBody ProductoDTO productoDTO) {

        try {

            ProductoDTO producto = productoService.save(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(producto);

        } catch (RuntimeException ex) {

            log.error("Error al guardar producto: {}", ex.getMessage());

            if (ex.getMessage().contains("código") ||
                    ex.getMessage().contains("existe") ||
                    ex.getMessage().contains("categoría") ||
                    ex.getMessage().contains("empresa")) {

                return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

        } catch (Exception e) {

            log.error("Error inesperado al guardar producto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al guardar el producto");
        }
    }


    // LISTAR PRODUCTOS

    @Operation(
            summary = "Listar productos",
            description = "Retorna todos los productos registrados.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de productos",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = ProductoDTO.class))
                            )
                    )
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN','EXTERNO')")
    @GetMapping("/list")
    public List<ProductoDTO> listar() {
        return productoService.listar();
    }


    // BUSCAR POR ID

    @Operation(
            summary = "Buscar producto por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Producto encontrado"),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
            }
    )
    @PreAuthorize("hasAnyRole('ADMIN','EXTERNO')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProductoEntity> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productoService.findById(id));
        } catch (Exception e) {
            log.warn("Producto con ID {} no encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // ACTUALIZAR PRODUCTO

    @Operation(
            summary = "Actualizar producto por ID",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = ProductoDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Producto actualizado"),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
            }
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ProductoEntity> actualizar(
            @PathVariable Long id,
            @Validated @RequestBody ProductoDTO productoDTO) {

        try {
            ProductoEntity productoActualizado = productoService.actualizarPorId(id, productoDTO);
            return ResponseEntity.ok(productoActualizado);
        } catch (Exception e) {
            log.warn("Error al actualizar producto con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // ELIMINAR PRODUCTO

    @Operation(
            summary = "Eliminar producto por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Producto eliminado"),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
            }
    )
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            productoService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Error al eliminar producto con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
