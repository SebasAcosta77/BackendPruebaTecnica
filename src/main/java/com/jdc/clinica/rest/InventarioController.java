package com.jdc.clinica.rest;

import com.jdc.clinica.DTO.InventarioDTO;
import com.jdc.clinica.models.InventarioEntity;
import com.jdc.clinica.services.IInventarioService;
import com.jdc.clinica.services.Implemets.InventarioPDFServiceImplement;
import com.jdc.clinica.services.Implemets.MailServiceImplement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin("*")
public class InventarioController {

    @Autowired
    private IInventarioService inventarioService;

    @Autowired
    private InventarioPDFServiceImplement pdfService;

    @Autowired
    private MailServiceImplement emailPDFService;



 
    // LISTAR
  
    @GetMapping("/list")
    public ResponseEntity<List<InventarioEntity>> listar() {
        List<InventarioEntity> lista = inventarioService.findAll();
        return lista.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(lista);
    }


   
    // BUSCAR POR ID
 
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(inventarioService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(" Inventario con ID " + id + " no existe.");
        }
    }


   
    // CREAR
 
    @PostMapping("/save")
    public ResponseEntity<?> guardar(@RequestBody InventarioDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.save(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(" Error al guardar: " + e.getMessage());
        }
    }


 
    // ACTUALIZAR
  
    @PutMapping("/update/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody InventarioDTO dto) {
        try {
            return ResponseEntity.ok(inventarioService.actualizarPorId(id, dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: No existe inventario con ID " + id);
        }
    }


   
    // ELIMINAR

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            inventarioService.delete(id);
            return ResponseEntity.ok("🗑 Inventario eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(" Error al eliminar: ID no encontrado.");
        }
    }



    // DESCARGAR PDF
  
    @GetMapping("/pdf")
    public ResponseEntity<InputStreamResource> descargarPDF() {

        List<InventarioEntity> inventario = inventarioService.findAll();
        ByteArrayInputStream pdf = pdfService.generarPDF(inventario);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inventario.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }



    // ENVIAR PDF POR CORREO Pendiente!! pasar SES a produciion para enviar emails a cualquier perodna
   
    @PostMapping("/pdf/enviar")
    public ResponseEntity<String> enviarPDF(@RequestParam String email) {

        try {
            List<InventarioEntity> inventario = inventarioService.findAll();
            ByteArrayInputStream pdf = pdfService.generarPDF(inventario);

            emailPDFService.sendPdfEmail(pdf.readAllBytes(), email);


            return ResponseEntity.ok("📧 PDF enviado correctamente al correo " + email);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(" Error al enviar el PDF: " + e.getMessage());
        }
    }

    @GetMapping("/empresa/{nit}")
    public ResponseEntity<List<InventarioEntity>> listarPorEmpresa(@PathVariable String nit) {
        return ResponseEntity.ok(inventarioService.findByEmpresa(nit));
    }

}
