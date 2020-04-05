package pojos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class IncomesSummary {

    Map<String, BigDecimal> dateIncomes;
    private Map<License, BigDecimal> licenseIncomes;
    private BigDecimal total;
    private Date start;
    private Date end;

    public IncomesSummary(Map<String, BigDecimal> dateIncomes, Map<License, BigDecimal> licenseIncomes, BigDecimal total, Date start, Date end) {
        this.dateIncomes = dateIncomes;
        this.licenseIncomes = licenseIncomes;
        this.total = total;
        this.start = start;
        this.end = end;
    }

    public Map<String, BigDecimal> getDateIncomes() {
        return dateIncomes;
    }

    public void setDateIncomes(Map<String, BigDecimal> dateIncomes) {
        this.dateIncomes = dateIncomes;
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
