package com.taxi.business;

import com.taxi.database.RecaudacionIngresoDbBean;
import com.taxi.datamodels.LazyLoad;
import com.taxi.entities.RecaudacionIngresosEntity;
import com.taxi.entities.RecaudacionesEntity;
import com.taxi.faces.SessionData;
import com.taxi.pojos.TaxiFilterMeta;
import com.taxi.singletons.TaxiLogger;
import com.taxi.utils.TransactionUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;
import org.primefaces.model.SortMeta;
import com.taxi.pojos.Recaudacion;
import com.taxi.pojos.RecaudacionIngreso;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = RecaudacionIngresosBean.BEAN_NAME)
@Interceptors(TaxiLogger.class)
public class RecaudacionIngresosBean implements LazyLoad<RecaudacionIngreso> {

    public static final String BEAN_NAME = "RecaudacionIngresosBean";

    @Inject
    private RecaudacionIngresoDbBean recaudacionIngresoDbBean;

    @Inject
    private SessionData sessionData;

    @Override
    public List<RecaudacionIngreso> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        filterMeta.put("recaudaciones.mes", new TaxiFilterMeta("recaudaciones.mes", sessionData.getMes(), MatchMode.EXACT));
        filterMeta.put("recaudaciones.ano", new TaxiFilterMeta("recaudaciones.ano", sessionData.getAno(), MatchMode.EXACT));

        return getData(first, pageSize, sortMeta, filterMeta, null);
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        filterMeta.put("recaudaciones.mes",new TaxiFilterMeta("recaudaciones.mes", sessionData.getMes(), MatchMode.EXACT));
        filterMeta.put("recaudaciones.ano", new TaxiFilterMeta("recaudaciones.ano", sessionData.getAno(), MatchMode.EXACT));
        return getTotal(filterMeta, null);
    }

    @Override
    public List<RecaudacionIngreso> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, Long parentId) {
        filterMeta.put("recaudaciones.mes",new TaxiFilterMeta("recaudaciones.mes", sessionData.getMes(), MatchMode.EXACT));
        filterMeta.put("recaudaciones.ano", new TaxiFilterMeta("recaudaciones.ano", sessionData.getAno(), MatchMode.EXACT));
        return recaudacionIngresoDbBean.getData(first, pageSize, sortMeta, filterMeta, parentId).stream().map(RecaudacionIngreso::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta, Long parentId) {
        filterMeta.put("recaudaciones.mes",new TaxiFilterMeta("recaudaciones.mes", sessionData.getMes(), MatchMode.EXACT));
        filterMeta.put("recaudaciones.ano", new TaxiFilterMeta("recaudaciones.ano", sessionData.getAno(), MatchMode.EXACT));
        return recaudacionIngresoDbBean.getTotal(filterMeta, parentId);
    }

    @Override
    public RecaudacionIngreso findById(Long id) {
        return new RecaudacionIngreso(recaudacionIngresoDbBean.findById(id));
    }

    public void update(RecaudacionIngreso recaudacionIngreso) {
        TransactionUtils.executeInTransaction(() -> {
            recaudacionIngresoDbBean.update(recaudacionIngreso);
            return null;
        });
    }

    public RecaudacionIngreso insert(RecaudacionIngreso ingreso, long recaudacionId) {
        RecaudacionIngresosEntity recaudacionIngresosEntity = new RecaudacionIngresosEntity(ingreso);

        return TransactionUtils.executeInTransaction(() -> new RecaudacionIngreso(recaudacionIngresoDbBean.insert(recaudacionIngresosEntity, recaudacionId)));
    }

    public void delete(Long id) {
        recaudacionIngresoDbBean.delete(id);
    }
}
