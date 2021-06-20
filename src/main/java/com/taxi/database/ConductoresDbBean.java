package com.taxi.database;

import com.taxi.entities.ConductorEntity;
import com.taxi.singletons.TaxiLogger;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import com.taxi.pojos.Conductor;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.taxi.utils.FilterUtils.getFilterFieldValue;
import static com.taxi.utils.FilterUtils.matchModeTranslation;

@Stateless(name = ConductoresDbBean.BEAN_NAME)
@Interceptors(TaxiLogger.class)
public class ConductoresDbBean {

    public final static String BEAN_NAME = "EmployeesDbBean";

    private Map<String, String> filterMapping;

    @Inject
    private Logger logger;

    @Inject
    private EmpresasDbBean empresasDbBean;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        filterMapping = new HashMap<>();
    }

    public ConductorEntity getSingle(Long id) {
        return em.find(ConductorEntity.class, id);
    }

    public List<ConductorEntity> findByFullName(String name) {
        String rawQuery = "SELECT * FROM conductores WHERE nombre LIKE '" + name + "%' ORDER BY nombre ";
        Query query = em.createNativeQuery(rawQuery, ConductorEntity.class);

        return query.getResultList();
    }

    public List<ConductorEntity> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT * FROM conductores, empresas WHERE conductores.id_empresa = empresas.id ");

        Query query = buildFilters(sortMeta, filterMeta, rawQuery, ConductorEntity.class);

        if (pageSize > 0)
            query = query.setMaxResults(pageSize).setFirstResult(first);

        return query.getResultList();
    }

    public int getTotal(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM conductores, empresas WHERE conductores.id_empresa = empresas.id ");

        Query query = buildFilters(null, filterMeta, rawQuery, null);

        return ((BigInteger) query.getSingleResult()).intValue();
    }

    public ConductorEntity insert(Conductor conductor) {
        ConductorEntity employeeEntity = new ConductorEntity(conductor);
        em.persist(employeeEntity);

        return employeeEntity;
    }

    public void update(Conductor conductor) {
        ConductorEntity conductorEntity = getSingle(conductor.getId());
        conductorEntity.setNombre(conductor.getNombre());
        conductorEntity.setEmpresasEntity(empresasDbBean.findSingleByName(conductor.getEmpresa().getNombre()));
        conductorEntity.setComplementoIva(conductor.getComplemento_iva());
        conductorEntity.setT000(conductor.getT000());
        conductorEntity.setT065(conductor.getT065());
        conductorEntity.setT060(conductor.getT060());
        conductorEntity.setT055(conductor.getT055());
        conductorEntity.setT050(conductor.getT050());

        em.merge(conductorEntity);
    }

    public void delete(Long id) {
        em.remove(getSingle(id));
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
                        rawQuery.append(" AND ").append(" empresas.nombre ").append(matchModeTranslation(entry.getValue().getFilterMatchMode())).append(":").append(entry.getKey());
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

    public ConductorEntity getSingleByName(String nombre) {
        String rawQuery = "SELECT * FROM conductores WHERE nombre LIKE '" + nombre + "%' ORDER BY nombre ";
        Query query = em.createNativeQuery(rawQuery, ConductorEntity.class);

        return (ConductorEntity) query.getSingleResult();
    }
    public ConductorEntity getSingleByNameExact(String nombre) {
        String rawQuery = "SELECT * FROM conductores WHERE nombre ='" + nombre + "' ORDER BY nombre ";
        Query query = em.createNativeQuery(rawQuery, ConductorEntity.class);

        return (ConductorEntity) query.getSingleResult();
    }

    public ConductorEntity findById(Long id) {
        return em.find(ConductorEntity.class, id);
    }
}
