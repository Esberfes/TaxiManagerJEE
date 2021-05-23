package com.taxi.database;

import com.taxi.entities.RecaudacionIngresosEntity;
import com.taxi.entities.RecaudacionesEntity;
import com.taxi.pojos.Recaudacion;
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
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.taxi.utils.FilterUtils.getFilterFieldValue;
import static com.taxi.utils.FilterUtils.matchModeTranslation;

@Stateless(name = RecaudacionDbBean.BEAN_NAME)
@Interceptors(TaxiLogger.class)
public class RecaudacionDbBean {

    public final static String BEAN_NAME = "RecaudacionDbBean";

    @PersistenceContext
    private EntityManager em;

    @Inject
    private LicenciasDbBean licenciasDbBean;

    @Inject
    private RecaudacionIngresoDbBean recaudacionIngresoDbBean;

    public List<RecaudacionesEntity> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT recaudaciones.* FROM licencias, recaudaciones LEFT JOIN recaudacion_ingresos on (recaudacion_ingresos.id_recaudacion = recaudaciones.id) WHERE recaudaciones.id_licencia = licencias.id ");

        Query query = buildFilters(sortMeta, filterMeta, rawQuery, RecaudacionesEntity.class, true);

        if (pageSize > 0)
            query = query.setMaxResults(pageSize).setFirstResult(first);

        List<RecaudacionesEntity> data = query.getResultList();

        for (RecaudacionesEntity entity : data) {
            RecaudacionIngresosEntity ingresosEntity = null;

            if (entity.getRecaudacionIngresosEntities() != null) {
                ingresosEntity =  entity.getRecaudacionIngresosEntities().stream().max((o1, o2) -> {
                    if(o1.getDia() == null)
                        return -1;
                    if(o2.getDia() == null)
                        return 1;

                    if (o1.getDia() > o2.getDia())
                        return 1;
                    if (o1.getDia() < o2.getDia())
                        return -1;
                    if (o1.getDia().equals(o2.getDia()) && o1.getTurno().equalsIgnoreCase("tarde"))
                        return 1;
                    if (o1.getDia().equals(o2.getDia()) && o1.getTurno().equalsIgnoreCase(o2.getTurno()))
                        return 0;

                    return -1;
                }).orElse(null);
            }
            if (ingresosEntity != null) {
                entity.setNumeracionFin(ingresosEntity.getNumeracion());
                recaudacionIngresoDbBean.setRecaudacion(entity.getRecaudacionIngresosEntities());
            }
        }


        return data;
    }

    public int getTotal(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(DISTINCT(recaudaciones.id)) FROM licencias, recaudaciones LEFT JOIN recaudacion_ingresos on (recaudacion_ingresos.id_recaudacion = recaudaciones.id) WHERE recaudaciones.id_licencia = licencias.id ");

        Query query = buildFilters(null, filterMeta, rawQuery, null, false);

        return ((BigInteger) query.getSingleResult()).intValue();
    }

    private Query buildFilters(Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, StringBuilder rawQuery, Class clazz, boolean group) {
        Map<String, FilterMeta> parameters = new HashMap<>();

        if (filterMeta != null) {
            for (Map.Entry<String, FilterMeta> entry : filterMeta.entrySet()) {
                if (entry.getValue().getFilterValue() != null
                        && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue()))
                        && entry.getValue().getFilterValue() != null
                        && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue()))) {

                    if (entry.getKey().equalsIgnoreCase("licencia.codigo")) {
                        rawQuery.append(" AND ").append(" licencias.codigo ").append(matchModeTranslation(entry.getValue().getFilterMatchMode())).append(":").append(entry.getKey());

                    } else if (entry.getKey().equalsIgnoreCase("id_conductor")) {
                        rawQuery.append(" AND ").append("recaudacion_ingresos.").append(entry.getKey()).append(matchModeTranslation(entry.getValue().getFilterMatchMode())).append(":").append(entry.getKey());
                    } else {
                        rawQuery.append(" AND ").append("recaudaciones.").append(entry.getKey()).append(matchModeTranslation(entry.getValue().getFilterMatchMode())).append(":").append(entry.getKey());
                    }


                    parameters.put(entry.getKey(), entry.getValue());
                }
            }
        }
        if (group)
            rawQuery.append(" GROUP BY ").append("recaudaciones.id ");

        if (sortMeta != null && !sortMeta.isEmpty()) {
            SortMeta sort = sortMeta.entrySet().iterator().next().getValue();
            if (sort.getSortField().equals("licencia.codigo")) {
                rawQuery.append(" ORDER BY ").append("licencias.codigo").append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
            } else {
                rawQuery.append(" ORDER BY ").append("recaudaciones.").append(sort.getSortField()).append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
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

    public void insert(Recaudacion recaudacion) {
        em.persist(new RecaudacionesEntity(recaudacion));
    }

    public void update(Recaudacion recaudacion) {
        RecaudacionesEntity recaudacionesEntity = em.find(RecaudacionesEntity.class, recaudacion.getId());
        recaudacionesEntity.setLicenciasEntity(licenciasDbBean.findSingleByCodigo(recaudacion.getLicencia().getCodigo()));
        recaudacionesEntity.setServiciosInicio(recaudacion.getServicios_inicio());
        recaudacionesEntity.setServiciosFin(recaudacion.getServicios_fin());
        recaudacionesEntity.setNumeracionInicio(recaudacion.getNumeracion_inicio());
        recaudacionesEntity.setNumeracionFin(recaudacion.getNumeracion_fin());
        recaudacionesEntity.setKmTotalesInicio(recaudacion.getKm_totales_inicio());
        recaudacionesEntity.setKmTotalesFin(recaudacion.getKm_totales_fin());
        recaudacionesEntity.setKmCargadoInicio(recaudacion.getKm_cargado_inicio());
        recaudacionesEntity.setKmCargadoFin(recaudacion.getKm_cargado_fin());
        recaudacionesEntity.setMes(recaudacion.getMes());
        recaudacionesEntity.setAno(recaudacion.getAno());

        em.persist(recaudacionesEntity);
    }

    public void delete(Long id) {
        em.remove(em.find(RecaudacionesEntity.class, id));
    }

    public RecaudacionesEntity findById(Long id) {
        return em.find(RecaudacionesEntity.class, id);
    }
}
