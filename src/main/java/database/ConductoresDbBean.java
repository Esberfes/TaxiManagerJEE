package database;

import entities.ConductorEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import pojos.Conductor;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Stateless(name = ConductoresDbBean.BEAN_NAME)
public class ConductoresDbBean {

    public final static String BEAN_NAME = "EmployeesDbBean";

    @Inject
    private Logger logger;

    @PersistenceContext
    private EntityManager em;

    public ConductorEntity getSingleEmployee(Long id) {
        return em.find(ConductorEntity.class, id);
    }

    public List<ConductorEntity> findEmployeesByFullName(String name) {
        String rawQuery = "SELECT * FROM conductores WHERE nombre LIKE '%" + name + "%' ORDER BY nombre ";
        Query query = em.createNativeQuery(rawQuery, ConductorEntity.class);

        return query.setMaxResults(10).getResultList();
    }

    public List<ConductorEntity> getEmployeeData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder query = new StringBuilder("SELECT * FROM conductores WHERE true ");

        buildFilters(filterMeta, query);

        if (!sortMeta.isEmpty()) {
            SortMeta sort = sortMeta.entrySet().iterator().next().getValue();
            query.append(" ORDER BY ").append(sort.getSortField()).append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
        }

        Query stats = em.createNativeQuery(query.toString(), ConductorEntity.class);

        if (pageSize > 0)
            stats = stats.setMaxResults(pageSize).setFirstResult(first);

        return stats.getResultList();
    }

    public int getTotalEmployees(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM conductores WHERE true ");

        buildFilters(filterMeta, rawQuery);

        Query query = em.createNativeQuery(rawQuery.toString());

        return ((BigInteger) query.getSingleResult()).intValue();
    }

    public ConductorEntity insertConductor(Conductor conductor) {
        ConductorEntity employeeEntity = new ConductorEntity(conductor);
        em.persist(employeeEntity);

        return employeeEntity;
    }

    public void updateEmployee(Conductor conductor) {
        ConductorEntity employeeEntity = getSingleEmployee(conductor.getId());
        employeeEntity.setNombre(conductor.getNombre());

        em.merge(employeeEntity);
    }

    public void deleteEmployee(Long id) {
        em.remove(getSingleEmployee(id));
    }

    public void truncate() {
        em.createNativeQuery("DELETE FROM conductores WHERE true").executeUpdate();
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
