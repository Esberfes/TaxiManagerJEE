package database;

import entities.VehicleEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import pojos.Vehicle;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Stateless(name = VehiclesDbBean.BEAN_NAME)
public class VehiclesDbBean {

    public final static String BEAN_NAME = "VehiclesDbBean";

    @Inject
    private Logger logger;

    @Inject
    private LicensesDbBean licensesDbBean;

    @PersistenceContext
    private EntityManager em;

    public List<VehicleEntity> getVehiclesData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder query = new StringBuilder("SELECT vehicles.* FROM vehicles LEFT JOIN licenses l ON (vehicles.license = l.id) WHERE true ");

        buildFilters(filterMeta, query);

        if (!sortMeta.isEmpty()) {
            SortMeta sort = sortMeta.entrySet().iterator().next().getValue();
            query.append(" ORDER BY ").append(sort.getSortField()).append(" ").append(sort.getSortOrder() == SortOrder.DESCENDING ? " DESC " : " ASC ");
        }

        Query stats = em.createNativeQuery(query.toString(), VehicleEntity.class);

        if (pageSize > 0)
            stats = stats.setMaxResults(pageSize).setFirstResult(first);

        return stats.getResultList();
    }

    public int getTotalVehicles(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM vehicles LEFT JOIN licenses l ON (vehicles.license = l.id) WHERE true ");

        buildFilters(filterMeta, rawQuery);

        Query query = em.createNativeQuery(rawQuery.toString());

        return ((BigInteger) query.getSingleResult()).intValue();
    }

    private void buildFilters(Map<String, FilterMeta> filterMeta, StringBuilder rawQuery) {
        if (filterMeta != null) {
            for (Map.Entry<String, FilterMeta> entry : filterMeta.entrySet()) {
                if (entry.getValue().getFilterValue() != null && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue()))
                        && entry.getValue().getFilterValue() != null && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue()))) {
                    if (entry.getKey().equalsIgnoreCase("license"))
                        rawQuery.append(" AND ").append(" code ").append("LIKE ").append(getFilterFieldValue(entry.getValue()));
                    else
                        rawQuery.append(" AND ").append(entry.getKey()).append(" LIKE ").append(getFilterFieldValue(entry.getValue()));
                }
            }
        }
    }

    private static Object getFilterFieldValue(FilterMeta filterField) {
        switch (filterField.getFilterMatchMode()) {
            default:
                return "'%" + filterField.getFilterValue() + "%'";
        }
    }

    public VehicleEntity insertVehicle(Vehicle vehicle) {
        VehicleEntity vehicleEntity = new VehicleEntity(vehicle);
        if (vehicle.getLicense() != null && vehicle.getLicense().getCode() != null)
            vehicleEntity.setLicense(licensesDbBean.getSingleLicenses(vehicle.getLicense().getId()));

        em.persist(vehicleEntity);

        return vehicleEntity;
    }

    public void truncate() {
        em.createNativeQuery("DELETE FROM vehicles WHERE true").executeUpdate();
    }

    public VehicleEntity insertVehicle(String name, String valid, Long id) {
        return null;
    }

    public void deleteVehicle(Long id) {
        em.remove(getSingleVehicle(id));
    }

    private VehicleEntity getSingleVehicle(Long id) {
        return em.find(VehicleEntity.class, id);
    }

    public void update(Vehicle vehicle) {
        VehicleEntity vehicleEntity = getSingleVehicle(vehicle.getId());
        vehicleEntity.setBrand(vehicle.getBrand());
        vehicleEntity.setRegistration(vehicle.getRegistration());

        em.merge(vehicleEntity);
    }
}
