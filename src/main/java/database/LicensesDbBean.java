package database;

import entities.LicenseEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.License;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Stateless(name = LicensesDbBean.BEAN_NAME)
public class LicensesDbBean {

    public final static String BEAN_NAME = "LicensesDbBean";

    @Inject
    private Logger logger;

    @PersistenceContext
    private EntityManager em;

    public LicenseEntity getSingleLicenses(Long id) {
        return em.find(LicenseEntity.class, id);
    }

    public List<LicenseEntity> findLicensesByCod(String code) {
        String rawQuery = "SELECT * FROM licenses WHERE code LIKE '%" + code + "%' ORDER BY code ASC";
        Query query = em.createNativeQuery(rawQuery, LicenseEntity.class);

        return query.setMaxResults(10).getResultList();
    }

    public List<LicenseEntity> getLicenseData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        StringBuilder query = new StringBuilder("SELECT * FROM licenses WHERE true ");

        buildFilters(filterMeta, query);

        Query stats = em.createNativeQuery(query.toString(), LicenseEntity.class);

        if (pageSize > 0)
            stats = stats.setMaxResults(pageSize).setFirstResult(first);

        return stats.getResultList();
    }

    public int getLicenseTotal(Map<String, FilterMeta> filterMeta) {
        StringBuilder rawQuery = new StringBuilder("SELECT COUNT(*) FROM licenses WHERE true ");

        buildFilters(filterMeta, rawQuery);

        Query query = em.createNativeQuery(rawQuery.toString());

        return ((BigInteger) query.getSingleResult()).intValue();
    }

    public LicenseEntity insertLicense(License license) {
        LicenseEntity licenseEntity = new LicenseEntity(license);
        em.persist(licenseEntity);

        return licenseEntity;
    }

    public void deleteLicense(Long id) {
        em.remove(getSingleLicenses(id));
    }

    public void truncate() {
        em.createNativeQuery("DELETE FROM licenses WHERE true").executeUpdate();
    }

    private void buildFilters(Map<String, FilterMeta> filterMeta, StringBuilder rawQuery) {
        if (filterMeta != null)
            for (Map.Entry<String, FilterMeta> entry : filterMeta.entrySet())
                if (entry.getValue().getFilterValue() != null && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue())) && entry.getValue().getFilterValue() != null && StringUtils.isNotBlank(String.valueOf(entry.getValue().getFilterValue())))
                    rawQuery.append(" AND ").append(entry.getKey()).append(" LIKE ").append(getFilterFieldValue(entry.getValue()));
    }

    private static Object getFilterFieldValue(FilterMeta filterField) {
        switch (filterField.getFilterMatchMode()) {
            default:
                return "'%" + filterField.getFilterValue() + "%'";
        }
    }

    public void updateLicense(License license) {
        LicenseEntity licenseEntity = getSingleLicenses(license.getId());
        licenseEntity.setCode(license.getCode());

        em.merge(licenseEntity);
    }
}
