package business;

import database.EstadosIngresoDbBean;
import datamodels.LazyLoad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.EstadosIngreso;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = EstadosIngresosBean.BEAN_NAME)
public class EstadosIngresosBean implements LazyLoad<EstadosIngreso> {

    public static final String BEAN_NAME = "EstadosIngresosBean";

    @Inject
    private EstadosIngresoDbBean estadosIngresoDbBean;

    @Override
    public List<EstadosIngreso> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return estadosIngresoDbBean.getData(first, pageSize, sortMeta, filterMeta).stream().map(EstadosIngreso::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return estadosIngresoDbBean.getTotal(filterMeta);
    }

    @Override
    public List<EstadosIngreso> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, Long parentId) {
        return null;
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta, Long parentId) {
        return 0;
    }

    public void update(EstadosIngreso estadosIngreso) {
        estadosIngresoDbBean.update(estadosIngreso);
    }

    public void delete(Long id) {
        estadosIngresoDbBean.delete(id);
    }

    public void insert(EstadosIngreso estadosIngreso) {
        estadosIngresoDbBean.insert(estadosIngreso);
    }

    public EstadosIngreso findSingleByName(String name) {
        return new EstadosIngreso(estadosIngresoDbBean.findSingleByName(name));
    }

    public List<EstadosIngreso> findByName(String value) {
        return estadosIngresoDbBean.findByName(value).stream().map(EstadosIngreso::new).collect(Collectors.toList());
    }
}
