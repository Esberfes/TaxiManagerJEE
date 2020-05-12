package pojos;

import entities.TiposGastosEntity;

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
}
