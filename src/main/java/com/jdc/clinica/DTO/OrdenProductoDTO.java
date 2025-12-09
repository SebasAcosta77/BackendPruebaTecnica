package com.jdc.clinica.DTO;

public class OrdenProductoDTO {

    private Long idordenproducto;
    private Long fkorden;
    private Long fkproducto;
    private int cantidad;


    public Long getIdordenproducto() {
        return idordenproducto;
    }

    public void setIdordenproducto(Long idordenproducto) {
        this.idordenproducto = idordenproducto;
    }

    public Long getFkorden() {
        return fkorden;
    }

    public void setFkorden(Long fkorden) {
        this.fkorden = fkorden;
    }

    public Long getFkproducto() {
        return fkproducto;
    }

    public void setFkproducto(Long fkproducto) {
        this.fkproducto = fkproducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
