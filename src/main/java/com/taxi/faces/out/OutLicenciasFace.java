package com.taxi.faces.out;

import com.taxi.business.GastosBean;
import com.taxi.business.LicenciasBean;
import com.taxi.business.RecaudacionBean;
import com.taxi.faces.SessionData;
import com.taxi.pojos.*;
import com.taxi.singletons.TaxiLogger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.MatchMode;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@ViewScoped
@Named(OutLicenciasFace.BEAN_NAME)
public class OutLicenciasFace implements Serializable {

    public static final String BEAN_NAME = "OutLicenciasFace";

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

    private  Recaudacion recaudacion;
    private int totaServicios;
    private int totalKilometros;
    private List<RecaudacionIngreso> recaudacionIngresos;
    private List<Gasto> gastos;
    private Licencia licenciaObj;
    private BigDecimal totalLiquido;
    private BigDecimal totalRecaudacion;
    private BigDecimal totalGastos;
    private Integer licencia;

    @PostConstruct
    public void init() {
        recaudacionIngresos = new LinkedList<>();
        gastos = new LinkedList<>();
        totalLiquido = new BigDecimal("0.00");
        totalRecaudacion = new BigDecimal("0.00");
        totalGastos = new BigDecimal("0.00");
        emptyIngresos();
    }

    public void refresh() {
        try {
            licenciaObj = licenciasBean.findSingleByCodigo(licencia);
            recaudacion = null;
            recaudacionIngresos = new LinkedList<>();
            gastos = new LinkedList<>();
            totalLiquido = new BigDecimal("0.00");
            totalRecaudacion = new BigDecimal("0.00");
            totalGastos = new BigDecimal("0.00");
            totalKilometros= 0;
            totaServicios = 0;

            calculateIngresos();
            calculateGastos();

            calculateTotalRecaudacion();
            calculateTotalGastos();
            calculateTotalLiquido();
            calculateTotaServicios();
            calculateTotaKilometros();
        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, new Object(){}.getClass().getEnclosingMethod().getName(), e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error(new Object(){}.getClass().getEnclosingMethod().getName(), e);
            emptyIngresos();
        }
    }

