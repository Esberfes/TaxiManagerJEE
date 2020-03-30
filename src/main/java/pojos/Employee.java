package pojos;

import entities.EmployeeEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Employee implements Serializable {

    private Long id;
    private String name;
    private String firstLastName;
    private String secondLastName;
    private String dni;
    private Date createdAt;
    private Date updatedAt;

    public Employee() {
    }

    public Employee(String name, String firstLastName, String secondLastName, String dni) {
        this.name = name;
        this.firstLastName = firstLastName;
        this.secondLastName = secondLastName;
        this.dni = dni;
    }

    public Employee(EmployeeEntity employeeEntity) {
        this.id = employeeEntity.getId();
        this.name = employeeEntity.getName();
        this.firstLastName = employeeEntity.getFirstLastName();
        this.secondLastName = employeeEntity.getSecondLastName();
        this.dni = employeeEntity.getDni();
        this.createdAt = employeeEntity.getCreatedAt();
        this.updatedAt = employeeEntity.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        final Employee other = (Employee) obj;

        return Objects.equals(this.id, other.id);
    }
}
