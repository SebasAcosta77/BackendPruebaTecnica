package com.jdc.clinica.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;

@Entity
@Table(name ="orden_producto")
public class OrdenProductoEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idordenproducto")
    private Long idordenproducto;

    // ========== ORDEN ==========
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fkorden", referencedColumnName = "idorden", nullable = false)
    @JsonIgnore
    private OrdenEntity orden;

    @JsonProperty("fkorden")
    public Long getIdOrden() {
        return orden != null ? orden.getIdorden() : null;
    }

    // ========== PRODUCTO ==========
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fkproducto", referencedColumnName = "idproducto", nullable = false)
    @JsonIgnore
    private ProductoEntity producto;

    @JsonProperty("fkproducto")
    public Long getIdProducto() {
        return producto != null ? producto.getIdproducto() : null;
    }

    // Cantidad
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;

    // Getters y Setters
    public Long getIdordenproducto() { return idordenproducto; }

    public OrdenEntity getOrden() { return orden; }
    public void setOrden(OrdenEntity orden) { this.orden = orden; }

    public ProductoEntity getProducto() { return producto; }
    public void setProducto(ProductoEntity producto) { this.producto = producto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
