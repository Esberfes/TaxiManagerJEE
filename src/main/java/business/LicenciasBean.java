package business;

import database.LicenciasDbBean;
import datamodels.LazyLoad;
import entities.LicenciasEntity;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import pojos.Licencia;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@LocalBean
@Stateless(name = LicenciasBean.BEAN_NAME)
public class LicenciasBean implements LazyLoad<Licencia> {

    public static final String BEAN_NAME = "LicenciasBean";

    @Inject
    private LicenciasDbBean licenciasDbBean;

    @Override
    public List<Licencia> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        return licenciasDbBean.getData(first, pageSize, sortMeta, filterMeta).stream().map(Licencia::new).collect(Collectors.toList());
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta) {
        return licenciasDbBean.getTotal(filterMeta);
    }

    @Override
    public List<Licencia> getData(int first, int pageSize, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta, Long parentId) {
        return null;
    }

    @Override
    public int getTotal(Map<String, FilterMeta> filterMeta, Long parentId) {
        return 0;
    }

    public List<Licencia> findByCodigo(Integer codigo) {
        return licenciasDbBean.findByCodigo(codigo).stream().map(Licencia::new).collect(Collectors.toList());
    }

    public Licencia findSingleByCodigo(Integer codigo) {
        return new Licencia(licenciasDbBean.findSingleByCodigo(codigo));
    }

    public void update(Licencia licencia) {
        licenciasDbBean.update(licencia);
    }

    public void delete(Long id) {
        licenciasDbBean.delete(id);
    }

    public LicenciasEntity insert(Licencia licencia) {
        return licenciasDbBean.insert(licencia);
    }

    public void truncate() {
        licenciasDbBean.truncate();
    }
}
