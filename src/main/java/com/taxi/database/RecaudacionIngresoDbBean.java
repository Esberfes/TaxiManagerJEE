package com.taxi.database;

import com.taxi.entities.ConductorEntity;
import com.taxi.entities.RecaudacionIngresosEntity;
import com.taxi.entities.RecaudacionesEntity;
import com.taxi.entities.RecaudacionesIngresosEstadosEntity;
import com.taxi.pojos.RecaudacionIngreso;
import com.taxi.singletons.TaxiLogger;
import com.taxi.utils.RecaudacionUtils;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static com.taxi.utils.FilterUtils.getFilterFieldValue;
import static com.taxi.utils.RecaudacionUtils.calculateEfectivo;
import static com.taxi.utils.RecaudacionUtils.calculateLiquido;

@Stateless(name = RecaudacionIngresoDbBean.BEAN_NAME)
@Interceptors(TaxiLogger.class)
public class RecaudacionIngresoDbBean {

    public final static String BEAN_NAME = "RecaudacionIngresoDbBean";

    @PersistenceContext
    private EntityManager em;

    @Inject
    private EstadosIngresoDbBean estadosIngresoDbBean;

    @Inject
    private ConductoresDbBean conductoresDbBean;

    @Inject
    private TaxiLogger logger;

    @Inject
    private RecaudacionDbBean recaudacionDbBean;


