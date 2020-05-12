package business;

import database.TiposGastoDbBean;
import datamodels.LazyLoad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.TiposGasto;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = TiposGastosBean.BEAN_NAME)
public class TiposGastosBean implements LazyLoad<TiposGasto> {

    public static final String BEAN_NAME = "TiposGastosBean";

    @Inject
    private TiposGastoDbBean tiposGastoDbBean;

    @Override
    public List<TiposGasto> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return tiposGastoDbBean.getData(first, pageSize, sortMeta, filterMeta).stream().map(TiposGasto::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return tiposGastoDbBean.getTotal(filterMeta);
    }

    public void delete(Long id) {

    }

    public void update(TiposGasto tiposGasto) {

    }
}
