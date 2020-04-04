package faces;

import business.WorkShiftsBean;
import pojos.IncomesSummary;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@ViewScoped
@Named(IncomesSummaryFace.BEAN_NAME)
public class IncomesSummaryFace implements Serializable {

    public static final String BEAN_NAME = "IncomesSummaryFace";

    @Inject
    private WorkShiftsBean workShiftsBean;

    private Date start;
    private Date end;

    private IncomesSummary incomesSummary;

    @PostConstruct
    public void init() {
        end = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        start = calendar.getTime();
        generateSummary();
    }

    public void generateSummary() {
        incomesSummary = workShiftsBean.generateSummary(start, end);
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

    public IncomesSummary getIncomesSummary() {
        return incomesSummary;
    }

    public void setIncomesSummary(IncomesSummary incomesSummary) {
        this.incomesSummary = incomesSummary;
    }
}
