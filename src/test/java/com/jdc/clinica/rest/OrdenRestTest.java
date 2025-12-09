package com.jdc.clinica.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdc.clinica.DTO.OrdenDTO;
import com.jdc.clinica.models.OrdenEntity;
import com.jdc.clinica.services.IOrdenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrdenRest.class)
class OrdenRestTest {

    @Autowired
    private MockMvc mockMvc;


    private IOrdenService ordenService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrdenEntity ordenEntity;
    private OrdenDTO ordenDTO;




    // POST - GUARDAR ORDEN

    @Test
    void testGuardarOrden() throws Exception {
        when(ordenService.save(any(OrdenDTO.class))).thenReturn(ordenEntity);

        mockMvc.perform(post("/api/orden/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordenDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descripcion").value("Consulta general"));
    }


    // GET - LISTAR

    @Test
    void testListarOrdenes() throws Exception {
        List<OrdenEntity> lista = Arrays.asList(ordenEntity);

        when(ordenService.findAll()).thenReturn(lista);

        mockMvc.perform(get("/api/orden/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descripcion").value("Consulta general"));
    }


    // GET - BUSCAR POR ID

    @Test
    void testBuscarPorId() throws Exception {
        when(ordenService.findById(1L)).thenReturn(ordenEntity);

        mockMvc.perform(get("/api/orden/buscar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("PENDIENTE"));
    }


    // PUT - ACTUALIZAR - pendiente




    // DELETE - ELIMINAR

    @Test
    void testEliminar() throws Exception {
        doNothing().when(ordenService).delete(1L);

        mockMvc.perform(delete("/api/orden/eliminar/1"))
                .andExpect(status().isOk());

        verify(ordenService, times(1)).delete(1L);
    }
}
