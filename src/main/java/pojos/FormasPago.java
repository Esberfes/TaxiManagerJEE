package pojos;

import entities.FormasPagosGastosEntity;

import java.util.Date;

public class FormasPago {

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
}
