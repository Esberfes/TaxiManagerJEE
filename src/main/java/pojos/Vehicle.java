package pojos;

import entities.VehicleEntity;

import java.util.Date;

public class Vehicle {

    private Long id;
    private String brand;
    private String registration;
    private License license;
    private Date createdAt;
    private Date updatedAt;

    public Vehicle() {

    }

    public Vehicle(String brand, String registration, License license) {
        this.brand = brand;
        this.registration = registration;
        this.license = license;
    }

    public Vehicle(VehicleEntity vehicleEntity) {
        this.id = vehicleEntity.getId();
        this.brand = vehicleEntity.getBrand();
        this.registration = vehicleEntity.getRegistration();
        this.license = vehicleEntity.getLicense() != null ? new License(vehicleEntity.getLicense()) : new License();
        this.createdAt = vehicleEntity.getCreatedAt();
        this.updatedAt = vehicleEntity.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
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
