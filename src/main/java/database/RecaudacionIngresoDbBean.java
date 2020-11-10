package database;

import entities.RecaudacionIngresosEntity;
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
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.BigDecimalUtils.percentage;
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
        StringBuilder rawQuery = new StringBuilder("SELECT * FROM recaudacion_ingresos, recaudaciones, conductores WHERE recaudacion_ingresos.id_recaudacion = recaudaciones.id AND recaudacion_ingresos.id_conductor = conductores.id ");

        if (parentId != null)
            rawQuery.append("AND recaudacion_ingresos.id_recaudacion = :idrecaudacion ");

        Query query = buildFilters(sortMeta, filterMeta, rawQuery, RecaudacionIngresosEntity.class);

        if (parentId != null)
            query.setParameter("idrecaudacion", parentId);

        if (pageSize > 0)
            query = query.setMaxResults(pageSize).setFirstResult(first);


        List<RecaudacionIngresosEntity> result = query.getResultList();
        setRecaudacion(result);

        return result;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void setRecaudacion(List<RecaudacionIngresosEntity> entities) {
        for (RecaudacionIngresosEntity entity : entities) {
            try {
                String query = "SELECT * FROM recaudacion_ingresos WHERE  id != :id AND dia <= :dia ORDER BY dia DESC , turno  DESC LIMIT 1";
                List<RecaudacionIngresosEntity> found = (List<RecaudacionIngresosEntity>) em.createNativeQuery(query, RecaudacionIngresosEntity.class)
                        .setParameter("id", entity.getId())
                        .setParameter("dia", entity.getDia())
                        .getResultList();

                if (!found.isEmpty()
                        && !("mañana".equalsIgnoreCase(entity.getTurno()) && entity.getDia().equals(found.get(0).getDia()))) {
                    BigDecimal rec = entity.getNumeracion().subtract(found.get(0).getNumeracion());
                    if (rec.doubleValue() < 0)
                        entity.setRecaudacion(entity.getNumeracion());
                    else
                        entity.setRecaudacion(rec);
                } else
                    entity.setRecaudacion(entity.getNumeracion());

                BigDecimal total = new BigDecimal("0.00");

                if (entity.getAnulados() != null)
                    total = entity.getRecaudacion().subtract(entity.getAnulados());

                BigDecimal liquido = total.add(entity.getConductorEntity().getComplementoIva());

                if (entity.getRecaudacion().doubleValue() <= entity.getConductorEntity().getT065().doubleValue()) {
                    liquido = liquido.add(percentage(entity.getRecaudacion(), new BigDecimal(35)));
                } else if (entity.getRecaudacion().doubleValue() <= entity.getConductorEntity().getT060().doubleValue()) {
                    liquido = liquido.add(percentage(entity.getRecaudacion(), new BigDecimal(40)));
                } else if (entity.getRecaudacion().doubleValue() <= entity.getConductorEntity().getT055().doubleValue()) {
                    liquido = liquido.add(percentage(entity.getRecaudacion(), new BigDecimal(45)));
                } else {
                    liquido = liquido.add(percentage(entity.getRecaudacion(), new BigDecimal(50)));
                }

                entity.setLiquido(liquido);

            } catch (Throwable ignore) {
                System.out.println();
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
        em.remove(em.find(RecaudacionIngresosEntity.class, id));
    }
}
