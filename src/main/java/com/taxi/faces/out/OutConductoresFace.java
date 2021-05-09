package com.taxi.faces.out;

import com.taxi.business.ConductoresBean;
import com.taxi.business.GastosBean;
import com.taxi.business.LicenciasBean;
import com.taxi.business.RecaudacionBean;
import com.taxi.faces.SessionData;
import com.taxi.pojos.Conductor;
import com.taxi.pojos.Recaudacion;
import com.taxi.pojos.RecaudacionIngreso;
import com.taxi.pojos.out.ConductorResultado;
import com.taxi.singletons.TaxiLogger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import static com.taxi.utils.RecaudacionUtils.calculateSalario;

@ViewScoped
@Named(OutConductoresFace.BEAN_NAME)
public class OutConductoresFace implements Serializable {

    public static final String BEAN_NAME = "OutConductoresFace";

    @Inject
    private transient TaxiLogger logger;

    @Inject
    private ConductoresBean conductoresBean;

    @Inject
    private RecaudacionBean recaudacionBean;

    @Inject
    private LicenciasBean licenciasBean;

    @Inject
    private GastosBean gastosBean;

    @Inject
    private SessionData sessionData;

    private List<ConductorResultado> conductorResultados;
    private Recaudacion recaudacion;
    private Conductor conductor;

    @PostConstruct
    public void init() {
        conductorResultados = new LinkedList<>();
    }

    public void refresh() {
        try {

            recaudacion = null;
            conductorResultados = new LinkedList<>();

            if (conductor != null) {
                Map<String, FilterMeta> filterMeta = new HashMap<>();
                filterMeta.put("ano", new FilterMeta("ano", sessionData.getAno()));
                filterMeta.put("mes", new FilterMeta("mes", sessionData.getMes()));
                filterMeta.put("id_conductor", new FilterMeta("id_conductor", conductor.getId()));

                Map<String, SortMeta> sortMeta = new HashMap<>();
                sortMeta.put("mes", new SortMeta("mes", "mes", SortOrder.ASCENDING, null));

                List<Recaudacion> recaudaciones = recaudacionBean.getData(0, 10000, sortMeta, filterMeta);
                recaudacion = recaudaciones.get(0);
                List<RecaudacionIngreso> recaudacionIngresos = recaudacion.getRecaudacionIngresos();

                for (RecaudacionIngreso recaudacionIngreso : recaudacionIngresos) {
                    ConductorResultado conductorResultado = conductorResultados.stream()
                            .filter(c -> recaudacionIngreso.getConductor().equals(c.getConductor()))
                            .findFirst()
                            .orElse(null);

                    if (conductorResultado == null) {
                        conductorResultado = new ConductorResultado(recaudacionIngreso.getConductor(),
                                recaudacionIngreso.getRecaudacion(), recaudacionIngreso.getLiquido(),
                                recaudacionIngreso.getTarjeta(), recaudacionIngreso.getApp(),
                                recaudacionIngreso.getEfectivo(),calculateSalario(recaudacionIngreso.getConductor(), recaudacionIngreso));
                        conductorResultados.add(conductorResultado);
                    } else {
                        conductorResultado.setRecaudacion(conductorResultado.getRecaudacion().add(recaudacionIngreso.getRecaudacion() != null ? recaudacionIngreso.getRecaudacion() : new BigDecimal("0.00")));
                        conductorResultado.setLiquido(conductorResultado.getLiquido().add(recaudacionIngreso.getLiquido() != null ? recaudacionIngreso.getLiquido() : new BigDecimal("0.00")));
                        conductorResultado.setTarjeta(conductorResultado.getTarjeta().add(recaudacionIngreso.getTarjeta() != null ?recaudacionIngreso.getTarjeta() : new BigDecimal("0.00")));
                        conductorResultado.setApp(conductorResultado.getApp().add(recaudacionIngreso.getApp() != null ? recaudacionIngreso.getApp() : new BigDecimal("0.00")));
                        conductorResultado.setEfectivo(conductorResultado.getEfectivo().add(recaudacionIngreso.getEfectivo() != null ? recaudacionIngreso.getEfectivo() : new BigDecimal("0.00")));
                        conductorResultado.setSalario(conductorResultado.getSalario().add(calculateSalario(conductorResultado.getConductor(), recaudacionIngreso)));
                    }

                }
            }
        } catch (Throwable e) {
            logger.error("Error refrescando resultados conductores", e);
        }
    }

    public List<ConductorResultado> getConductorResultados() {
        return conductorResultados;
    }

    public void setConductorResultados(List<ConductorResultado> conductorResultados) {
        this.conductorResultados = conductorResultados;
    }

    public Recaudacion getRecaudacion() {
        return recaudacion;
    }

    public void setRecaudacion(Recaudacion recaudacion) {
        this.recaudacion = recaudacion;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }
}
