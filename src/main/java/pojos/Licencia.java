package pojos;

import entities.LicenciasEntity;

import java.util.Date;

public class Licencia {

    private Long id;
    private Empresa empresa;
    private Integer codigo;
    private Boolean es_eurotaxi;
    private Date creado;
    private Date actualizado;

    public Licencia() {
    }

    public Licencia(LicenciasEntity licenciasEntity) {
        this.id = licenciasEntity.getId();
        this.empresa = new Empresa(licenciasEntity.getEmpresasEntity());
        this.codigo = licenciasEntity.getCodigo();
        this.es_eurotaxi = licenciasEntity.getEsEurotaxi();
        this.creado = licenciasEntity.getCreado();
        this.actualizado = licenciasEntity.getCreado();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Boolean getEs_eurotaxi() {
        return es_eurotaxi;
    }

    public void setEs_eurotaxi(Boolean es_eurotaxi) {
        this.es_eurotaxi = es_eurotaxi;
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
