package com.taxi.database;

import com.taxi.entities.FormasPagosGastosEntity;
import com.taxi.singletons.TaxiLogger;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import com.taxi.pojos.FormasPago;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.taxi.utils.FilterUtils.getFilterFieldValue;

@Stateless(name = FormasPagosDbBean.BEAN_NAME)
@Interceptors(TaxiLogger.class)
public class FormasPagosDbBean {

    public final static String BEAN_NAME = "FormasPagosDbBean";

    @PersistenceContext
    private EntityManager em;

    public List<FormasPagosGastosEntity> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT * FROM formas_pagos_gastos WHERE true ");

        Query query = buildFilters(sortMeta, filterMeta, rawQuery, FormasPagosGastosEntity.class);

        if (pageSize > 0)
            query = query.setMaxResults(pageSize).setFirstResult(first);

        return query.getResultList();
    }

    public int getTotal(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM formas_pagos_gastos WHERE true ");

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

                    rawQuery.append(" AND ").append("formas_pagos_gastos.").append(entry.getKey()).append(" LIKE ").append(":").append(entry.getKey());

                    parameters.put(entry.getKey(), entry.getValue());
                }
            }
        }

        if (sortMeta != null && !sortMeta.isEmpty()) {
            SortMeta sort = sortMeta.entrySet().iterator().next().getValue();
            rawQuery.append(" ORDER BY ").append("formas_pagos_gastos.").append(sort.getSortField()).append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");

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

    public void update(FormasPago formasPago) {
        FormasPagosGastosEntity formasPagosGastosEntity = em.find(FormasPagosGastosEntity.class, formasPago.getId());
        formasPagosGastosEntity.setNombre(formasPago.getNombre());

        em.merge(formasPagosGastosEntity);
    }

    public void insert(FormasPago formasPago) {
        em.persist(new FormasPagosGastosEntity(formasPago));
    }

    public void delete(Long id) {
        em.remove(em.find(FormasPagosGastosEntity.class, id));
    }

    public List<FormasPagosGastosEntity> findByName(String value) {
        Query query = em.createNativeQuery("SELECT * FROM formas_pagos_gastos WHERE nombre LIKE '" + value + "%'  ORDER BY nombre ",
                FormasPagosGastosEntity.class);

        return query.setMaxResults(10).getResultList();
    }

    public FormasPagosGastosEntity findSingleByName(String value) {
        return findByName(value).get(0);
    }

    public FormasPagosGastosEntity findById(Long id) {
        return em.find(FormasPagosGastosEntity.class, id);
    }
}

