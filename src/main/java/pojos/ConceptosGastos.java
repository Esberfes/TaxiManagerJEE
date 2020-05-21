package pojos;

import entities.ConceptosGastosEntity;

import java.util.Date;

public class ConceptosGastos {

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
}
