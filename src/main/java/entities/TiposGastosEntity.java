package entities;

import pojos.TiposGasto;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "tipos_gastos")
public class TiposGastosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "nombre", unique = true)
    private String nombre;

    @Column(name = "es_operacional")
    private Boolean esOperacional;

    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;

    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;

    @PrePersist
    public void prePersist() {
        creado = new Date();
        actualizado = new Date();
    }

    public TiposGastosEntity() {}

    public TiposGastosEntity(TiposGasto tiposGasto) {
        this.id = tiposGasto.getId();
        this.nombre = tiposGasto.getNombre();
        this.esOperacional = tiposGasto.getEs_operacional();
        this.creado = tiposGasto.getCreado();
        this.actualizado = tiposGasto.getActualizado();
    }

    @PreUpdate
    public void preUpdate() {
        actualizado = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TiposGastosEntity that = (TiposGastosEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(esOperacional, that.esOperacional) &&
                Objects.equals(creado, that.creado) &&
                Objects.equals(actualizado, that.actualizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, esOperacional, creado, actualizado);
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

    public Boolean getEsOperacional() {
        return esOperacional;
    }

    public void setEsOperacional(Boolean esOperacional) {
        this.esOperacional = esOperacional;
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
