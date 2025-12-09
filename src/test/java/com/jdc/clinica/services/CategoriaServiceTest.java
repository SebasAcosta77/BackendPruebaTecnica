package com.jdc.clinica.services;

import com.jdc.clinica.DTO.CategoriaDTO;
import com.jdc.clinica.Mapper.CategoriaMapper;
import com.jdc.clinica.models.CategoriaEntity;
import com.jdc.clinica.repository.ICategoriaRepository;
import com.jdc.clinica.services.Implemets.CategoriaServiceImplemet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoriaServiceTest {

    @Mock
    private ICategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServiceImplemet categoriaService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }


    @Test
    void findAll_retornaListaCategorias() {
        CategoriaEntity c1 = new CategoriaEntity(1L, "Medicamentos", "Categoría de fármacos");
        CategoriaEntity c2 = new CategoriaEntity(2L, "Insumos", "Material hospitalario");

        when(categoriaRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<CategoriaEntity> result = categoriaService.findAll();

        assertEquals(2, result.size());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    void listar_retornaListaCategoriaDTO() {
        CategoriaEntity c1 = new CategoriaEntity(1L, "Medicamentos", "Categoría de fármacos");

        when(categoriaRepository.findAll()).thenReturn(List.of(c1));

        List<CategoriaDTO> result = categoriaService.listar();

        assertEquals(1, result.size());
        assertEquals("Medicamentos", result.get(0).getNombre());
        verify(categoriaRepository).findAll();
    }


    @Test
    void findById_existente_retornaCategoria() {
        CategoriaEntity entity = new CategoriaEntity(1L, "Equipos", "Máquinas médicas");
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(entity));

        CategoriaEntity result = categoriaService.findById(1L);

        assertEquals("Equipos", result.getNombre());
        verify(categoriaRepository).findById(1L);
    }

    @Test
    void findById_noExistente_lanzaError() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> categoriaService.findById(99L));

        assertTrue(exception.getMessage().contains("Categoría no encontrada"));
    }


    @Test
    void save_creaNuevaCategoria() {
        CategoriaDTO dto = new CategoriaDTO(null, "Nueva", "Pruebas");
        CategoriaEntity saved = new CategoriaEntity(1L, "Nueva", "Pruebas");

        when(categoriaRepository.save(any())).thenReturn(saved);

        CategoriaEntity result = categoriaService.save(dto);

        assertEquals("Nueva", result.getNombre());
        verify(categoriaRepository).save(any());
    }

    @Test
    void save_editaCategoriaExistente() {
        CategoriaDTO dto = new CategoriaDTO(1L, "Actualizado", "Modificado");
        CategoriaEntity existente = new CategoriaEntity(1L, "Viejo", "Desc anterior");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(categoriaRepository.save(any())).thenReturn(existente);

        CategoriaEntity result = categoriaService.save(dto);

        assertEquals("Actualizado", result.getNombre());
        verify(categoriaRepository).save(any());
    }


    @Test
    void actualizarPorId_modificaCategoria() {
        CategoriaDTO dto = new CategoriaDTO(1L, "Dental", "Uso odontológico");
        CategoriaEntity existente = new CategoriaEntity(1L, "Antiguo", "Viejo");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(categoriaRepository.save(existente)).thenReturn(existente);

        CategoriaEntity result = categoriaService.actualizarPorId(1L, dto);

        assertEquals("Dental", result.getNombre());
        verify(categoriaRepository).save(existente);
    }


    @Test
    void delete_eliminaCategoria() {
        CategoriaEntity existente = new CategoriaEntity(1L, "Eliminar", "Borrar");

        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(existente));

        categoriaService.delete(1L);

        verify(categoriaRepository).delete(existente);
    }
}