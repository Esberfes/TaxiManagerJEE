package com.taxi.business;

import com.taxi.database.LicenciasDbBean;
import com.taxi.datamodels.LazyLoad;
import com.taxi.entities.LicenciasEntity;
import com.taxi.pojos.Licencia;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

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
        if (codigo == null)
            return null;

        LicenciasEntity licenciasEntity = licenciasDbBean.findSingleByCodigo(codigo);

        if (licenciasEntity == null)
            return null;

        return new Licencia(licenciasEntity);
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
