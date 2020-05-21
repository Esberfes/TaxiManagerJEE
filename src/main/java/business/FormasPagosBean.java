package business;

import database.FormasPagosDbBean;
import datamodels.LazyLoad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.FormasPago;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = FormasPagosBean.BEAN_NAME)
public class FormasPagosBean implements LazyLoad<FormasPago> {

    public static final String BEAN_NAME = "FormasPagosBean";

    @Inject
    private FormasPagosDbBean formasPagosDbBean;

    @Override
    public List<FormasPago> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return formasPagosDbBean.getData(first, pageSize, sortMeta, filterMeta).stream().map(FormasPago::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return formasPagosDbBean.getTotal(filterMeta);
    }

    public void update(FormasPago formasPago) {
        formasPagosDbBean.update(formasPago);
    }

    public void delete(Long id) {
        formasPagosDbBean.delete(id);
    }

    public void insert(FormasPago formasPago) {
        formasPagosDbBean.insert(formasPago);
    }
}
