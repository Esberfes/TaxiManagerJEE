package pojos;

import entities.WorkShiftEntity;
import enums.ShiftType;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class WorkShift implements Serializable {

    private Long id;
    private ShiftType shiftType;
    private Date day;
    private Employee employee;
    private License license;
    private double income;
    private Date createdAt;
    private Date updatedAt;

    public WorkShift() {

    }

    public WorkShift(Long id, ShiftType shiftType, Date day, Employee employee, License license, double income) {
        this.id = id;
        this.shiftType = shiftType;
        this.day = day;
        this.employee = employee;
        this.license = license;
        this.income = income;
    }

    public WorkShift(WorkShiftEntity workShiftEntity) {
        this.id = workShiftEntity.getId();
        this.shiftType = workShiftEntity.getShiftType();
        this.day = workShiftEntity.getDay();
        this.employee = new Employee(workShiftEntity.getEmployee());
        this.license = new License(workShiftEntity.getLicense());
        this.income = workShiftEntity.getIncome();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }

    public void setShiftType(ShiftType shiftType) {
        this.shiftType = shiftType;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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

        final WorkShift other = (WorkShift) obj;

        return Objects.equals(this.id, other.id);
    }
}
