package business;

import database.ConceptosGastosDbBean;
import datamodels.LazyLoad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.ConceptosGastos;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = ConceptosGastosBean.BEAN_NAME)
public class ConceptosGastosBean implements LazyLoad<ConceptosGastos> {

    public static final String BEAN_NAME = "ConceptosGastosBean";

    @Inject
    private ConceptosGastosDbBean conceptosGastosDbBean;

    @Override
    public List<ConceptosGastos> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return conceptosGastosDbBean.getData(first, pageSize, sortMeta, filterMeta).stream().map(ConceptosGastos::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return conceptosGastosDbBean.getTotal(filterMeta);
    }

    public void update(ConceptosGastos conceptosGastos) {

    }

    public void delete(Long id) {

    }
}
