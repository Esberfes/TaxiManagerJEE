package pojos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class IncomesSummary {

    private Map<License, BigDecimal> licenseIncomes;
    private BigDecimal total;
    private Date start;
    private Date end;

    public IncomesSummary(Map<License, BigDecimal> licenseIncomes, BigDecimal total, Date start, Date end) {
        this.licenseIncomes = licenseIncomes;
        this.total = total;
        this.start = start;
        this.end = end;
    }

    public Map<License, BigDecimal> getLicenseIncomes() {
        return licenseIncomes;
    }

    public void setLicenseIncomes(Map<License, BigDecimal> licenseIncomes) {
        this.licenseIncomes = licenseIncomes;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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
}
