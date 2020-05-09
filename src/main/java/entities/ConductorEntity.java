package entities;

import pojos.Conductor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "conductores")
public class ConductorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_empresa", referencedColumnName = "id")
    private EmpresasEntity empresasEntity;

    @Column(name = "complemento_iva")
    private BigDecimal complementoIva;

    @Column(name = "t065")
    private BigDecimal t065;

    @Column(name = "t060")
    private BigDecimal t060;

    @Column(name = "t055")
    private BigDecimal t055;

    @Column(name = "t050")
    private BigDecimal t050;

    @Column(name = "creado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;

    @Column(name = "actualizado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizado;


    public ConductorEntity() {
    }

    public ConductorEntity(Conductor conductor) {
        this.id = conductor.getId();
        this.nombre = conductor.getNombre();
        this.empresasEntity = new EmpresasEntity(conductor.getEmpresa());
        this.complementoIva = conductor.getComplemento_iva();
        this.t065 = conductor.getT065();
        this.t060 = conductor.getT060();
        this.t055 = conductor.getT055();
        this.t050 = conductor.getT050();
        this.creado = conductor.getCreado();
        this.actualizado = conductor.getActualizado();
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
        ConductorEntity that = (ConductorEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(empresasEntity, that.empresasEntity) &&
                Objects.equals(complementoIva, that.complementoIva) &&
                Objects.equals(t065, that.t065) &&
                Objects.equals(t060, that.t060) &&
                Objects.equals(t055, that.t055) &&
                Objects.equals(t050, that.t050) &&
                Objects.equals(creado, that.creado) &&
                Objects.equals(actualizado, that.actualizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, empresasEntity, complementoIva, t065, t060, t055, t050, creado, actualizado);
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

    public EmpresasEntity getEmpresasEntity() {
        return empresasEntity;
    }

    public void setEmpresasEntity(EmpresasEntity empresasEntity) {
        this.empresasEntity = empresasEntity;
    }

    public BigDecimal getComplementoIva() {
        return complementoIva;
    }

    public void setComplementoIva(BigDecimal complementoIva) {
        this.complementoIva = complementoIva;
    }

    public BigDecimal getT065() {
        return t065;
    }

    public void setT065(BigDecimal t065) {
        this.t065 = t065;
    }

    public BigDecimal getT060() {
        return t060;
    }

    public void setT060(BigDecimal t060) {
        this.t060 = t060;
    }

    public BigDecimal getT055() {
        return t055;
    }

    public void setT055(BigDecimal t055) {
        this.t055 = t055;
    }

    public BigDecimal getT050() {
        return t050;
    }

    public void setT050(BigDecimal t050) {
        this.t050 = t050;
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
