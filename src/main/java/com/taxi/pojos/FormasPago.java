package com.taxi.pojos;

import com.taxi.entities.FormasPagosGastosEntity;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class FormasPago extends DefaultTreeNode implements Serializable, Comparable<FormasPago>, TreeElement  {

    private Long id;
    private String nombre;
    private Date creado;
    private Date actualizado;

    public FormasPago() {
    }

    public FormasPago(FormasPagosGastosEntity formasPagosGastosEntity) {
        this.id = formasPagosGastosEntity.getId();
        this.nombre = formasPagosGastosEntity.getNombre();
        this.creado = formasPagosGastosEntity.getCreado();
        this.actualizado = formasPagosGastosEntity.getActualizado();
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
    public FormasPago getData() {
        return this;
    }

    public FormasPago(String nombre) {
        this.nombre = nombre;
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
    public int compareTo(FormasPago o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public TreeElement buildTree(TreeNode parent) {
        setParent(parent);
        return this;
    }
}
