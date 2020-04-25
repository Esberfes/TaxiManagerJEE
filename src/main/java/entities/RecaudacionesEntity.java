package entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "recaudaciones")
public class RecaudacionesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "servicios_inicio")
    private Integer serviciosInicio;

    @Column(name = "servicions_fin")
    private Integer servicionsFin;

    @Column(name = "numeracion_inicio")
    private BigDecimal numeracionInicio;

    @Column(name = "numeracion_fin")
    private BigDecimal numeracionFin;

    @Column(name = "km_totales_inicio")
    private Integer kmTotalesInicio;

    @Column(name = "km_totales_fin")
    private Integer kmTotalesFin;

    @Column(name = "km_cargado_inicio")
    private Integer kmCargadoInicio;

    @Column(name = "km_cargado_fin")
    private Integer kmCargadoFin;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "ano")
    private Integer ano;

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
        RecaudacionesEntity that = (RecaudacionesEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(serviciosInicio, that.serviciosInicio) &&
                Objects.equals(servicionsFin, that.servicionsFin) &&
                Objects.equals(numeracionInicio, that.numeracionInicio) &&
                Objects.equals(numeracionFin, that.numeracionFin) &&
                Objects.equals(kmTotalesInicio, that.kmTotalesInicio) &&
                Objects.equals(kmTotalesFin, that.kmTotalesFin) &&
                Objects.equals(kmCargadoInicio, that.kmCargadoInicio) &&
                Objects.equals(kmCargadoFin, that.kmCargadoFin) &&
                Objects.equals(mes, that.mes) &&
                Objects.equals(ano, that.ano) &&
                Objects.equals(creado, that.creado) &&
                Objects.equals(actualizado, that.actualizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serviciosInicio, servicionsFin, numeracionInicio, numeracionFin, kmTotalesInicio, kmTotalesFin, kmCargadoInicio, kmCargadoFin, mes, ano, creado, actualizado);
    }
}
