package business;

import datamodels.LazyLoad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.FormasPago;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Map;

@LocalBean
@Stateless(name = FormasPagosBean.BEAN_NAME)
public class FormasPagosBean implements LazyLoad<FormasPago> {

    public static final String BEAN_NAME = "FormasPagosBean";

    @Override
    public List<FormasPago> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return null;
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return 0;
    }
}
