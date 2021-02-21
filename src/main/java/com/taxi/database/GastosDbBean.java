package com.taxi.database;

import com.taxi.entities.GastosEntity;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import com.taxi.pojos.Gasto;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.taxi.utils.FilterUtils.getFilterFieldValue;

public class GastosDbBean {

    public final static String BEAN_NAME = "GastosDbBean";

    @PersistenceContext
    private EntityManager em;

    @Inject
    private LicenciasDbBean licenciasDbBean;

    @Inject
    private FormasPagosDbBean formasPagosDbBean;

    @Inject
    private ConceptosGastosDbBean conceptosGastosDbBean;

    public List<GastosEntity> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT * FROM gastos, licencias, formas_pagos_gastos, conceptos_gastos, tipos_gastos" +
                " WHERE " +
                "gastos.id_licencia = licencias.id " +
                "AND gastos.id_forma_pago = formas_pagos_gastos.id " +
                "AND gastos.id_concepto = conceptos_gastos.id " +
                "AND conceptos_gastos.id_tipo_gasto = tipos_gastos.id");

        Query query = buildFilters(sortMeta, filterMeta, rawQuery, GastosEntity.class);

        if (pageSize > 0)
            query = query.setMaxResults(pageSize).setFirstResult(first);

        return query.getResultList();
    }


    public int getTotal(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM  gastos, licencias, formas_pagos_gastos, conceptos_gastos, tipos_gastos" +
                " WHERE " +
                "gastos.id_licencia = licencias.id " +
                "AND gastos.id_forma_pago = formas_pagos_gastos.id " +
                "AND gastos.id_concepto = conceptos_gastos.id " +
                "AND conceptos_gastos.id_tipo_gasto = tipos_gastos.id");

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

                    switch (entry.getKey()) {
                        case "licencia.codigo":
                            rawQuery.append(" AND ").append(" licencias.codigo ").append("LIKE ").append(":").append(entry.getKey());
                            break;
                        case "concepto.nombre":
                            rawQuery.append(" AND ").append(" conceptos_gastos.nombre ").append("LIKE ").append(":").append(entry.getKey());
                            break;
                        case "formaPago.nombre":
                            rawQuery.append(" AND ").append(" formas_pagos_gastos ").append("LIKE ").append(":").append(entry.getKey());
                            break;
                        case "tipos_gastos.es_operacional" :
                            rawQuery.append(" AND ").append(" tipos_gastos.es_operacional ").append("LIKE ").append(":").append(entry.getKey());
                            break;
                        default:
                            rawQuery.append(" AND ").append("gastos.").append(entry.getKey()).append(" LIKE ").append(":").append(entry.getKey());
                            break;
                    }

                    parameters.put(entry.getKey(), entry.getValue());
                }
            }
        }

        if (sortMeta != null && !sortMeta.isEmpty()) {
            SortMeta sort = sortMeta.entrySet().iterator().next().getValue();

            switch (sort.getSortField()) {
                case "licencia.codigo":
                    rawQuery.append(" ORDER BY ").append("licencias.codigo").append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
                    break;
                case "concepto.nombre":
                    rawQuery.append(" ORDER BY ").append("conceptos_gastos.nombre").append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
                    break;
                case "formaPago.nombre":
                    rawQuery.append(" ORDER BY ").append("formas_pagos_gastos.nombre").append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
                    break;
                default:
                    rawQuery.append(" ORDER BY ").append("gastos.").append(sort.getSortField()).append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
                    break;
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

    public GastosEntity insert(GastosEntity gasto) {
        em.persist(gasto);

        return gasto;
    }

    public void truncate() {
        em.createNativeQuery("DELETE FROM gastos WHERE true").executeUpdate();
    }

    public void update(Gasto gasto) {
        GastosEntity gastosEntity = em.find(GastosEntity.class, gasto.getId());
        gastosEntity.setLicenciasEntity(licenciasDbBean.findSingleByCodigo(gasto.getLicencia().getCodigo()));
        gastosEntity.setFormasPagosGastosEntity(formasPagosDbBean.findSingleByName(gasto.getFormaPago().getNombre()));
        gastosEntity.setConceptosGastosEntity(conceptosGastosDbBean.findSingleByName(gasto.getConcepto().getNombre()));
        gastosEntity.setDefinicion(gasto.getDefinicion());
        gastosEntity.setFechaFactura(gasto.getFechaFactura());
        gastosEntity.setImporte(gasto.getImporte());
        gastosEntity.setCreado(gasto.getCreado());


        em.merge(gastosEntity);
    }

    public void delete(Long id) {
        em.remove(em.find(GastosEntity.class, id));
    }
}
