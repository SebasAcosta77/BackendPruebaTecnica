package com.jdc.clinica.rest;

import com.jdc.clinica.DTO.EmpresaDTO;
import com.jdc.clinica.models.EmpresaEntity;
import com.jdc.clinica.services.IEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaRest {

    @Autowired
    private IEmpresaService empresaService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public ResponseEntity<?> save(@Validated @RequestBody EmpresaDTO empresaDTO) {
        try {
            EmpresaEntity empresa = empresaService.save(empresaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(empresa); // 201 → Creado exitosamente
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // error controlado
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado al guardar la empresa: " + e.getMessage());
        }
    }

    // =========================================
    // LISTAR EMPRESAS
    // =========================================
    @PreAuthorize("hasAnyRole('ADMIN','EXTERNO')")
    @GetMapping("/list")
    public ResponseEntity<List<EmpresaEntity>> list() {
        return ResponseEntity.ok(empresaService.findAll());
    }

    // =========================================
    // BUSCAR POR NIT
    // =========================================
    @PreAuthorize("hasAnyRole('ADMIN','EXTERNO')")
    @GetMapping("/buscar/{nit}")
    public ResponseEntity<EmpresaEntity> getByNit(@PathVariable String nit) {
        try {
            return ResponseEntity.ok(empresaService.findById(nit));
        } catch (Exception e) {
            System.out.println("Error al buscar empresa: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // =========================================
    // ACTUALIZAR EMPRESA
    // =========================================
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/actualizar/{nit}")
    public ResponseEntity<EmpresaEntity> actualizar(
            @PathVariable String nit,
            @Validated @RequestBody EmpresaDTO empresaDTO) {

        try {
            EmpresaEntity empresaActualizada = empresaService.actualizarPorId(nit, empresaDTO);
            return ResponseEntity.ok(empresaActualizada);
        } catch (Exception e) {
            System.out.println("Error al actualizar empresa: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // =========================================
    // ELIMINAR EMPRESA
    // =========================================
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/eliminar/{nit}")
    public ResponseEntity<?> delete(@PathVariable String nit) {

        try {
            boolean eliminada = empresaService.eliminarSiNoTieneDependencias(nit);

            if (eliminada) {
                return ResponseEntity.ok("✔ Empresa eliminada correctamente");
            } else {
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body("⚠ No se pudo eliminar porque tiene inventarios o productos → Se desactivó automáticamente");
            }

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/desactivar/{nit}")
    public ResponseEntity<?> desactivar(@PathVariable String nit) {
        try {
            EmpresaEntity empresa = empresaService.desactivar(nit);
            return ResponseEntity.ok("Empresa desactivada correctamente → Estado = INACTIVO");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(" No se pudo desactivar: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/activar/{nit}")
    public ResponseEntity<?> activar(@PathVariable String nit) {
        try {
            EmpresaEntity empresa = empresaService.activar(nit);
            return ResponseEntity.ok("Empresa ACTIVADA correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(" No se pudo activar: " + e.getMessage());
        }
    }
}
