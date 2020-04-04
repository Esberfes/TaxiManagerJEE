package database;

import entities.WorkShiftEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import pojos.WorkShift;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Stateless(name = WorkShiftsDbBean.BEAN_NAME)
public class WorkShiftsDbBean {

    public final static String BEAN_NAME = "WorkShiftsDbBean";

    @Inject
    private Logger logger;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private EmployeesDbBean employeesDbBean;

    @Inject
    private LicensesDbBean licensesDbBean;

    public WorkShiftEntity getSingleWorkShift(Long id) {
        return em.find(WorkShiftEntity.class, id);
    }

    public List<WorkShiftEntity> getWorkShiftData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder query = new StringBuilder("SELECT workshifts.* FROM workshifts, employees, licenses WHERE workshifts.employee = employees.id AND workshifts.license = licenses.id ");

        buildFilters(filterMeta, query);

        if (!sortMeta.isEmpty()) {
            SortMeta sort = sortMeta.entrySet().iterator().next().getValue();
            query.append(" ORDER BY ").append(sort.getSortField()).append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
        }

        Query stats = em.createNativeQuery(query.toString(), WorkShiftEntity.class);

        if (pageSize > 0)
            stats = stats.setMaxResults(pageSize).setFirstResult(first);

        return stats.getResultList();
    }

    public int getWorkShiftTotal(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM workshifts, employees, licenses WHERE workshifts.employee = employees.id AND workshifts.license = licenses.id ");

        buildFilters(filterMeta, rawQuery);

        Query query = em.createNativeQuery(rawQuery.toString());

        return ((BigInteger) query.getSingleResult()).intValue();
    }

    public void insertWorkShift(WorkShift workShift) {
        WorkShiftEntity workShiftEntity = new WorkShiftEntity(workShift);
        workShiftEntity.setEmployee(employeesDbBean.getSingleEmployee(workShift.getEmployee().getId()));
        workShiftEntity.setLicense(licensesDbBean.getSingleLicenses(workShift.getLicense().getId()));

        em.persist(workShiftEntity);
    }

    public void truncate() {
        em.createNativeQuery("DELETE FROM workshifts WHERE true").executeUpdate();
    }

    public void deleteWorkShift(Long id) {
        em.remove(getSingleWorkShift(id));
    }

    private void buildFilters(Map<String, FilterMeta> filterMeta, StringBuilder rawQuery) {
        if (filterMeta != null) {
            for (Map.Entry<String, FilterMeta> entry : filterMeta.entrySet()) {
                if (entry.getValue().getFilterValue() != null && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue()))
                        && entry.getValue().getFilterValue() != null && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue()))) {
                    if (entry.getKey().equalsIgnoreCase("employee"))
                        rawQuery.append(" AND ").append(" concat(name, ' ', firstLastName, ' ', secondLastName) ").append("LIKE ").append(getFilterFieldValue(entry.getValue()));
                    else if (entry.getKey().equalsIgnoreCase("license"))
                        rawQuery.append(" AND ").append(" licenses.code ").append("LIKE ").append(getFilterFieldValue(entry.getValue()));
                    else if (entry.getKey().equalsIgnoreCase("day")) {
                        // dd-mm-YYYY
                        String parsedDate = entry.getValue().getFilterValue().toString().replace("/", "-");
                        String[] fragments = parsedDate.split("-");
                        if (fragments.length == 3) {
                            parsedDate = fragments[2] + "-" + fragments[1] + "-" + fragments[0];
                        } else if (fragments.length == 2) {
                            parsedDate = fragments[1] + "-" + fragments[0];
                        }

                        rawQuery.append(" AND ").append(entry.getKey()).append(" LIKE ").append("'%").append(parsedDate).append("%'");
                    } else
                        rawQuery.append(" AND ").append(entry.getKey()).append(" LIKE ").append(getFilterFieldValue(entry.getValue()));
                }

            }
        }
    }

    private static Object getFilterFieldValue(FilterMeta filterField) {
        switch (filterField.getFilterMatchMode()) {
            default:
                return "'%" + filterField.getFilterValue() + "%'";
        }
    }

    public void updateWorkShift(WorkShift workShift) {
        WorkShiftEntity workShiftEntity = getSingleWorkShift(workShift.getId());
        workShiftEntity.setIncome(workShift.getIncome());
        workShiftEntity.setShiftType(workShift.getShiftType());
        workShiftEntity.setDay(workShift.getDay());

        em.merge(workShiftEntity);
    }

    public List<WorkShiftEntity> getWorkShiftsBetween(Date start, Date end) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String rawQuery = "SELECT workshifts.* FROM workshifts WHERE day >= '" + simpleDateFormat.format(start) + "' AND day <= '" + simpleDateFormat.format(end) + "' ORDER BY day ASC";
        Query query = em.createNativeQuery(rawQuery, WorkShiftEntity.class);

        return query.getResultList();
    }
}
