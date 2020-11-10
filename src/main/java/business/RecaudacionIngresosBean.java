package business;

import database.RecaudacionIngresoDbBean;
import datamodels.LazyLoad;
import entities.RecaudacionIngresosEntity;
import entities.RecaudacionesEntity;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.Recaudacion;
import pojos.RecaudacionIngreso;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = RecaudacionIngresosBean.BEAN_NAME)
public class RecaudacionIngresosBean implements LazyLoad<RecaudacionIngreso> {

    public static final String BEAN_NAME = "RecaudacionIngresosBean";

    @Inject
    private RecaudacionIngresoDbBean recaudacionIngresoDbBean;

    @Override
    public List<RecaudacionIngreso> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return getData(first, pageSize, sortMeta, filterMeta, null);
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return getTotal(filterMeta, null);
    }

    @Override
    public List<RecaudacionIngreso> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, Long parentId) {
        return recaudacionIngresoDbBean.getData(first, pageSize, sortMeta, filterMeta, parentId).stream().map(RecaudacionIngreso::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta, Long parentId) {
        return recaudacionIngresoDbBean.getTotal(filterMeta, parentId);
    }

    public void update(RecaudacionIngreso recaudacionIngreso) {
        recaudacionIngresoDbBean.update(recaudacionIngreso);
    }

    public RecaudacionIngreso insert(RecaudacionIngreso ingreso, Recaudacion selectedRecaudacion) {
        RecaudacionIngresosEntity recaudacionIngresosEntity = new RecaudacionIngresosEntity(ingreso);
        recaudacionIngresosEntity.setRecaudacionesEntity(new RecaudacionesEntity(selectedRecaudacion));

        return new RecaudacionIngreso(recaudacionIngresoDbBean.insert(recaudacionIngresosEntity));
    }

    public void delete(Long id) {
        recaudacionIngresoDbBean.delete(id);
    }
}
