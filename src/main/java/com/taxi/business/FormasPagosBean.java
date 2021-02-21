package com.taxi.business;

import com.taxi.database.FormasPagosDbBean;
import com.taxi.datamodels.LazyLoad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import com.taxi.pojos.FormasPago;

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

    @Override
    public List<FormasPago> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, Long parentId) {
        return null;
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta, Long parentId) {
        return 0;
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

    public List<FormasPago> findByName(String value) {
        return formasPagosDbBean.findByName(value).stream().map(FormasPago::new).collect(Collectors.toList());
    }

    public FormasPago findSingleByName(String value) {
        return new FormasPago(formasPagosDbBean.findSingleByName(value));
    }
}
