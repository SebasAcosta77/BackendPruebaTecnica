package com.jdc.clinica.services.Implemets;

import com.jdc.clinica.DTO.EmpresaDTO;
import com.jdc.clinica.Mapper.EmpresaMapper;
import com.jdc.clinica.models.EmpresaEntity;
import com.jdc.clinica.repository.IEmpresaRepository;
import com.jdc.clinica.repository.IInventarioRepository;
import com.jdc.clinica.repository.IProductoRepository;
import com.jdc.clinica.services.IEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaServiceImplement implements IEmpresaService {

    @Autowired
    private IEmpresaRepository empresaRepository;

    @Autowired
    private IInventarioRepository inventarioRepository;

    @Autowired
    private IProductoRepository productoRepository;


    // =====================================================
    // LISTAR ENTIDADES (DEVUELVE ENTITY)
    // =====================================================
    @Override
    public List<EmpresaEntity> findAll() {
        return empresaRepository.findAll();
    }

    // =====================================================
    // LISTAR DTO (RECOMENDADO PARA RESPUESTAS JSON)
    // =====================================================
    @Override
    public List<EmpresaDTO> listar() {
        return empresaRepository.findAll()
                .stream()
                .map(EmpresaMapper::toDTO)
                .toList();
    }

    // =====================================================
    // BUSCAR POR NIT
    // =====================================================
    @Override
    public EmpresaEntity findById(String nit) {
        return empresaRepository.findById(nit)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada con NIT: " + nit));
    }


    // =====================================================
    // CREAR
    // =====================================================
    @Override
    public EmpresaEntity save(EmpresaDTO dto) {

        if(dto.getNit() == null || dto.getNit().isBlank()){
            throw new RuntimeException("El NIT es obligatorio");
        }

        // Evitar que se cree un registro con un NIT ya existente
        if (empresaRepository.existsById(dto.getNit())) {
            throw new RuntimeException("Ya existe una empresa con este NIT: " + dto.getNit());
        }

        // Crear entidad nueva desde el DTO
        EmpresaEntity entity = EmpresaMapper.toEntity(dto);

        return empresaRepository.save(entity);
    }


    // =====================================================
    // ACTUALIZAR POR NIT — usando DTO
    // =====================================================
    @Override
    public EmpresaEntity actualizarPorId(String nit, EmpresaDTO dto) {

        EmpresaEntity entity = empresaRepository.findById(nit)
                .orElseThrow(() -> new RuntimeException("No existe empresa con NIT: " + nit));

        EmpresaMapper.updateEntity(entity, dto);

        return empresaRepository.save(entity);
    }


    // =====================================================
    // ELIMINAR
    // =====================================================
    @Override
    public void delete(String nit) {

        long conteo = inventarioRepository.countByEmpresa_Nit(nit);

        if (conteo > 0) {
            throw new RuntimeException("No se puede eliminar la empresa porque tiene inventarios. Se recomienda desactivarla.");
        }

        EmpresaEntity empresa = empresaRepository.findById(nit)
                .orElseThrow(() -> new RuntimeException("No existe una empresa con ese NIT."));

        empresaRepository.delete(empresa);
    }


    // =====================================================
    // DESACTIVAR & ACTIVAR EMPRESA
    // =====================================================
    @Override
    public EmpresaEntity desactivar(String nit) {

        EmpresaEntity entity = findById(nit);

        long inventarios = inventarioRepository.countByEmpresa_Nit(nit);
        long productos = productoRepository.countByEmpresa_Nit(nit);

        if(inventarios == 0 && productos == 0) {
            throw new RuntimeException("No tiene inventarios ni productos, puedes eliminarla en lugar de desactivarla.");
        }

        entity.setEstado("INACTIVO");
        return empresaRepository.save(entity);
    }

    @Override
    public EmpresaEntity activar(String nit) {

        EmpresaEntity entity = findById(nit);
        entity.setEstado("ACTIVO");

        return empresaRepository.save(entity);
    }

    @Override
    public boolean eliminarSiNoTieneDependencias(String nit) {
        EmpresaEntity empresa = empresaRepository.findById(nit)
                .orElseThrow(() -> new RuntimeException("No existe empresa con NIT: " + nit));

        long inventarios = inventarioRepository.countByEmpresa_Nit(nit);
        long productos = productoRepository.countByEmpresa_Nit(nit);

        // Si tiene dependencias NO se elimina
        if (inventarios > 0 || productos > 0) {
            empresa.setEstado("INACTIVO");
            empresaRepository.save(empresa);
            return false;
        }

        // Si no tiene dependencias → se elimina normal
        empresaRepository.deleteById(nit);
        return true;
    }

}
