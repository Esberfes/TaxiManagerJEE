package faces.old;

import java.io.Serializable;


public class IncomesSummaryFace implements Serializable {
/*
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
        PrimeFaces.current().ajax().update("chart");
    }

    public String getChartData() {
        List<List<Object>> data = new ArrayList<>();
        List<Object> headers = Arrays.asList("Año y mes", "Recaudación");
        data.add(headers);

        if (incomesSummary != null) {
            for (Map.Entry<String, BigDecimal> entry : incomesSummary.getDateIncomes().entrySet()) {
                List<Object> row = Arrays.asList(entry.getKey(), entry.getValue());
                data.add(row);
            }
        }

        return new Gson().toJson(data);
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

 */
}
