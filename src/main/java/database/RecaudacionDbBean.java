package database;

import entities.RecaudacionesEntity;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import pojos.Licencia;
import pojos.Recaudacion;

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

@Stateless(name = RecaudacionDbBean.BEAN_NAME)
public class RecaudacionDbBean {

    public final static String BEAN_NAME = "RecaudacionDbBean";

    @PersistenceContext
    private EntityManager em;

    @Inject
    private LicenciasDbBean licenciasDbBean;

    public List<RecaudacionesEntity> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT * FROM recaudaciones WHERE true ");

        Query query = buildFilters(sortMeta, filterMeta, rawQuery, RecaudacionesEntity.class);

        if (pageSize > 0)
            query = query.setMaxResults(pageSize).setFirstResult(first);

        return query.getResultList();
    }

    public int getTotal(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM recaudaciones WHERE true ");

        Query query = buildFilters(null, filterMeta, rawQuery, null);

        return ((BigInteger) query.getSingleResult()).intValue();
    }

    private Query buildFilters(Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, StringBuilder rawQuery, Class clazz) {
        Map<String, FilterMeta> parameters = new HashMap<>();

        if (filterMeta != null) {
            for (Map.Entry<String, FilterMeta> entry : filterMeta.entrySet()) {
                if (entry.getValue().getFilterValue() != null
                        && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue()))
                        && entry.getValue().getFilterValue() != null
                        && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue()))) {

                    rawQuery.append(" AND ").append("recaudaciones.").append(entry.getKey()).append(" LIKE ").append(":").append(entry.getKey());

                    parameters.put(entry.getKey(), entry.getValue());
                }
            }
        }

        if (sortMeta != null && !sortMeta.isEmpty()) {
            SortMeta sort = sortMeta.entrySet().iterator().next().getValue();
            rawQuery.append(" ORDER BY ").append("recaudaciones.").append(sort.getSortField()).append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
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

    public void insert(Recaudacion recaudacion) {

    }


    public void update(Licencia licencia) {

    }

    public void delete(Long id) {
        em.remove(em.find(RecaudacionesEntity.class, id));
    }
}
