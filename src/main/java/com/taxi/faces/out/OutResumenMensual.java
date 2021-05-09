package com.taxi.faces.out;

import com.taxi.business.GastosBean;
import com.taxi.business.LicenciasBean;
import com.taxi.business.RecaudacionBean;
import com.taxi.faces.SessionData;
import com.taxi.pojos.Gasto;
import com.taxi.pojos.Recaudacion;
import com.taxi.pojos.RecaudacionIngreso;
import com.taxi.pojos.out.ResumenMes;
import com.taxi.singletons.TaxiLogger;
import org.primefaces.model.FilterMeta;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import static com.taxi.utils.RecaudacionUtils.calculateSalario;

@ViewScoped
@Named(OutResumenMensual.BEAN_NAME)
public class OutResumenMensual implements Serializable {

    public static final String BEAN_NAME = "OutResumenMensual";


    @Inject
    private transient TaxiLogger logger;

    @Inject
    private RecaudacionBean recaudacionBean;

    @Inject
    private LicenciasBean licenciasBean;

    @Inject
    private GastosBean gastosBean;

    @Inject
    private SessionData sessionData;

    private List<ResumenMes> resumen;

    @PostConstruct
    public void init() {
        refresh();
    }

    public void refresh() {
        resumen = new ArrayList<>();
        Map<String, FilterMeta> filterMeta = new HashMap<>();
        filterMeta.put("ano", new FilterMeta("ano", sessionData.getAno()));
        filterMeta.put("mes", new FilterMeta("mes", sessionData.getMes()));
        ResumenMes resumenMes = new ResumenMes();

        List<Gasto> gastos = gastosBean.getData(0, Integer.MAX_VALUE, new HashMap<>(), filterMeta);
        List<Recaudacion> recaudaciones = recaudacionBean.getData(0, Integer.MAX_VALUE, new HashMap<>(), filterMeta);

        for(Recaudacion recaudacion : recaudaciones) {
            if(recaudacion.getRecaudacionIngresos() != null) {
                for(RecaudacionIngreso recaudacionIngreso : recaudacion.getRecaudacionIngresos()) {
                    resumenMes.setRecaudacion(resumenMes.getRecaudacion().add(recaudacionIngreso.getRecaudacion()));
                    resumenMes.setSalarios(resumenMes.getSalarios().add(calculateSalario(recaudacionIngreso.getConductor(), recaudacionIngreso)));
                }
            }
        }
        for(Gasto gasto : gastos) {
            if(gasto.getTipoGasto().getEs_operacional()) {
                resumenMes.setGastosOp(resumenMes.getGastosOp().add(gasto.getImporte()));
            }
        }

        resumenMes.setBeneficioOp(resumenMes.getRecaudacion().subtract(resumenMes.getGastosOp()).subtract(resumenMes.getSalarios()));

        resumen.add(resumenMes);
    }

    public List<ResumenMes> getResumen() {
        return resumen;
    }

    public void setResumen(List<ResumenMes> resumen) {
        this.resumen = resumen;
    }
}
