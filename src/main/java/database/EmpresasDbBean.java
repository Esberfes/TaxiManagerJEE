package database;

import entities.EmpresasEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import pojos.Empresa;

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

@Stateless(name = EmpresasDbBean.BEAN_NAME)
public class EmpresasDbBean {

    public final static String BEAN_NAME = "EmpresasDbBean";

    @Inject
    private Logger logger;

    @PersistenceContext
    private EntityManager em;

    public EmpresasEntity getSingleEmpresa(Long id) {
        return em.find(EmpresasEntity.class, id);
    }

    public EmpresasEntity insert(Empresa empresa) {
        EmpresasEntity empresasEntity = new EmpresasEntity(empresa);
        em.persist(empresasEntity);

        return empresasEntity;
    }

    public void truncate() {
        em.createNativeQuery("DELETE FROM empresas WHERE true").executeUpdate();
    }

    public int getTotal(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM empresas WHERE true ");

        Query query = buildFilters(null, filterMeta, rawQuery, null);

        return ((BigInteger) query.getSingleResult()).intValue();
    }

    public List<EmpresasEntity> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT * FROM empresas WHERE true ");

        Query query = buildFilters(sortMeta, filterMeta, rawQuery, EmpresasEntity.class);

        if (pageSize > 0)
            query = query.setMaxResults(pageSize).setFirstResult(first);

        return query.getResultList();
    }

    private Query buildFilters(Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, StringBuilder rawQuery, Class clazz) {
        Map<String, FilterMeta> parameters = new HashMap<>();

        if (filterMeta != null) {
            for (Map.Entry<String, FilterMeta> entry : filterMeta.entrySet()) {
                if (entry.getValue().getFilterValue() != null
                        && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue()))
                        && entry.getValue().getFilterValue() != null
                        && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue()))) {

                    rawQuery.append(" AND ").append(entry.getKey()).append(" LIKE ").append(":").append(entry.getKey());

                    parameters.put(entry.getKey(), entry.getValue());
                }
            }
        }

        if (sortMeta != null && !sortMeta.isEmpty()) {
            SortMeta sort = sortMeta.entrySet().iterator().next().getValue();
            rawQuery.append(" ORDER BY ").append(sort.getSortField()).append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
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

    public void delete(Long id) {
        em.remove(getSingleEmpresa(id));
    }

    public List<EmpresasEntity> findByName(String name) {
        String rawQuery = "SELECT * FROM empresas WHERE nombre LIKE :nombre ORDER BY nombre ASC";
        name = "%" + name + "%";
        Query query = em.createNativeQuery(rawQuery, EmpresasEntity.class);
        query.setParameter("nombre", name);

        return query.setMaxResults(10).getResultList();
    }

    public EmpresasEntity findSingleByName(String name) {
        String rawQuery = "SELECT * FROM empresas WHERE nombre LIKE :nombre ORDER BY nombre ASC";
        Query query = em.createNativeQuery(rawQuery, EmpresasEntity.class);
        query.setParameter("nombre", name);

        return (EmpresasEntity) query.getSingleResult();
    }

    public void update(Empresa empresa) {
        EmpresasEntity empresasEntity = em.find(EmpresasEntity.class, empresa.getId());
        empresasEntity.setNombre(empresa.getNombre());

        em.merge(empresasEntity);
    }
}
