package database;

import entities.ConductorEntity;
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
import java.util.List;
import java.util.Map;

@Stateless(name = EmpresasDbBean.BEAN_NAME)
public class EmpresasDbBean {

    public final static String BEAN_NAME = "EmpresasDbBean";

    @Inject
    private Logger logger;

    @PersistenceContext
    private EntityManager em;

    private EmpresasEntity getSingleEmpresa(Long id) {
        return em.find(EmpresasEntity.class, id);
    }

    public EmpresasEntity insertEmpresa(Empresa empresa) {
        EmpresasEntity empresasEntity = new EmpresasEntity(empresa);
        em.persist(empresasEntity);

        return empresasEntity;
    }

    public void truncate() {
        em.createNativeQuery("DELETE FROM empresas WHERE true").executeUpdate();
    }

    public int getTotal(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM empresas WHERE true ");

        buildFilters(filterMeta, rawQuery);

        Query query = em.createNativeQuery(rawQuery.toString());

        return ((BigInteger) query.getSingleResult()).intValue();
    }

    public List<EmpresasEntity> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder query = new StringBuilder("SELECT * FROM empresas WHERE true ");

        buildFilters(filterMeta, query);

        if (!sortMeta.isEmpty()) {
            SortMeta sort = sortMeta.entrySet().iterator().next().getValue();
            query.append(" ORDER BY ").append(sort.getSortField()).append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
        }

        Query stats = em.createNativeQuery(query.toString(), EmpresasEntity.class);

        if (pageSize > 0)
            stats = stats.setMaxResults(pageSize).setFirstResult(first);

        return stats.getResultList();
    }

    private void buildFilters(Map<String, FilterMeta> filterMeta, StringBuilder rawQuery) {
        if (filterMeta != null)
            for (Map.Entry<String, FilterMeta> entry : filterMeta.entrySet())
                if (entry.getValue().getFilterValue() != null
                        && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue()))
                        && entry.getValue().getFilterValue() != null
                        && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue())))

                    rawQuery.append(" AND ").append(entry.getKey()).append(" LIKE ").append(getFilterFieldValue(entry.getValue()));
    }

    private static Object getFilterFieldValue(FilterMeta filterField) {
        switch (filterField.getFilterMatchMode()) {
            default:
                return "'%" + filterField.getFilterValue() + "%'";
        }
    }

    public void delete(Long id) {
        em.remove(getSingleEmpresa(id));
    }
}
