package com.taxi.pojos;

import com.taxi.entities.TiposGastosEntity;

import java.util.Date;

public class TiposGasto {

    private Long id;
    private String nombre;
    private Boolean es_operacional;
    private Date creado;
    private Date actualizado;

    public TiposGasto(TiposGastosEntity tiposGastosEntity) {
        this.id = tiposGastosEntity.getId();
        this.nombre = tiposGastosEntity.getNombre();
        this.es_operacional = tiposGastosEntity.getEsOperacional();
        this.creado = tiposGastosEntity.getCreado();
        this.actualizado = tiposGastosEntity.getActualizado();
    }

    public TiposGasto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEs_operacional() {
        return es_operacional;
    }

    public void setEs_operacional(Boolean es_operacional) {
        this.es_operacional = es_operacional;
    }

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Date getActualizado() {
        return actualizado;
    }

    public void setActualizado(Date actualizado) {
        this.actualizado = actualizado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TiposGasto that = (TiposGasto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (es_operacional != null ? !es_operacional.equals(that.es_operacional) : that.es_operacional != null)
            return false;
        if (creado != null ? !creado.equals(that.creado) : that.creado != null) return false;
        return actualizado != null ? actualizado.equals(that.actualizado) : that.actualizado == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (es_operacional != null ? es_operacional.hashCode() : 0);
        result = 31 * result + (creado != null ? creado.hashCode() : 0);
        result = 31 * result + (actualizado != null ? actualizado.hashCode() : 0);
        return result;
    }
}
