package com.jdc.clinica.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "producto_categoria")
public class ProductoCategoriaEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_productocategoria")
    private Long id;

    // ============================
    // FK PRODUCTO
    // ============================

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", referencedColumnName = "idproducto", nullable = false)
    @JsonIgnore
    private ProductoEntity producto;

    @JsonProperty("producto_id")
    public Long getProductoId() {
        return (producto != null) ? producto.getIdproducto() : null;
    }

    // ============================
    // FK CATEGORIA
    // ============================

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", referencedColumnName = "idcategoria", nullable = false)
    @JsonIgnore
    private CategoriaEntity categoria;

    @JsonProperty("categoria_id")
    public Long getCategoriaId() {
        return (categoria != null) ? categoria.getIdcategoria() : null;
    }

    // ============================
    // GETTERS & SETTERS
    // ============================

    public Long getId() {
        return id;
    }

    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
    }

    public CategoriaEntity getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
    }
}
