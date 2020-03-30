package pojos;

import enums.ShiftType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class WorkShift implements Serializable {

    private Long id;
    private ShiftType shiftType;
    private Date start;
    private Date end;
    private Employee employee;
    private License license;
    private List<Income> incomes;
    private Date createdAt;
    private Date updatedAt;

    public WorkShift() {
        incomes = new ArrayList<>();
    }

    public WorkShift(Long id, ShiftType shiftType, Date start, Date end, Employee employee, License license, List<Income> incomes) {
        this.id = id;
        this.shiftType = shiftType;
        this.start = start;
        this.end = end;
        this.employee = employee;
        this.license = license;
        this.incomes = incomes;
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
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

    public List<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

    public void addIncome(Income income) {
        this.incomes.add(income);
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
