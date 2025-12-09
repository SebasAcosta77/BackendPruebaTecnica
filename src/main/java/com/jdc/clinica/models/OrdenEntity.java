package com.jdc.clinica.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "ordenes")
public class OrdenEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idorden")
    private Long idorden;

    @NotNull
    @Column(name = "fecha")
    private LocalDateTime fecha;

    @NotNull
    @Column(name = "total")
    private float total;

    // RELACIÓN N:1 Cliente
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", referencedColumnName = "idcliente", nullable = false)
    @JsonIgnoreProperties({"ordenes"})
    private ClienteEntity cliente;

    // RELACIÓN 1:N producto en orden
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdenProductoEntity> productos = new ArrayList<>();

    public void addProducto(OrdenProductoEntity detalle) {
        productos.add(detalle);
        detalle.setOrden(this);
    }

    public void removeProducto(OrdenProductoEntity detalle) {
        productos.remove(detalle);
        detalle.setOrden(null);
    }


    // Getters y setters
    public Long getIdorden() { return idorden; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public float getTotal() { return total; }
    public void setTotal(float total) { this.total = total; }

    public ClienteEntity getCliente() { return cliente; }
    public void setCliente(ClienteEntity cliente) { this.cliente = cliente; }

    public List<OrdenProductoEntity> getProductos() { return productos; }
    public void setProductos(List<OrdenProductoEntity> productos) { this.productos = productos; }
}
