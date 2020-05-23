package business;

import database.RecaudacionDbBean;
import datamodels.LazyLoad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.Recaudacion;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = RecaudacionBean.BEAN_NAME)
public class RecaudacionBean implements LazyLoad<Recaudacion> {

    public static final String BEAN_NAME = "RecaudacionBean";

    @Inject
    private RecaudacionDbBean recaudacionDbBean;

    @Override
    public List<Recaudacion> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return recaudacionDbBean.getData(first, pageSize, sortMeta, filterMeta).stream().map(Recaudacion::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return recaudacionDbBean.getTotal(filterMeta);
    }

    public void update(Recaudacion recaudacion) {
        recaudacionDbBean.update(recaudacion);
    }

    public void insert(Recaudacion recaudacion) {
        recaudacionDbBean.insert(recaudacion);
    }

    public void delete(Long id) {
        recaudacionDbBean.delete(id);
    }
}
