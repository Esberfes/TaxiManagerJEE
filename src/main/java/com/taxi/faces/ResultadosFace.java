package com.taxi.faces;

import com.taxi.business.ConductoresBean;
import com.taxi.business.GastosBean;
import com.taxi.business.RecaudacionBean;
import com.taxi.business.RecaudacionIngresosBean;
import com.google.gson.Gson;
import com.taxi.singletons.TaxiLogger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import com.taxi.pojos.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.taxi.utils.BigDecimalUtils.percentage;


@ViewScoped
@Named(ResultadosFace.BEAN_NAME)
public class ResultadosFace implements Serializable {

    public static final String BEAN_NAME = "ResultadosFace";

    @Inject
    private RecaudacionBean recaudacionBean;

    @Inject
    private GastosBean gastosBean;

    @Inject
    private ConductoresBean conductoresBean;

    @Inject
    private RecaudacionIngresosBean recaudacionIngresosBean;

    @Inject
    private transient TaxiLogger logger;

    private int mes;
    private int ano;

    private List<RecaudacionResult> recaudacion;
    private List<SalariosResult> salarios;
    private List<GastoResult> gastosOperativos;
    private List<GastoResult> gastosNoOperativos;

    @PostConstruct
    public void init() {
        LocalDateTime now = LocalDateTime.now();
        mes = now.getMonth().getValue();
        ano = now.getYear();

        refresh();
    }

