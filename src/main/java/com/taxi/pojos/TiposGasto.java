package com.taxi.pojos;

import com.taxi.entities.TiposGastosEntity;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

public class TiposGasto  extends DefaultTreeNode implements Serializable, Comparable<TiposGasto> , TreeElement {

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

    @Override
    public TiposGasto getData() {
        return this;
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

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(nombre, that.nombre)) return false;
        if (!Objects.equals(es_operacional, that.es_operacional))
            return false;
        if (!Objects.equals(creado, that.creado)) return false;
        return Objects.equals(actualizado, that.actualizado);
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

    @Override
    public int compareTo(TiposGasto o) {
        return id.compareTo(o.id);
    }

    @Override
    public TreeElement buildTree(TreeNode parent) {
        setParent(parent);

        return this;
    }
}
