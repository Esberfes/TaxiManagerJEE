package com.taxi.pojos;


import com.taxi.entities.EmpresasEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

public class Empresa {

    private Long id;
    private String nombre;
    private Date creado;
    private Date actualizado;

    public Empresa(String nombre) {
        this.nombre = nombre;
    }

    public Empresa(EmpresasEntity empresasEntity) {
        this.id = empresasEntity.getId();
        this.nombre = empresasEntity.getNombre();
        this.creado = empresasEntity.getCreado();
        this.actualizado = empresasEntity.getActualizado();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa that = (Empresa) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(creado, that.creado) &&
                Objects.equals(actualizado, that.actualizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, creado, actualizado);
    }
}
