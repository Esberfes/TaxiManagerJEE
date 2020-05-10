package database;

import entities.ConductorEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import pojos.Conductor;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.FilterUtils.getFilterFieldValue;

@Stateless(name = ConductoresDbBean.BEAN_NAME)
public class ConductoresDbBean {

    public final static String BEAN_NAME = "EmployeesDbBean";

    private Map<String, String> filterMapping;

    @Inject
    private Logger logger;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        filterMapping = new HashMap<>();
    }

    public ConductorEntity getSingleConductor(Long id) {
        return em.find(ConductorEntity.class, id);
    }

    public List<ConductorEntity> findEmployeesByFullName(String name) {
        String rawQuery = "SELECT * FROM conductores WHERE nombre LIKE '%" + name + "%' ORDER BY nombre ";
        Query query = em.createNativeQuery(rawQuery, ConductorEntity.class);

        return query.setMaxResults(10).getResultList();
    }

    public List<ConductorEntity> getEmployeeData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT * FROM conductores, empresas WHERE conductores.id_empresa = empresas.id ");

        Query query = buildFilters(null, filterMeta, rawQuery, ConductorEntity.class);

        if (pageSize > 0)
            query = query.setMaxResults(pageSize).setFirstResult(first);

        return query.getResultList();
    }

    public int getTotalEmployees(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM conductores, empresas WHERE conductores.id_empresa = empresas.id ");

        Query query = buildFilters(null, filterMeta, rawQuery, null);

        return ((BigInteger) query.getSingleResult()).intValue();
    }

    public ConductorEntity insertConductor(Conductor conductor) {
        ConductorEntity employeeEntity = new ConductorEntity(conductor);
        em.persist(employeeEntity);

        return employeeEntity;
    }

    public void update(Conductor conductor) {
        ConductorEntity employeeEntity = getSingleConductor(conductor.getId());
        employeeEntity.setNombre(conductor.getNombre());

        em.merge(employeeEntity);
    }

    public void delete(Long id) {
        em.remove(getSingleConductor(id));
    }

    public void truncate() {
        em.createNativeQuery("DELETE FROM conductores WHERE true").executeUpdate();
    }

    private Query buildFilters(Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, StringBuilder rawQuery, Class clazz) {
        Map<String, FilterMeta> parameters = new HashMap<>();

        if (filterMeta != null) {
            for (Map.Entry<String, FilterMeta> entry : filterMeta.entrySet()) {
                if (entry.getValue().getFilterValue() != null
                        && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue()))
                        && entry.getValue().getFilterValue() != null
                        && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue()))) {

                    if (entry.getKey().equalsIgnoreCase("empresa.nombre")) {
                        rawQuery.append(" AND ").append(" empresas.nombre ").append("LIKE ").append(":").append(entry.getKey());
                    } else {
                        rawQuery.append(" AND ").append("conductores.").append(entry.getKey()).append(" LIKE ").append(":").append(entry.getKey());
                    }

                    parameters.put(entry.getKey(), entry.getValue());
                }
            }
        }

        if (sortMeta != null && !sortMeta.isEmpty()) {
            SortMeta sort = sortMeta.entrySet().iterator().next().getValue();
            if (sort.getSortField().equals("empresa.nombre")) {
                rawQuery.append(" ORDER BY ").append("empresas.nombre").append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
            } else {
                rawQuery.append(" ORDER BY ").append("conductores.").append(sort.getSortField()).append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
            }
        }

        Query query;
        if (clazz != null)
            query = em.createNativeQuery(rawQuery.toString(), clazz);
        else
            query = em.createNativeQuery(rawQuery.toString());

        for (Map.Entry<String, FilterMeta> parameter : parameters.entrySet())
            query.setParameter(parameter.getKey(), getFilterFieldValue(parameter.getValue()));

        return query;
    }

}
