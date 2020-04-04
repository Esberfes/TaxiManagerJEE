package database;

import entities.EmployeeEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.Employee;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Stateless(name = EmployeesDbBean.BEAN_NAME)
public class EmployeesDbBean {

    public final static String BEAN_NAME = "EmployeesDbBean";

    @Inject
    private Logger logger;

    @PersistenceContext
    private EntityManager em;

    public EmployeeEntity getSingleEmployee(Long id) {
        return em.find(EmployeeEntity.class, id);
    }

    public List<EmployeeEntity> getEmployeeData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder query = new StringBuilder("SELECT * FROM employees WHERE true ");

        buildFilters(filterMeta, query);

        Query stats = em.createNativeQuery(query.toString(), EmployeeEntity.class);

        if (pageSize > 0)
            stats = stats.setMaxResults(pageSize).setFirstResult(first);

        return stats.getResultList();
    }

    public int getTotalEmployees(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM employees WHERE true ");

        buildFilters(filterMeta, rawQuery);

        Query query = em.createNativeQuery(rawQuery.toString());

        return ((BigInteger) query.getSingleResult()).intValue();
    }

    public EmployeeEntity insertEmployee(Employee employee) {
        EmployeeEntity employeeEntity = new EmployeeEntity(employee);
        em.persist(employeeEntity);

        return employeeEntity;
    }

    public void updateEmployee(Employee employee) {
        EmployeeEntity employeeEntity = getSingleEmployee(employee.getId());
        employeeEntity.setName(employee.getName());
        employeeEntity.setFirstLastName(employee.getFirstLastName());
        employeeEntity.setSecondLastName(employee.getSecondLastName());
        employeeEntity.setDni(employee.getDni());

        em.merge(employeeEntity);
    }

    public void deleteEmployee(Long id) {
        em.remove(getSingleEmployee(id));
    }

    public void truncate() {
        em.createNativeQuery("DELETE FROM employees WHERE true").executeUpdate();
    }

    private void buildFilters(Map<String, FilterMeta> filterMeta, StringBuilder rawQuery) {
        if (filterMeta != null)
            for (Map.Entry<String, FilterMeta> entry : filterMeta.entrySet())
                if (entry.getValue().getFilterValue() != null && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue())) && entry.getValue().getFilterValue() != null && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue())))
                    rawQuery.append(" AND ").append(entry.getKey()).append(" LIKE ").append(getFilterFieldValue(entry.getValue()));
    }

    private static Object getFilterFieldValue(FilterMeta filterField) {
        switch (filterField.getFilterMatchMode()) {
            default:
                return "'%" + filterField.getFilterValue() + "%'";
        }
    }
}
