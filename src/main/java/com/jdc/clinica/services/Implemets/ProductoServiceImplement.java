package com.jdc.clinica.services.Implemets;

import com.jdc.clinica.DTO.ProductoDTO;
import com.jdc.clinica.Mapper.ProductoMapper;
import com.jdc.clinica.models.CategoriaEntity;
import com.jdc.clinica.models.EmpresaEntity;
import com.jdc.clinica.models.ProductoCategoriaEntity;
import com.jdc.clinica.models.ProductoEntity;
import com.jdc.clinica.repository.ICategoriaRepository;
import com.jdc.clinica.repository.IEmpresaRepository;
import com.jdc.clinica.repository.IProductoRepository;
import com.jdc.clinica.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImplement implements IProductoService {

    @Autowired
    private IProductoRepository productoRepository;
    @Autowired
    private IEmpresaRepository empresaRepository;
    @Autowired
    private ICategoriaRepository categoriaRepository;


    // LISTAR

    @Override
    @Transactional
    public List<ProductoEntity> findAll() {
        return productoRepository.findAll();
    }

    @Override
    @Transactional
    public List<ProductoDTO> listar() {
        return productoRepository.findAll()
                .stream()
                .map(ProductoMapper::toDTO)
                .toList();
    }


    // BUSCAR POR ID

    @Override
    @Transactional
    public ProductoEntity findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }


    // CREAR

    @Override
    @Transactional
    public ProductoDTO save(ProductoDTO dto) {


        EmpresaEntity empresa = empresaRepository.findById(dto.getEmpresaNit())
                .orElseThrow(() -> new RuntimeException("La empresa con NIT " + dto.getEmpresaNit() + " no existe"));


        if (productoRepository.existsByCodigo(dto.getCodigo())) {
            throw new RuntimeException("Ya existe un producto con el código: " + dto.getCodigo());
        }

        ProductoEntity entity = new ProductoEntity();
        entity.setCodigo(dto.getCodigo());
        entity.setNombre(dto.getNombre());
        entity.setCaracteristicas(dto.getCaracteristicas());
        entity.setPrecio_cop(dto.getPrecioCop());
        entity.setPrecio_usd(dto.getPrecioUsd());
        entity.setPrecio_eur(dto.getPrecioEur());
        entity.setEmpresa(empresa);


        if (dto.getCategoriasIds() != null && !dto.getCategoriasIds().isEmpty()) {

            List<ProductoCategoriaEntity> relaciones = dto.getCategoriasIds().stream()
                    .map(id -> {
                        CategoriaEntity categoria = categoriaRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("La categoría con ID " + id + " no existe"));

                        ProductoCategoriaEntity pc = new ProductoCategoriaEntity();
                        pc.setProducto(entity);
                        pc.setCategoria(categoria);
                        return pc;
                    }).collect(Collectors.toList());

            entity.setCategorias(relaciones);
        }


        ProductoEntity guardado = productoRepository.save(entity);

        return ProductoMapper.toDTO(guardado);
    }


    @Override
    @Transactional
    public ProductoEntity actualizarPorId(Long id, ProductoDTO dto) {


        ProductoEntity entity = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe producto con ID: " + id));


        entity.setCodigo(dto.getCodigo());
        entity.setNombre(dto.getNombre());
        entity.setCaracteristicas(dto.getCaracteristicas());
        entity.setPrecio_cop(dto.getPrecioCop());
        entity.setPrecio_usd(dto.getPrecioUsd());
        entity.setPrecio_eur(dto.getPrecioEur());


        if (dto.getEmpresaNit() != null) {
            EmpresaEntity empresa = empresaRepository.findById(dto.getEmpresaNit())
                    .orElseThrow(() -> new RuntimeException(
                            "La empresa con NIT " + dto.getEmpresaNit() + " no existe"));

            entity.setEmpresa(empresa);
        }

        return productoRepository.save(entity);
    }




    @Override
    @Transactional
    public void delete(Long id) {
        ProductoEntity entity = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe producto con ID: " + id));

        productoRepository.delete(entity);
    }
}
