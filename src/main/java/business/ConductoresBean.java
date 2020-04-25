package business;

import database.ConductoresDbBean;
import datamodels.LazyLoad;
import entities.ConductorEntity;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.Conductor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = ConductoresBean.BEAN_NAME)
public class ConductoresBean implements LazyLoad<Conductor> {

    public static final String BEAN_NAME = "ConductoresBean";

    @Inject
    private ConductoresDbBean conductoresDbBean;

    @Override
    public List<Conductor> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return conductoresDbBean.getEmployeeData(first, pageSize, sortMeta, filterMeta).stream().map(Conductor::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return conductoresDbBean.getTotalEmployees(filterMeta);
    }

    public void insertConductor(String name) {
        conductoresDbBean.insertConductor(new Conductor(name));
    }

    public void update(Conductor conductor) {
        conductoresDbBean.updateEmployee(conductor);
    }

    public void deleteEmployee(Long id) {
        conductoresDbBean.deleteEmployee(id);
    }

    public List<Conductor> findEmployeesByFullName(String name) {
        return conductoresDbBean.findEmployeesByFullName(name).stream().map(Conductor::new).collect(Collectors.toList());
    }

    public ConductorEntity insertConductor(Conductor conductor) {
        return conductoresDbBean.insertConductor(conductor);
    }

    public void truncate() {
        conductoresDbBean.truncate();
    }
}
