package com.jdc.clinica.services;

import com.jdc.clinica.DTO.ClienteDTO;
import com.jdc.clinica.Mapper.ClienteMapper;
import com.jdc.clinica.models.ClienteEntity;
import com.jdc.clinica.repository.IClienteRepository;
import com.jdc.clinica.services.Implemets.ClienteServiceImplement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private IClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImplement clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    // TEST LISTAR ENTIDADES

    @Test
    void testFindAll() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setIdcliente(1L);
        cliente.setNombre("Juan");
        cliente.setTelefono("3001112233");

        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<ClienteEntity> lista = clienteService.findAll();

        assertEquals(1, lista.size());
        assertEquals("Juan", lista.get(0).getNombre());
    }


    //  TEST LISTAR DTO

    @Test
    void testListarDTO() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setIdcliente(1L);
        cliente.setNombre("Juan");
        cliente.setTelefono("3001112233");

        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<ClienteDTO> lista = clienteService.listar();

        assertEquals(1, lista.size());
        assertEquals("Juan", lista.get(0).getNombre());
    }


    // TEST FIND BY ID

    @Test
    void testFindById() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setIdcliente(1L);
        cliente.setNombre("Pedro");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteEntity resultado = clienteService.findById(1L);

        assertEquals("Pedro", resultado.getNombre());
    }

    @Test
    void testFindById_NotFound() {
        when(clienteRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> clienteService.findById(2L));
    }


    // TEST CREAR CLIENTE

    @Test
    void testSaveNuevoCliente() {
        ClienteDTO dto = new ClienteDTO(null, "Nuevo", "3124556677");

        ClienteEntity entidad = new ClienteEntity();
        entidad.setIdcliente(1L);
        entidad.setNombre("Nuevo");
        entidad.setTelefono("3124556677");

        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(entidad);

        ClienteEntity guardado = clienteService.save(dto);

        assertNotNull(guardado);
        assertEquals("Nuevo", guardado.getNombre());
        assertEquals("3124556677", guardado.getTelefono());
    }


    //  TEST EDITAR CLIENTE

    @Test
    void testSaveEditarCliente() {
        ClienteDTO dto = new ClienteDTO(1L, "Editado", "3009001234");
        ClienteEntity existente = new ClienteEntity();
        existente.setIdcliente(1L);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(clienteRepository.save(any())).thenReturn(existente);

        ClienteEntity actualizado = clienteService.save(dto);

        assertEquals("Editado", actualizado.getNombre());
    }


    //  TEST ACTUALIZAR POR ID

    @Test
    void testActualizarPorId() {
        ClienteEntity existente = new ClienteEntity();
        existente.setIdcliente(1L);

        ClienteDTO dto = new ClienteDTO(1L, "Nuevo Nombre", "3001234567");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(clienteRepository.save(any())).thenReturn(existente);

        ClienteEntity resp = clienteService.actualizarPorId(1L, dto);

        assertEquals("Nuevo Nombre", resp.getNombre());
    }


    //  TEST DELETE

    @Test
    void testDelete() {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setIdcliente(1L);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).delete(cliente);

        clienteService.delete(1L);

        verify(clienteRepository, times(1)).delete(cliente);
    }
}
