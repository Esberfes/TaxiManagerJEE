package com.taxi.faces.out;

import com.taxi.business.ConductoresBean;
import com.taxi.business.GastosBean;
import com.taxi.business.LicenciasBean;
import com.taxi.business.RecaudacionBean;
import com.taxi.faces.SessionData;
import com.taxi.pojos.Conductor;
import com.taxi.pojos.Recaudacion;
import com.taxi.pojos.RecaudacionIngreso;
import com.taxi.pojos.TaxiFilterMeta;
import com.taxi.pojos.out.ConductorResultado;
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
import java.util.stream.Collectors;

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
    private List<Recaudacion> recaudacion;
    private Conductor conductor;

    @PostConstruct
    public void init() {
        conductorResultados = new LinkedList<>();
    }

    public void refresh() {
        try {

            recaudacion = null;
            conductorResultados = new LinkedList<>();


            Map<String, FilterMeta> filterMeta = new HashMap<>();
            filterMeta.put("ano", new TaxiFilterMeta("ano", sessionData.getAno(), MatchMode.EXACT));
            filterMeta.put("mes", new TaxiFilterMeta("mes", sessionData.getMes(), MatchMode.EXACT));
            if (conductor != null)
                filterMeta.put("id_conductor", new TaxiFilterMeta("id_conductor", conductor.getId(), MatchMode.EXACT));

            Map<String, SortMeta> sortMeta = new HashMap<>();
            sortMeta.put("mes", new SortMeta("mes", "mes", SortOrder.ASCENDING, null));

            recaudacion = recaudacionBean.getData(0, 10000, sortMeta, filterMeta);
            List<RecaudacionIngreso> recaudacionIngresos = recaudacion.stream().flatMap(r -> r.getRecaudacionIngresos().stream()).collect(Collectors.toList());

           List<List<RecaudacionIngreso>> recaudacionIngresosByConductor = new LinkedList<>();

            if (conductor != null)
                recaudacionIngresosByConductor
                        .add(new LinkedList<>(recaudacionIngresos.stream()
                                .filter(r -> r.getConductor().getId().equals(conductor.getId())).collect(Collectors.toList())));
            else {
                recaudacionIngresosByConductor
                        .addAll(new ArrayList<>(recaudacionIngresos.stream()
                                .collect(Collectors.groupingBy(w -> w.getConductor().getId())).values()));
            }

            for(List<RecaudacionIngreso> recaudacionIngresosConductores : recaudacionIngresosByConductor) {
                for (RecaudacionIngreso recaudacionIngreso : recaudacionIngresosConductores) {
                    ConductorResultado conductorResultado = conductorResultados.stream()
                            .filter(c -> recaudacionIngreso.getConductor().equals(c.getConductor()))
                            .findFirst()
                            .orElse(null);

                    if (conductorResultado == null) {
                        conductorResultado = new ConductorResultado(recaudacionIngreso.getConductor(),
                                recaudacionIngreso.getRecaudacion(), recaudacionIngreso.getLiquido(),
                                recaudacionIngreso.getTarjeta(), recaudacionIngreso.getApp(),
                                recaudacionIngreso.getEfectivo(), calculateSalario(recaudacionIngreso.getConductor(), recaudacionIngreso));
                        conductorResultados.add(conductorResultado);
                    } else {
                        conductorResultado.setRecaudacion(conductorResultado.getRecaudacion().add(recaudacionIngreso.getRecaudacion() != null ? recaudacionIngreso.getRecaudacion() : new BigDecimal("0.00")));
                        conductorResultado.setLiquido(conductorResultado.getLiquido().add(recaudacionIngreso.getLiquido() != null ? recaudacionIngreso.getLiquido() : new BigDecimal("0.00")));
                        conductorResultado.setTarjeta(conductorResultado.getTarjeta().add(recaudacionIngreso.getTarjeta() != null ? recaudacionIngreso.getTarjeta() : new BigDecimal("0.00")));
                        conductorResultado.setApp(conductorResultado.getApp().add(recaudacionIngreso.getApp() != null ? recaudacionIngreso.getApp() : new BigDecimal("0.00")));
                        conductorResultado.setEfectivo(conductorResultado.getEfectivo().add(recaudacionIngreso.getEfectivo() != null ? recaudacionIngreso.getEfectivo() : new BigDecimal("0.00")));
                        conductorResultado.setSalario(conductorResultado.getSalario().add(calculateSalario(conductorResultado.getConductor(), recaudacionIngreso)));
                    }

                }
            }

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, new Object(){}.getClass().getEnclosingMethod().getName(), e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error(new Object(){}.getClass().getEnclosingMethod().getName(), e);
        }
    }

    public List<ConductorResultado> getConductorResultados() {
        return conductorResultados;
    }

    public void setConductorResultados(List<ConductorResultado> conductorResultados) {
        this.conductorResultados = conductorResultados;
    }

    public List<Recaudacion> getRecaudacion() {
        return recaudacion;
    }

    public void setRecaudacion(List<Recaudacion> recaudacion) {
        this.recaudacion = recaudacion;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }
}