    private void emptyIngresos() {
        this.recaudacionIngresos = new LinkedList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.MONTH, sessionData.getMes() - 1);
        calendar.set(Calendar.YEAR, sessionData.getAno());
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysInMonth; i++) {
            this.recaudacionIngresos.add(RecaudacionIngreso.createEmpty(i, "mañana"));
            this.recaudacionIngresos.add(RecaudacionIngreso.createEmpty(i, "tarde"));
        }
    }

    private void calculateGastos() {
        if (licenciaObj != null) {
            Map<String, FilterMeta> filterMeta = new HashMap<>();
           filterMeta.put("ano", new TaxiFilterMeta("recaudaciones.ano", sessionData.getAno(), MatchMode.EXACT));
            filterMeta.put("mes", new TaxiFilterMeta("recaudaciones.mes", sessionData.getMes(), MatchMode.EXACT));
            filterMeta.put("id_licencia", new TaxiFilterMeta("gastos.id_licencia", licenciaObj.getId(), MatchMode.EXACT));
            filterMeta.put("es_operacional", new TaxiFilterMeta("tipos_gastos.es_operacional", 1, MatchMode.EXACT));

            Map<String, SortMeta> sortMeta = new HashMap<>();
            sortMeta.put("mes", new SortMeta("mes", "mes", SortOrder.ASCENDING, null));

            this.gastos = gastosBean.getData(0, 10000, sortMeta, filterMeta);

           this.gastos.addAll( gastosBean.getGastosComunes(sessionData.getMes(), sessionData.getAno(), licenciaObj.getId()));

        }
    }

    private void calculateIngresos() {
        emptyIngresos();
        if (licenciaObj != null) {
            Map<String, FilterMeta> filterMeta = new HashMap<>();
            filterMeta.put("id_licencia", new TaxiFilterMeta("id_licencia", licenciaObj.getId(), MatchMode.EXACT));

            Map<String, SortMeta> sortMeta = new HashMap<>();
            sortMeta.put("mes", new SortMeta("mes", "mes", SortOrder.ASCENDING, null));

            List<Recaudacion> recaudaciones = recaudacionBean.getData(0, 10000, sortMeta, filterMeta);
            recaudacion = recaudaciones.get(0);
            List<RecaudacionIngreso> recaudacionIngresos = recaudacion.getRecaudacionIngresos();

            for (RecaudacionIngreso recaudacionIngreso : recaudacionIngresos) {

                this.recaudacionIngresos.stream()
                        .filter(r -> r.getDia().equals(recaudacionIngreso.getDia())
                                && r.getTurno().equals(recaudacionIngreso.getTurno()))
                        .findFirst()
                        .ifPresent(other -> other.replace(recaudacionIngreso));
            }
        }
    }

    public void calculateTotalGastos() {
        BigDecimal result = new BigDecimal("0.00");

        for (Gasto gasto : gastos) {
            result = result.add(gasto.getImporte());
        }

        totalGastos = result;
    }

    public void calculateTotalRecaudacion() {
        BigDecimal result = new BigDecimal("0.00");

        for (RecaudacionIngreso recaudacionIngreso : recaudacionIngresos) {
            if(recaudacionIngreso.getRecaudacion() != null)
                result = result.add(recaudacionIngreso.getRecaudacion());
        }

        totalRecaudacion =  result;
    }

    public void calculateTotalLiquido() {
        BigDecimal result = new BigDecimal("0.00");

        for (RecaudacionIngreso recaudacionIngreso : recaudacionIngresos) {
            if(recaudacionIngreso.getLiquido() != null)
                result = result.add(recaudacionIngreso.getLiquido());
        }

        totalLiquido = result;
    }

    public void calculateTotaServicios() {
        if(recaudacion == null || recaudacion.getServicios_inicio() == null || recaudacion.getServicios_fin() == null)
            totaServicios = 0;
       else
           totaServicios = recaudacion.getServicios_fin() - recaudacion.getServicios_inicio();
    }

    public void calculateTotaKilometros() {
        if(recaudacion == null || recaudacion.getKm_totales_inicio() == null || recaudacion.getKm_totales_fin() == null)
            totalKilometros = 0;
        else
            totalKilometros =  recaudacion.getKm_totales_fin() - recaudacion.getKm_totales_inicio();
    }

    public Integer getLicencia() {
        return licencia;
    }

    public void setLicencia(Integer licencia) {
        this.licencia = licencia;
    }

    public List<RecaudacionIngreso> getRecaudacionIngresos() {
        return recaudacionIngresos;
    }

    public void setRecaudacionIngresos(List<RecaudacionIngreso> recaudacionIngresos) {
        this.recaudacionIngresos = recaudacionIngresos;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public Licencia getLicenciaObj() {
        return licenciaObj;
    }

    public void setLicenciaObj(Licencia licenciaObj) {
        this.licenciaObj = licenciaObj;
    }

    public BigDecimal getTotalLiquido() {
        return totalLiquido;
    }

    public void setTotalLiquido(BigDecimal totalLiquido) {
        this.totalLiquido = totalLiquido;
    }

    public BigDecimal getTotalRecaudacion() {
        return totalRecaudacion;
    }

    public void setTotalRecaudacion(BigDecimal totalRecaudacion) {
        this.totalRecaudacion = totalRecaudacion;
    }

    public BigDecimal getTotalGastos() {
        return totalGastos;
    }

    public void setTotalGastos(BigDecimal totalGastos) {
        this.totalGastos = totalGastos;
    }

    public int getTotaServicios() {
        return totaServicios;
    }

    public void setTotaServicios(int totaServicios) {
        this.totaServicios = totaServicios;
    }

    public int getTotalKilometros() {
        return totalKilometros;
    }

    public void setTotalKilometros(int totalKilometros) {
        this.totalKilometros = totalKilometros;
    }
}
