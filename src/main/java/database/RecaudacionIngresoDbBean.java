package database;

import entities.ConductorEntity;
import entities.RecaudacionIngresosEntity;
import entities.RecaudacionesIngresosEstadosEntity;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import pojos.RecaudacionIngreso;

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

@Stateless(name = RecaudacionIngresoDbBean.BEAN_NAME)
public class RecaudacionIngresoDbBean {

    public final static String BEAN_NAME = "RecaudacionIngresoDbBean";

    @PersistenceContext
    private EntityManager em;

    @Inject
    private EstadosIngresoDbBean estadosIngresoDbBean;

    @Inject
    private ConductoresDbBean conductoresDbBean;

    public List<RecaudacionIngresosEntity> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, Long parentId) {
        StringBuilder rawQuery = new StringBuilder("SELECT * FROM recaudacion_ingresos WHERE recaudacion_ingresos.id_recaudacion = :idrecaudacion ");

        Query query = buildFilters(sortMeta, filterMeta, rawQuery, RecaudacionIngresosEntity.class);

        query.setParameter("idrecaudacion", parentId);

        if (pageSize > 0)
            query = query.setMaxResults(pageSize).setFirstResult(first);

        return query.getResultList();
    }

    public int getTotal(Map<String, FilterMeta> filterMeta, Long parentId) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM recaudacion_ingresos WHERE recaudacion_ingresos.id_recaudacion = :idrecaudacion ");

        Query query = buildFilters(null, filterMeta, rawQuery, null);

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

                    rawQuery.append(" AND ").append("recaudacion_ingresos.").append(entry.getKey()).append(" LIKE ").append(":").append(entry.getKey());

                    parameters.put(entry.getKey(), entry.getValue());
                }
            }
        }

        if (sortMeta != null && !sortMeta.isEmpty()) {
            SortMeta sort = sortMeta.entrySet().iterator().next().getValue();

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
        recaudacionIngresosEntity.setConductorEntity(conductoresDbBean.getSingleByName(recaudacionIngreso.getConductor().getNombre()));
        recaudacionIngresosEntity.setDia(recaudacionIngreso.getDia());
        recaudacionIngresosEntity.setTurno(recaudacionIngreso.getTurno());
        recaudacionIngresosEntity.setNumeracion(recaudacionIngreso.getNumeracion());
        recaudacionIngresosEntity.setAnulados(recaudacionIngreso.getAnulados());
        recaudacionIngresosEntity.setAnulados(recaudacionIngreso.getAnulados());
        recaudacionIngresosEntity.setRecaudacion(recaudacionIngreso.getRecaudacion());
        recaudacionIngresosEntity.setObservaciones(recaudacionIngreso.getObservaciones());
        recaudacionIngresosEntity.setRecaudacionesIngresosEstadosEntity(estadosIngresoDbBean.findSingleByName(recaudacionIngreso.getEstado().getNombre()));

        em.merge(recaudacionIngresosEntity);
    }

    public RecaudacionIngresosEntity insert(RecaudacionIngresosEntity recaudacionIngreso) {
        em.persist(recaudacionIngreso);

        return recaudacionIngreso;
    }

    public void delete(Long id) {
       em.remove( em.find(RecaudacionIngresosEntity.class, id));
    }
}
