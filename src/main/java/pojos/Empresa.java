package pojos;


import entities.EmpresasEntity;

import java.util.Date;
import java.util.Objects;

public class Empresa {

    private Long id;
    private String nombre;
    private Date creado;
    private Date actualizado;

    public Empresa(String nombre) {
        this.nombre = nombre;
    }

    public Empresa(EmpresasEntity empresasEntity) {
        this.id = empresasEntity.getId();
        this.nombre = empresasEntity.getNombre();
        this.creado = empresasEntity.getCreado();
        this.actualizado = empresasEntity.getActualizado();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa that = (Empresa) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(creado, that.creado) &&
                Objects.equals(actualizado, that.actualizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, creado, actualizado);
    }
}