    private List<RecaudacionResult> calculateRecaudacion() {
        Map<Integer, RecaudacionResult> results = new HashMap<>();

        try {
            Map<String, FilterMeta> filterMeta = new HashMap<>();
            filterMeta.put("ano", new FilterMeta("ano", ano));
            filterMeta.put("mes", new FilterMeta("mes", mes));
            Map<String, SortMeta> sortMeta = new HashMap<>();
            sortMeta.put("mes", new SortMeta("mes", "mes", SortOrder.DESCENDING, null));

            List<Recaudacion> data = recaudacionBean.getData(0, 10000, sortMeta, filterMeta);

            for (Recaudacion recaudacion : data) {
                if (results.containsKey(recaudacion.getLicencia().getCodigo())) {
                    RecaudacionResult recaudacionResult = results.get(recaudacion.getLicencia().getCodigo());
                    for (RecaudacionIngreso ingreso : recaudacion.getRecaudacionIngresos())
                        recaudacionResult.addRecaudacion(ingreso);
                } else {
                    RecaudacionResult recaudacionResult = new RecaudacionResult(recaudacion.getLicencia().getCodigo());
                    results.put(recaudacion.getLicencia().getCodigo(), recaudacionResult);
                    for (RecaudacionIngreso ingreso : recaudacion.getRecaudacionIngresos())
                        recaudacionResult.addRecaudacion(ingreso);
                }
            }
            logger.info("Recaudación calculada, numero de elmentos: " + results.size());
        } catch (Throwable e) {
            logger.error("Error calculando recaudación", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error recuperando recaudaciones", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return new ArrayList<>(results.values());
    }

    public void refresh() {
        recaudacion = calculateRecaudacion();
        salarios = calculateSalarios();
        gastosOperativos = calculateGastos(true);
        gastosNoOperativos = calculateGastos(false);
    }

    private List<SalariosResult> calculateSalarios() {
        Map<Long, SalariosResult> results = new HashMap<>();
        try {
            List<Conductor> conductores = conductoresBean.getData(0, 10000, new HashMap<>(), new HashMap<>());

            for (Conductor conductor : conductores) {
                SalariosResult salariosResult;
                if (results.containsKey(conductor.getId())) {
                    salariosResult = results.get(conductor.getId());
                } else {
                    salariosResult = new SalariosResult(conductor.getId(), conductor.getNombre());
                    results.put(conductor.getId(), salariosResult);
                }

                Map<String, FilterMeta> filterMeta = new HashMap<>();
                filterMeta.put("recaudaciones.ano", new FilterMeta("ano", ano));
                filterMeta.put("recaudaciones.mes", new FilterMeta("mes", mes));
                filterMeta.put("id_conductor", new FilterMeta("id_conductor", conductor.getId()));
                Map<String, SortMeta> sortMeta = new HashMap<>();

                List<RecaudacionIngreso> ingresos = recaudacionIngresosBean.getData(0, 10000, sortMeta, filterMeta);
                BigDecimal complemento = new BigDecimal("0.00");
                for (RecaudacionIngreso recaudacionIngreso : ingresos) {

                    if (recaudacionIngreso.getRecaudacion().doubleValue() <= conductor.getT065().doubleValue()) {
                        complemento = complemento.add(percentage(recaudacionIngreso.getRecaudacion(), new BigDecimal(35)));
                    } else if (recaudacionIngreso.getRecaudacion().doubleValue() <= conductor.getT060().doubleValue()) {
                        complemento = complemento.add(percentage(recaudacionIngreso.getRecaudacion(), new BigDecimal(40)));
                    } else if (recaudacionIngreso.getRecaudacion().doubleValue() <= conductor.getT055().doubleValue()) {
                        complemento = complemento.add(percentage(recaudacionIngreso.getRecaudacion(), new BigDecimal(45)));
                    } else {
                        complemento = complemento.add(percentage(recaudacionIngreso.getRecaudacion(), new BigDecimal(50)));
                    }

                    complemento = complemento.subtract(conductor.getComplemento_iva());

                    salariosResult.addSalario(complemento);
                }

            }
            logger.info("Salarios calculados, numero de elmentos: " + results.size());
        } catch (Throwable e) {
            logger.error("Error calculando salarios", e);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error recuperando recaudaciones", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return new ArrayList<>(results.values());
    }

    public BigDecimal getTotalFromSalarios() {
        BigDecimal result = new BigDecimal("0.00");
        for (SalariosResult salariosResult : salarios)
            result = result.add(salariosResult.getSalario());

        return result;
    }

    public BigDecimal getTotalFromGastos(List<GastoResult> gastoResults) {
        BigDecimal result = new BigDecimal("0.00");
        for (GastoResult gastoResult : gastoResults)
            result = result.add(gastoResult.getImporte());

        return result;
    }

    public BigDecimal sumRecaudacion() {
        BigDecimal result = new BigDecimal("0.00");
        for (RecaudacionResult recaudacionResult : recaudacion)
            result = result.add(recaudacionResult.getRecaudacion());

        return result;
    }

    public String getChartRecaudacion() {
        List<Object[]> data = new ArrayList<>();
        Object[] header = new Object[]{"Licencia", "Recaudacion"};
        data.add(header);

        for (RecaudacionResult recaudacionResult : recaudacion) {
            Object[] d = new Object[]{String.valueOf(recaudacionResult.getId()), recaudacionResult.getRecaudacion().doubleValue()};
            data.add(d);
        }

        return new Gson().toJson(data);
    }

    public BigDecimal beneficio() {
        return sumRecaudacion()
                .subtract(getTotalFromGastos(gastosNoOperativos))
                .subtract(getTotalFromGastos(gastosOperativos))
                .subtract(getTotalFromSalarios());
    }
    public String getChartSalario() {
        List<Object[]> data = new ArrayList<>();
        Object[] header = new Object[]{"Conductor", "Salario"};
        data.add(header);

        for (SalariosResult salariosResult : salarios) {
            Object[] d = new Object[]{String.valueOf(salariosResult.getNombre()), salariosResult.getSalario().doubleValue()};
            data.add(d);
        }

        return new Gson().toJson(data);
    }

    public String getChartGastosOperacionales() {
        return getGastosAsJson(gastosOperativos);
    }

    public String getChartGastosNoOperacionales() {
        return getGastosAsJson(gastosNoOperativos);
    }

    private String getGastosAsJson(List<GastoResult> gastosNoOperativos) {
        List<Object[]> data = new ArrayList<>();

        Object[] header = new Object[]{"Tipo", "Gasto"};
        data.add(header);

        for (GastoResult gastoResult : gastosNoOperativos) {
            Object[] d = new Object[]{gastoResult.getConcepto(), gastoResult.getImporte().doubleValue()};
            data.add(d);
        }

        return new Gson().toJson(data);
    }

    private List<GastoResult> calculateGastos(boolean operacional) {
        Map<String, GastoResult> result = new HashMap<>();
        try {
            Map<String, FilterMeta> filterMeta = new HashMap<>();
            filterMeta.put("creado", new FilterMeta("creado", ano + "-" + mes + "-"));
            filterMeta.put("tipos_gastos.es_operacional", new FilterMeta("tipos_gastos.es_operacional", operacional ? "1" : "0"));
            Map<String, SortMeta> sortMeta = new HashMap<>();
            sortMeta.put("creado", new SortMeta("creado", "creado", SortOrder.DESCENDING, null));

            List<Gasto> data = gastosBean.getData(0, 1000, sortMeta, filterMeta);

            for (Gasto gasto : data) {
                if (result.containsKey(gasto.getConcepto().getNombre())) {
                    GastoResult gastoResult = result.get(gasto.getConcepto().getNombre());
                    gastoResult.addGasto(gasto);
                    result.put(gasto.getConcepto().getNombre(), gastoResult);
                } else {
                    GastoResult gastoResult = new GastoResult(gasto.getConcepto().getNombre());
                    gastoResult.addGasto(gasto);
                    result.put(gasto.getConcepto().getNombre(), gastoResult);
                }
            }

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error recuperando recaudaciones", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        return new ArrayList<>(result.values());
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public List<RecaudacionResult> getRecaudacion() {
        return recaudacion;
    }

    public void setRecaudacion(List<RecaudacionResult> recaudacion) {
        this.recaudacion = recaudacion;
    }

    public List<SalariosResult> getSalarios() {
        return salarios;
    }

    public void setSalarios(List<SalariosResult> salarios) {
        this.salarios = salarios;
    }

    public List<GastoResult> getGastosOperativos() {
        return gastosOperativos;
    }

    public void setGastosOperativos(List<GastoResult> gastosOperativos) {
        this.gastosOperativos = gastosOperativos;
    }

    public List<GastoResult> getGastosNoOperativos() {
        return gastosNoOperativos;
    }

    public void setGastosNoOperativos(List<GastoResult> gastosNoOperativos) {
        this.gastosNoOperativos = gastosNoOperativos;
    }
}
