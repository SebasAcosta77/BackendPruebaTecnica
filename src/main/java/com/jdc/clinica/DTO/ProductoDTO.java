package com.jdc.clinica.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;
/**
 * DTO para transferencia de datos del Producto.
 * Incluye validaciones y estructura limpia según buenas prácticas.
 */

public class ProductoDTO {

    private Long idproducto;

    @NotBlank(message = "El código del producto es obligatorio")
    private String codigo;
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;
    @NotBlank(message = "Las características del producto son obligatorias")
    private String caracteristicas;
    @PositiveOrZero(message = "El precio en COP no puede ser negativo")
    private float precioCop;
    @PositiveOrZero(message = "El precio en USD no puede ser negativo")
    private float precioUsd;
    @PositiveOrZero(message = "El precio en EUR no puede ser negativo")
    private float precioEur;

    // FK Empresa
    @NotBlank(message = "El NIT de la empresa es obligatorio")
    private String empresaNit;

    // IDs de categorías asociadas
    private List<Long> categoriasIds;


    // Constructor vacío
    public ProductoDTO() {
    }

    public ProductoDTO(Long idproducto, String codigo, String nombre, String caracteristicas, float precioCop, float precioUsd, float precioEur, String empresaNit, List<Long> categoriasIds) {
        this.idproducto = idproducto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.caracteristicas = caracteristicas;
        this.precioCop = precioCop;
        this.precioUsd = precioUsd;
        this.precioEur = precioEur;
        this.empresaNit = empresaNit;
        this.categoriasIds = categoriasIds;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // ============================
    // GETTERS & SETTERS
    // ============================
    public Long getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Long idproducto) {
        this.idproducto = idproducto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public float getPrecioCop() {
        return precioCop;
    }

    public void setPrecioCop(float precioCop) {
        this.precioCop = precioCop;
    }

    public float getPrecioUsd() {
        return precioUsd;
    }

    public void setPrecioUsd(float precioUsd) {
        this.precioUsd = precioUsd;
    }

    public float getPrecioEur() {
        return precioEur;
    }

    public void setPrecioEur(float precioEur) {
        this.precioEur = precioEur;
    }

    public String getEmpresaNit() {
        return empresaNit;
    }

    public void setEmpresaNit(String empresaNit) {
        this.empresaNit = empresaNit;
    }

    public List<Long> getCategoriasIds() {
        return categoriasIds;
    }

    public void setCategoriasIds(List<Long> categoriasIds) {
        this.categoriasIds = categoriasIds;
    }
}
