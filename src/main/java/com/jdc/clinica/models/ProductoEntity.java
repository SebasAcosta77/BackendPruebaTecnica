package com.jdc.clinica.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name ="productos")
public class ProductoEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproducto")
    private Long idproducto;

    @NotNull
    @Column(name = "codigo", length = 50)
    private String codigo;

    @NotNull
    @Column(name = "nombre", length = 50)
    private String nombre;

    @NotNull
    @Column(name = "caracteristicas", length = 100)
    private String caracteristicas;

    @NotNull
    @Column(name = "preciocop")
    private float precio_cop;

    @NotNull
    @Column(name = "preciousd")
    private float precio_usd;

    @NotNull
    @Column(name = "precioeur")
    private float precio_eur;

    @JsonIgnore
    @OneToMany(mappedBy = "producto")
    private List<OrdenProductoEntity> ordenes;



    // =======================
    // RELACIÓN N:1 EMPRESA
    // =======================
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_nit", referencedColumnName = "nit")
    private EmpresaEntity empresa;

    public EmpresaEntity getEmpresa() { return empresa; }
    public void setEmpresa(EmpresaEntity empresa) { this.empresa = empresa; }


    // RELACIÓN N:N CON CATEGORÍA

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ProductoCategoriaEntity> categorias;

    public void addOrden(OrdenProductoEntity detalle) {
        ordenes.add(detalle);
        detalle.setProducto(this);
    }

    public ProductoEntity() {
    }

    public ProductoEntity(Long idproducto, String codigo, String nombre, String caracteristicas, float precio_cop, float precio_usd, float precio_eur, List<OrdenProductoEntity> ordenes, EmpresaEntity empresa, List<ProductoCategoriaEntity> categorias) {
        this.idproducto = idproducto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.caracteristicas = caracteristicas;
        this.precio_cop = precio_cop;
        this.precio_usd = precio_usd;
        this.precio_eur = precio_eur;
        this.ordenes = ordenes;
        this.empresa = empresa;
        this.categorias = categorias;
    }

    public void setIdproducto(Long idproducto) {
        this.idproducto = idproducto;
    }

    public void setOrdenes(List<OrdenProductoEntity> ordenes) {
        this.ordenes = ordenes;
    }

    public List<ProductoCategoriaEntity> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<ProductoCategoriaEntity> categorias) {
        this.categorias = categorias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getters y setters
    public Long getIdproducto() { return idproducto; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getCaracteristicas() { return caracteristicas; }
    public void setCaracteristicas(String caracteristicas) { this.caracteristicas = caracteristicas; }

    public float getPrecio_cop() { return precio_cop; }
    public void setPrecio_cop(float precio_cop) { this.precio_cop = precio_cop; }

    public float getPrecio_usd() { return precio_usd; }
    public void setPrecio_usd(float precio_usd) { this.precio_usd = precio_usd; }

    public float getPrecio_eur() { return precio_eur; }
    public void setPrecio_eur(float precio_eur) { this.precio_eur = precio_eur; }

    public List<OrdenProductoEntity> getOrdenes() { return ordenes; }
}
