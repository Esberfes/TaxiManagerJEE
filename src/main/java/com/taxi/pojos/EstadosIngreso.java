package com.taxi.pojos;

import com.taxi.entities.RecaudacionesIngresosEstadosEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class='node-root'><div><b>")
                .append(getClass().getSimpleName()).append("</b></div>")
                .append("<ul>");

        sb.append(Arrays.stream(getClass().getDeclaredFields()).map(e -> {
            try {
                Object o = e.get(this);
                return o != null? "<li>" + "<b>" + e.getName() + ": &nbsp;<b>" + o.toString() + "</li>": "";
            } catch (IllegalAccessException illegalAccessException) {
                return "<li>" + illegalAccessException.toString() + "</li>";
            }
        }).collect(Collectors.joining("\n")));


        return sb.append("</ul>").append("</div>").toString();
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
