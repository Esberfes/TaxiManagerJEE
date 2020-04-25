package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "licencias")
public class LicenciasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "esEurotaxi")
    private Boolean esEurotaxi;

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
        LicenciasEntity that = (LicenciasEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(codigo, that.codigo) &&
                Objects.equals(esEurotaxi, that.esEurotaxi) &&
                Objects.equals(creado, that.creado) &&
                Objects.equals(actualizado, that.actualizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo, esEurotaxi, creado, actualizado);
    }
}
