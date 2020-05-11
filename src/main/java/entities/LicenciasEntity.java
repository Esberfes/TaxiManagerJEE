package entities;

import pojos.Licencia;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "licencias")
public class LicenciasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_empresa", referencedColumnName = "id")
    private EmpresasEntity empresasEntity;

    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "es_eurotaxi")
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

    public LicenciasEntity() {
    }

    public LicenciasEntity(Licencia licencia) {
        this.id = licencia.getId();
        this.empresasEntity = new EmpresasEntity(licencia.getEmpresa());
        this.codigo = licencia.getCodigo();
        this.esEurotaxi = licencia.getEs_eurotaxi();
        this.creado = licencia.getCreado();
        this.actualizado = licencia.getActualizado();
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmpresasEntity getEmpresasEntity() {
        return empresasEntity;
    }

    public void setEmpresasEntity(EmpresasEntity empresasEntity) {
        this.empresasEntity = empresasEntity;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Boolean getEsEurotaxi() {
        return esEurotaxi;
    }

    public void setEsEurotaxi(Boolean esEurotaxi) {
        this.esEurotaxi = esEurotaxi;
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
