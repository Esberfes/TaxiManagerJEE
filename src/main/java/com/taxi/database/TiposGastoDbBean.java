package com.taxi.database;

import com.taxi.entities.TiposGastosEntity;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import com.taxi.pojos.TiposGasto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.taxi.utils.FilterUtils.getFilterFieldValue;

@Stateless(name = TiposGastoDbBean.BEAN_NAME)
public class TiposGastoDbBean {

    public final static String BEAN_NAME = "TiposGastoDbBean";

    @PersistenceContext
    private EntityManager em;

    public List<TiposGastosEntity> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT * FROM tipos_gastos WHERE true ");

        Query query = buildFilters(sortMeta, filterMeta, rawQuery, TiposGastosEntity.class);

        if (pageSize > 0)
            query = query.setMaxResults(pageSize).setFirstResult(first);

        return query.getResultList();
    }

    public int getTotal(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM tipos_gastos WHERE true ");

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

                    rawQuery.append(" AND ").append("tipos_gastos.").append(entry.getKey()).append(" LIKE ").append(":").append(entry.getKey());

                    parameters.put(entry.getKey(), entry.getValue());
                }
            }
        }

        if (sortMeta != null && !sortMeta.isEmpty()) {
            SortMeta sort = sortMeta.entrySet().iterator().next().getValue();
            rawQuery.append(" ORDER BY ").append("tipos_gastos.").append(sort.getSortField()).append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
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

    public TiposGastosEntity findSingle(Long id) {
        return em.find(TiposGastosEntity.class, id);
    }

    public List<TiposGastosEntity> findByName(String name) {
        String rawQuery = "SELECT * FROM tipos_gastos WHERE nombre LIKE :nombre ORDER BY nombre ASC";
        name = "%" + name + "%";
        Query query = em.createNativeQuery(rawQuery, TiposGastosEntity.class);
        query.setParameter("nombre", name);

        return query.setMaxResults(10).getResultList();
    }

    public TiposGastosEntity findSingleByName(String name) {
        String rawQuery = "SELECT * FROM tipos_gastos WHERE nombre LIKE :nombre ORDER BY nombre ASC";
        name = "%" + name + "%";
        Query query = em.createNativeQuery(rawQuery, TiposGastosEntity.class);
        query.setParameter("nombre", name);

        return (TiposGastosEntity) query.getSingleResult();
    }

    public void update(TiposGasto tiposGasto) {
        TiposGastosEntity tiposGastosEntity = em.find(TiposGastosEntity.class, tiposGasto.getId());
        tiposGastosEntity.setNombre(tiposGasto.getNombre());
        tiposGastosEntity.setEsOperacional(tiposGasto.getEs_operacional());

        em.merge(tiposGastosEntity);
    }

    public void delete(Long id) {
        em.remove(em.find(TiposGastosEntity.class, id));
    }

    public void insert(TiposGasto tiposGasto) {
        em.persist(new TiposGastosEntity(tiposGasto));
    }
}
