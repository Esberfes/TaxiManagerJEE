package com.taxi.pojos;

import com.taxi.entities.RecaudacionesIngresosEstadosEntity;

import java.util.Date;

public class EstadosIngreso {

    private Long id;
    private String nombre;
    private Date creado;
    private Date actualizado;

    public EstadosIngreso(RecaudacionesIngresosEstadosEntity estadosEntity) {
        this.id = estadosEntity.getId();
        this.nombre = estadosEntity.getNombre();
        this.creado = estadosEntity.getCreado();
        this.actualizado = estadosEntity.getActualizado();
    }

    public EstadosIngreso(String nombre) {
        this.nombre = nombre;
    }

    public EstadosIngreso() {

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

        EstadosIngreso that = (EstadosIngreso) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