    public List<RecaudacionIngresosEntity> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, Long parentId) {
        StringBuilder rawQuery = new StringBuilder("SELECT * FROM recaudacion_ingresos, recaudaciones, conductores WHERE recaudacion_ingresos.id_recaudacion = recaudaciones.id AND recaudacion_ingresos.id_conductor = conductores.id ");

        if (parentId != null)
            rawQuery.append("AND recaudacion_ingresos.id_recaudacion = :idrecaudacion ");

        Query query = buildFilters(sortMeta, filterMeta, rawQuery, RecaudacionIngresosEntity.class);

        if (parentId != null)
            query.setParameter("idrecaudacion", parentId);

        if (pageSize > 0)
            query = query.setMaxResults(pageSize).setFirstResult(first);


        List<RecaudacionIngresosEntity> result = query.getResultList();

        if(result != null && result.size() > 0) {
            List<RecaudacionIngresosEntity> recaudacionIngresosEntities = result.get(0).getRecaudacionesEntity().getRecaudacionIngresosEntities();
            setRecaudacion(recaudacionIngresosEntities);

            return recaudacionIngresosEntities.stream().filter(r ->result.stream().anyMatch(o -> o.getId().equals(r.getId()))).collect(Collectors.toList());
        }

        return result;
    }

    public void setRecaudacion(List<RecaudacionIngresosEntity> entities) {
        entities.sort((o1, o2) -> {
            if(o1 == null || o2 == null)
                return -1;
            if (o1.getDia() > o2.getDia())
                return 1;
            if (o1.getDia() < o2.getDia())
                return -1;
            if (o1.getDia().equals(o2.getDia()) && o1.getTurno().equalsIgnoreCase("tarde"))
                return 1;
            if (o1.getDia().equals(o2.getDia()) && o1.getTurno().equalsIgnoreCase(o2.getTurno()))
                return 0;

            return -1;
        });

        for (int i = 0; i < entities.size(); i++) {
            try {
                RecaudacionIngresosEntity ingresosEntity = entities.get(i);

                if (i == 0) {
                    ingresosEntity.setRecaudacion(ingresosEntity.getNumeracion().subtract(ingresosEntity.getRecaudacionesEntity().getNumeracionInicio()));
                } else {
                    RecaudacionIngresosEntity ingresosEntityLast = entities.get(i - 1);
                    ingresosEntity.setRecaudacion(ingresosEntity.getNumeracion().subtract(ingresosEntityLast.getNumeracion()));
                }

                if (ingresosEntity.getAnulados() != null)
                    ingresosEntity.setRecaudacion(ingresosEntity.getRecaudacion().subtract(ingresosEntity.getAnulados()));

                ingresosEntity.setLiquido(calculateLiquido(new RecaudacionIngreso(ingresosEntity)));

            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public int getTotal(Map<String, FilterMeta> filterMeta, Long parentId) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM recaudacion_ingresos, recaudaciones, conductores WHERE recaudacion_ingresos.id_recaudacion = recaudaciones.id AND recaudacion_ingresos.id_conductor = conductores.id ");

        if (parentId != null)
            rawQuery.append("AND recaudacion_ingresos.id_recaudacion = :idrecaudacion ");

        Query query = buildFilters(null, filterMeta, rawQuery, null);

        if (parentId != null)
            query.setParameter("idrecaudacion", parentId);

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

                    if (entry.getKey().startsWith("recaudaciones."))
                        rawQuery.append(" AND ").append(entry.getKey()).append(" LIKE ").append(":").append(entry.getKey());
                    else if (entry.getKey().startsWith("conductor."))
                        rawQuery.append(" AND ").append("conductores.nombre").append(" LIKE ").append(":").append(entry.getKey());
                    else
                        rawQuery.append(" AND ").append("recaudacion_ingresos.").append(entry.getKey()).append(" LIKE ").append(":").append(entry.getKey());

                    parameters.put(entry.getKey(), entry.getValue());
                }
            }
        }

        if (sortMeta != null && !sortMeta.isEmpty()) {
            SortMeta sort = sortMeta.entrySet().iterator().next().getValue();

            if (sort.getSortField().startsWith("recaudaciones."))
                rawQuery.append(" ORDER BY ").append("recaudaciones.").append(sort.getSortField()).append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
            else if (sort.getSortField().startsWith("conductor."))
                rawQuery.append(" ORDER BY ").append("conductores.nombre").append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
            else
                rawQuery.append(" ORDER BY ").append("recaudacion_ingresos.").append(sort.getSortField()).append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");

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

    public void update(RecaudacionIngreso recaudacionIngreso) {
        RecaudacionIngresosEntity recaudacionIngresosEntity = em.find(RecaudacionIngresosEntity.class, recaudacionIngreso.getId());
        recaudacionIngresosEntity.setConductorEntity(new ConductorEntity(recaudacionIngreso.getConductor()));
        recaudacionIngresosEntity.setDia(recaudacionIngreso.getDia());
        recaudacionIngresosEntity.setTurno(recaudacionIngreso.getTurno());
        recaudacionIngresosEntity.setNumeracion(recaudacionIngreso.getNumeracion());
        recaudacionIngresosEntity.setAnulados(recaudacionIngreso.getAnulados());
        recaudacionIngresosEntity.setApp(recaudacionIngreso.getApp());
        recaudacionIngresosEntity.setTarjeta(recaudacionIngreso.getTarjeta());
        recaudacionIngresosEntity.setPagos(recaudacionIngreso.getPagos());
        recaudacionIngresosEntity.setRecaudacion(recaudacionIngreso.getRecaudacion());
        recaudacionIngresosEntity.setPagos(recaudacionIngreso.getPagos());
        recaudacionIngresosEntity.setT(recaudacionIngreso.getT());
        recaudacionIngresosEntity.setObservaciones(recaudacionIngreso.getObservaciones());
        recaudacionIngresosEntity.setRecaudacionesIngresosEstadosEntity(new RecaudacionesIngresosEstadosEntity(recaudacionIngreso.getEstado()));
        if(recaudacionIngreso.getEfectivo() == null)
            recaudacionIngresosEntity.setEfectivo(calculateEfectivo(recaudacionIngreso, recaudacionIngreso.getRecaudacionObj()));
        else
            recaudacionIngresosEntity.setEfectivo(recaudacionIngreso.getEfectivo());

        em.merge(recaudacionIngresosEntity);
    }

    public RecaudacionIngresosEntity insert(RecaudacionIngresosEntity recaudacionIngreso, long recaudacionId) {
        RecaudacionesEntity single = recaudacionDbBean.findById(recaudacionId);

        recaudacionIngreso.setRecaudacionesEntity(single);
        List<RecaudacionIngresosEntity> recaudacionIngresosEntities = single.getRecaudacionIngresosEntities();
        recaudacionIngresosEntities.add(recaudacionIngreso);

        if (recaudacionIngreso.getEfectivo() == null) {
            setRecaudacion(recaudacionIngresosEntities);

            BigDecimal liquido = recaudacionIngreso.getLiquido();

            if (recaudacionIngreso.getTarjeta() != null)
                liquido = liquido.subtract(recaudacionIngreso.getTarjeta());

            if (recaudacionIngreso.getApp() != null)
                liquido = liquido.subtract(recaudacionIngreso.getApp());

            recaudacionIngreso.setEfectivo(liquido);
        } else {
            recaudacionIngreso.setEfectivo(recaudacionIngreso.getEfectivo());
        }

        em.persist(recaudacionIngreso);

        return recaudacionIngreso;
    }

    public void delete(Long id) {
        em.remove(em.find(RecaudacionIngresosEntity.class, id));
    }

    public RecaudacionIngresosEntity findById(Long id) {
        return em.find(RecaudacionIngresosEntity.class, id);
    }
}
