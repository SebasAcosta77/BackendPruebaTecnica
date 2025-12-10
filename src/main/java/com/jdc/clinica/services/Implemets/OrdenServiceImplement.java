package com.jdc.clinica.services.Implemets;

import com.jdc.clinica.DTO.CategoriaDTO;
import com.jdc.clinica.DTO.CrearOrdenDTO;
import com.jdc.clinica.DTO.OrdenDTO;
import com.jdc.clinica.DTO.OrdenProductoDTO;
import com.jdc.clinica.models.*;
import com.jdc.clinica.repository.*;
import com.jdc.clinica.services.IOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdenServiceImplement implements IOrdenService {

    @Autowired
    private IOrdenRepository ordenRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IOrdenProductoRepository ordenProductoRepository;

    @Autowired
    private IUsuarioRepository usuariosRepository;

    
    // LISTAR 
   
    @Override
    public List<OrdenEntity> findAll() {
        return ordenRepository.findAll();
    }

   
    // BUSCAR POR ID

    @Override
    public OrdenEntity findById(Long id) {
        return ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));
    }

    
    // CREAR
  
    @Override
    @Transactional
    public OrdenEntity save(OrdenDTO dto) {


        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        UsuariosEntity usuario = usuariosRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ClienteEntity cliente = usuario.getCliente();
        if (cliente == null)
            throw new RuntimeException("Este usuario no tiene cliente asociado");

      
        // CREAR
      
        OrdenEntity orden = new OrdenEntity();
        orden.setFecha(dto.getFecha());
        orden.setTotal(dto.getTotal());
        orden.setCliente(cliente);

      
        // PRODUCTOS DE LA ORDEN
     
        for (OrdenProductoDTO item : dto.getProductos()) {

            ProductoEntity producto = productoRepository.findById(item.getFkproducto())
                    .orElseThrow(() ->
                            new RuntimeException("Producto no encontrado con ID: " + item.getFkproducto()));

            OrdenProductoEntity detalle = new OrdenProductoEntity();
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());

            orden.addProducto(detalle);
        }

        
        // GUARDAR 
     
        return ordenRepository.save(orden);
    }



    @Override
    public OrdenEntity actualizarPorId(Long id, OrdenDTO ordenDTO) {
        return null;
    }



 
    // ELIMINAR ORDEN
  
    @Override
    public void delete(Long id) {

        OrdenEntity orden = ordenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe orden con ID: " + id));

        ordenRepository.delete(orden);
    }
}
