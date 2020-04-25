package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "recaudacion_ingresos")
public class RecaudacionIngresosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "dia")
    private Integer dia;

    @Column(name = "turno")
    private String turno;

    @Column(name = "numeracion")
    private BigDecimal numeracion;

    @Column(name = "anulados")
    private BigDecimal anulados;

    @Column(name = "recaudacion")
    private BigDecimal recaudacion;

    @Column(name = "observaciones")
    private String observaciones;

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

    @PreUpdate
    public void preUpdate() {
        actualizado = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecaudacionIngresosEntity that = (RecaudacionIngresosEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(dia, that.dia) &&
                Objects.equals(turno, that.turno) &&
                Objects.equals(numeracion, that.numeracion) &&
                Objects.equals(anulados, that.anulados) &&
                Objects.equals(recaudacion, that.recaudacion) &&
                Objects.equals(observaciones, that.observaciones) &&
                Objects.equals(creado, that.creado) &&
                Objects.equals(actualizado, that.actualizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dia, turno, numeracion, anulados, recaudacion, observaciones, creado, actualizado);
    }
}
