package database;

import entities.WorkShiftEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.WorkShift;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
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
                        rawQuery.append(" AND ").append(" concat(name, firstLastName, secondLastName) ").append("LIKE ").append(getFilterFieldValue(entry.getValue()));
                    else if (entry.getKey().equalsIgnoreCase("license"))
                        rawQuery.append(" AND ").append(" licenses.code ").append("LIKE ").append(getFilterFieldValue(entry.getValue()));
                    else
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
}
