package pojos;

import java.io.Serializable;
import java.util.Objects;

public class Income implements Serializable {

    private Long id;
    private Double amount;
    private WorkShift workShift;

    public Income() {
    }

    public Income(Long id, Double amount, WorkShift workShift) {
        this.id = id;
        this.amount = amount;
        this.workShift = workShift;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public WorkShift getWorkShift() {
        return workShift;
    }

    public void setWorkShift(WorkShift workShift) {
        this.workShift = workShift;
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

        final Income other = (Income) obj;

        return Objects.equals(this.id, other.id);
    }
}
