package com.jdc.clinica.models;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoEntityTest {

    private static Validator validator;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // =============================
    // 1. Validación correcta del producto
    // =============================
    @Test
    void testProductoValido() {
        ProductoEntity producto = new ProductoEntity();
        producto.setCodigo("COD-001");
        producto.setNombre("Monitor 24 pulgadas");
        producto.setCaracteristicas("Pantalla IPS 144Hz");
        producto.setPrecio_cop(850000);
        producto.setPrecio_usd(200);
        producto.setPrecio_eur(190);
        producto.setOrdenes(new ArrayList<>());
        producto.setCategorias(new ArrayList<>());
        producto.setEmpresa(new EmpresaEntity());

        Set<ConstraintViolation<ProductoEntity>> errores = validator.validate(producto);

        assertTrue(errores.isEmpty(), "La entidad debe ser válida con todos los campos completos");
    }

    // =============================
    // 2. Validación de campos obligatorios @NotNull
    // =============================
    @Test
    void testCamposObligatorios() {
        ProductoEntity producto = new ProductoEntity(); // sin datos

        Set<ConstraintViolation<ProductoEntity>> errores = validator.validate(producto);

        assertFalse(errores.isEmpty(), "Debe fallar por campos obligatorios faltantes");

        assertTrue(errores.stream().anyMatch(v -> v.getPropertyPath().toString().equals("codigo")));
        assertTrue(errores.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
        assertTrue(errores.stream().anyMatch(v -> v.getPropertyPath().toString().equals("caracteristicas")));
        assertTrue(errores.stream().anyMatch(v -> v.getPropertyPath().toString().equals("precio_cop")));
        assertTrue(errores.stream().anyMatch(v -> v.getPropertyPath().toString().equals("precio_usd")));
        assertTrue(errores.stream().anyMatch(v -> v.getPropertyPath().toString().equals("precio_eur")));
    }

    // =============================
    // 3. Validar método addOrden (relación Bidireccional)
    // =============================
    @Test
    void testAddOrdenProducto() {
        ProductoEntity producto = new ProductoEntity();
        producto.setCodigo("C-01");
        producto.setNombre("Teclado Mecánico");
        producto.setCaracteristicas("RGB switches red");
        producto.setPrecio_cop(150000);
        producto.setPrecio_usd(40);
        producto.setPrecio_eur(38);
        producto.setEmpresa(new EmpresaEntity());
        producto.setOrdenes(new ArrayList<>());

        OrdenProductoEntity orden = new OrdenProductoEntity();

        producto.addOrden(orden);

        assertEquals(1, producto.getOrdenes().size(), "La orden debe agregarse correctamente");
        assertEquals(producto, orden.getProducto(), "La relación debe ser bidireccional");
    }
}
