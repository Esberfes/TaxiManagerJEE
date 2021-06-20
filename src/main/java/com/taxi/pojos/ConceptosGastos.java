package com.taxi.pojos;

import com.taxi.entities.ConceptosGastosEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class ConceptosGastos  {

    private Long id;
    private String nombre;
    private TiposGasto tipoGasto;
    private Date creado;
    private Date actualizado;

    public ConceptosGastos(ConceptosGastosEntity conceptosGastosEntity) {
        this.id = conceptosGastosEntity.getId();
        this.nombre = conceptosGastosEntity.getNombre();
        this.tipoGasto = new TiposGasto(conceptosGastosEntity.getTiposGastosEntity());
        this.creado = conceptosGastosEntity.getCreado();
        this.actualizado = conceptosGastosEntity.getActualizado();
    }

    public ConceptosGastos() {

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

    public TiposGasto getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(TiposGasto tipoGasto) {
        this.tipoGasto = tipoGasto;
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
}
