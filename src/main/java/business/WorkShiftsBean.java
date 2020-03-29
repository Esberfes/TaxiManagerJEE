package business;

import datamodels.LazyLoad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.WorkShift;
import singletons.MockerSingleton;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@LocalBean
@Stateless(name = WorkShiftsBean.BEAN_NAME)
public class WorkShiftsBean implements LazyLoad<WorkShift> {

    public static final String BEAN_NAME = "WorkShiftsBean";

    @Inject
    private MockerSingleton mockerSingleton;

    @Override
    public List<WorkShift> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return mockerSingleton.getWorkShiftData(first, pageSize, sortMeta, filterMeta);
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return mockerSingleton.getWorkShiftTotal(filterMeta);
    }
}
