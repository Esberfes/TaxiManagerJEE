package entities;

import enums.ShiftType;
import pojos.WorkShift;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "workshifts")
public class WorkShiftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "shiftType")
    private ShiftType shiftType;

    @Column(name = "day")
    @Temporal(TemporalType.DATE)
    private Date day;

    @JoinColumn(name = "employee", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private EmployeeEntity employee;

    @JoinColumn(name = "license", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private LicenseEntity license;
    @Column(name = "income")
    private Double income;

    @Column(name = "createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public WorkShiftEntity() {
    }

    public WorkShiftEntity(WorkShift workShift) {
        this.shiftType = workShift.getShiftType();
        this.day = workShift.getDay();
        this.income = workShift.getIncome();
    }

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
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

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    public LicenseEntity getLicense() {
        return license;
    }

    public void setLicense(LicenseEntity license) {
        this.license = license;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
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
}
