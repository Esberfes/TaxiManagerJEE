package com.taxi.database;

import com.taxi.entities.GastosEntity;
import com.taxi.entities.TiposGastosEntity;
import com.taxi.pojos.Gasto;
import com.taxi.singletons.TaxiLogger;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.taxi.utils.FilterUtils.getFilterFieldValue;
import static com.taxi.utils.FilterUtils.matchModeTranslation;

@Stateless(name = GastosDbBean.BEAN_NAME)
@Interceptors(TaxiLogger.class)
public class GastosDbBean {

    public final static String BEAN_NAME = "GastosDbBean";

    @PersistenceContext
    private EntityManager em;

    @Inject
    private LicenciasDbBean licenciasDbBean;

    @Inject
    private FormasPagosDbBean formasPagosDbBean;

    @Inject
    private TiposGastoDbBean tiposGastoDbBean;

    public List<Gasto> getGastosComunes(int mes, int ano, Long id) {

        String rawQuery = "SELECT g FROM GastosEntity g"+
                " WHERE g.mes = :mes AND g.ano = :ano  AND g.licenciasEntity.id IS NULL AND g.tiposGastosEntity.nombre = 'GASTOS COMUNES'";
        TypedQuery<GastosEntity> nativeQuery = em.createQuery(rawQuery, GastosEntity.class);
        nativeQuery.setParameter("mes", mes);
        nativeQuery.setParameter("ano", ano);


        String sumQuery = "SELECT (SUM(r.kmTotalesFin) - SUM(r.kmTotalesInicio)) FROM RecaudacionesEntity r WHERE  r.mes = :mes AND r.ano = :ano";
        TypedQuery<Long> sumQ = em.createQuery(sumQuery, Long.class);
        sumQ.setParameter("mes", mes);
        sumQ.setParameter("ano", ano);

        String parQQuery = "SELECT (r.kmTotalesFin - r.kmTotalesInicio) FROM RecaudacionesEntity r WHERE  r.mes = :mes AND r.ano = :ano AND r.licenciasEntity.id =:id";
        TypedQuery<Integer> parQ = em.createQuery(parQQuery, Integer.class);
        parQ.setParameter("mes", mes);
        parQ.setParameter("ano", ano);
        parQ.setParameter("id", id);

        // esta liucencia
        Integer par = parQ.getSingleResult();

        // todas las licencia
        Long sum = sumQ.getSingleResult();
        List<GastosEntity> resultList = nativeQuery.getResultList();
        List<Gasto> collect = resultList.stream().map(Gasto::new).collect(Collectors.toList());

        for(Gasto gastosEntity :collect) {
            BigDecimal i = gastosEntity.getImporte()
                    .multiply(new BigDecimal(par))
                    .divide(new BigDecimal(sum), RoundingMode.CEILING);
            gastosEntity.setImporte(i);
        }

        return collect;
    }

    public List<GastosEntity> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT * FROM  gastos " +
                " LEFT JOIN tipos_gastos on (gastos.id_tipo_gasto = tipos_gastos.id) " +
                " LEFT JOIN formas_pagos_gastos on (gastos.id_forma_pago = formas_pagos_gastos.id) " +
                "WHERE true ");

        Query query = buildFilters(sortMeta, filterMeta, rawQuery, GastosEntity.class);

        if (pageSize > 0)
            query = query.setMaxResults(pageSize).setFirstResult(first);

        return query.getResultList();
    }


    public int getTotal(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT count(*) FROM  gastos " +
                " LEFT JOIN tipos_gastos on (gastos.id_tipo_gasto = tipos_gastos.id) " +
                " LEFT JOIN formas_pagos_gastos on (gastos.id_forma_pago = formas_pagos_gastos.id) " +
                "WHERE true ");

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
                            rawQuery.append(" AND ").append(" licencias.codigo ").append(matchModeTranslation(entry.getValue().getFilterMatchMode())).append(":").append(entry.getKey());
                            break;
                        case "formaPago.nombre":
                            rawQuery.append(" AND ").append(" formas_pagos_gastos. ").append(matchModeTranslation(entry.getValue().getFilterMatchMode())).append(":").append(entry.getKey());
                            break;
                        case "tipos_gastos.es_operacional":
                            rawQuery.append(" AND ").append(" tipos_gastos.es_operacional ").append(matchModeTranslation(entry.getValue().getFilterMatchMode())).append(":").append(entry.getKey());
                            break;
                        case "tipos_gastos.nombre":
                            rawQuery.append(" AND ").append(" tipos_gastos.nombre ").append(matchModeTranslation(entry.getValue().getFilterMatchMode())).append(":").append(entry.getKey());
                            break;
                        default:
                            rawQuery.append(" AND ").append(entry.getKey()).append(matchModeTranslation(entry.getValue().getFilterMatchMode())).append(":").append(entry.getKey());
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
                case "formaPago.nombre":
                    rawQuery.append(" ORDER BY ").append("formas_pagos_gastos.nombre").append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
                    break;
                case "tipoGasto.nombre":
                    rawQuery.append(" ORDER BY ").append(" tipos_gastos.nombre ").append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
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
        if (gasto.getTipoGasto() != null) {
            try {
                TiposGastosEntity tiposGastosEntity = tiposGastoDbBean.findSingleByName(gasto.getTipoGasto().getNombre());
                gastosEntity.setTiposGastosEntity(tiposGastosEntity);
            } catch (Throwable e) {
                gastosEntity.setTiposGastosEntity(null);
            }
        }
        gastosEntity.setDefinicion(gasto.getDefinicion());
        gastosEntity.setMes(gasto.getMes());
        gastosEntity.setAno(gasto.getAno());
        gastosEntity.setFechaFactura(gasto.getFechaFactura());
        gastosEntity.setImporte(gasto.getImporte());
        gastosEntity.setCreado(gasto.getCreado());


        em.merge(gastosEntity);
    }

    public void delete(Long id) {
        em.remove(em.find(GastosEntity.class, id));
    }

    public GastosEntity findById(Long id) {
        return em.find(GastosEntity.class, id);
    }
}
