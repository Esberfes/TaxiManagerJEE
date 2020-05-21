package entities;

import pojos.ConceptosGastos;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "conceptos_gastos")
public class ConceptosGastosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "nombre", unique = true)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_tipo_gasto", referencedColumnName = "id")
    private TiposGastosEntity tiposGastosEntity;

    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;

    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;

    public ConceptosGastosEntity() {
    }

    public ConceptosGastosEntity(ConceptosGastos conceptosGastos) {
        this.id = conceptosGastos.getId();
        this.nombre = conceptosGastos.getNombre();
        this.tiposGastosEntity = new TiposGastosEntity(conceptosGastos.getTipoGasto());
    }


    @PrePersist
    public void prePersist() {
        creado = new Date();
        actualizado = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        actualizado = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConceptosGastosEntity that = (ConceptosGastosEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(creado, that.creado) &&
                Objects.equals(actualizado, that.actualizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, creado, actualizado);
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

    public TiposGastosEntity getTiposGastosEntity() {
        return tiposGastosEntity;
    }

    public void setTiposGastosEntity(TiposGastosEntity tiposGastosEntity) {
        this.tiposGastosEntity = tiposGastosEntity;
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
