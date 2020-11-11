package database;

import entities.RecaudacionIngresosEntity;
import entities.RecaudacionesEntity;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
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

    @Inject
    private RecaudacionIngresoDbBean recaudacionIngresoDbBean;

    public List<RecaudacionesEntity> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT * FROM recaudaciones, licencias WHERE recaudaciones.id_licencia = licencias.id ");

        Query query = buildFilters(sortMeta, filterMeta, rawQuery, RecaudacionesEntity.class);

        if (pageSize > 0)
            query = query.setMaxResults(pageSize).setFirstResult(first);

        List<RecaudacionesEntity> data = query.getResultList();

        for (RecaudacionesEntity entity : data) {
            RecaudacionIngresosEntity ingresosEntity = null;

            for (RecaudacionIngresosEntity ingreso : entity.getRecaudacionIngresosEntities()) {
                if (ingresosEntity == null)
                    ingresosEntity = ingreso;
                else if (ingreso.getDia() > ingresosEntity.getDia())
                    ingresosEntity = ingreso;
                else if (ingreso.getDia().equals(ingresosEntity.getDia()) && "mañana".equalsIgnoreCase(ingresosEntity.getTurno()))
                    ingresosEntity = ingreso;
            }

            if (ingresosEntity != null)
                entity.setNumeracionFin(ingresosEntity.getNumeracion());

            recaudacionIngresoDbBean.setRecaudacion(entity.getRecaudacionIngresosEntities());
        }


        return data;
    }

    public int getTotal(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM recaudaciones, licencias WHERE recaudaciones.id_licencia = licencias.id ");

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

                    if (entry.getKey().equalsIgnoreCase("licencia.codigo")) {
                        rawQuery.append(" AND ").append(" licencias.codigo ").append("LIKE ").append(":").append(entry.getKey());
                    } else {
                        rawQuery.append(" AND ").append("recaudaciones.").append(entry.getKey()).append(" LIKE ").append(":").append(entry.getKey());
                    }


                    parameters.put(entry.getKey(), entry.getValue());
                }
            }
        }

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

    public RecaudacionesEntity findSingle(Long id) {
        return em.find(RecaudacionesEntity.class, id);
    }
}
