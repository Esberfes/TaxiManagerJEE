package pojos;

import entities.LicenseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class License implements Serializable {

    private Long id;
    private String code;
    private Date createdAt;
    private Date updatedAt;

    public License() {
    }

    public License(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    public License(String code) {
        this.code = code;
    }

    public License(LicenseEntity licenseEntity) {
        this.id = licenseEntity.getId();
        this.code = licenseEntity.getCode();
        this.createdAt = licenseEntity.getCreatedAt();
        this.updatedAt = licenseEntity.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

        final License other = (License) obj;

        return Objects.equals(this.id, other.id);
    }
}
