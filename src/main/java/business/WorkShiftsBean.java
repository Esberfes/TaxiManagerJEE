package business;

import database.WorkShiftsDbBean;
import datamodels.LazyLoad;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.WorkShift;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = WorkShiftsBean.BEAN_NAME)
public class WorkShiftsBean implements LazyLoad<WorkShift> {

    public static final String BEAN_NAME = "WorkShiftsBean";

    @Inject
    private WorkShiftsDbBean workShiftsDbBean;

    @Override
    public List<WorkShift> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return workShiftsDbBean.getWorkShiftData(first, pageSize, sortMeta, filterMeta).stream().map(e -> new WorkShift(e)).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return workShiftsDbBean.getWorkShiftTotal(filterMeta);
    }

    public void deleteWorkShift(Long id) {
        workShiftsDbBean.deleteWorkShift(id);
    }
}
