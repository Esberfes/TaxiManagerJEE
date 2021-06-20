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
import org.primefaces.model.DefaultTreeNode;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

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

    private ResumenMes resumen;
    private List<Gasto> gastos;
    private List<Gasto> gastosGestion;
    private List<Gasto> gastosOperativos;
    private DefaultTreeNode root;

    @PostConstruct
    public void init() {
        refresh();
    }

    public DefaultTreeNode getRoot() {
        return root;
    }

    public void refresh() {
        try {
            ResumenMes resumenMes = new ResumenMes();

            gastos = gastosBean.getData(0, Integer.MAX_VALUE, new HashMap<>(), new HashMap<>());
            List<Recaudacion> recaudaciones = recaudacionBean.getData(0, Integer.MAX_VALUE, new HashMap<>(), new HashMap<>());

            for (Recaudacion recaudacion : recaudaciones) {
                if (recaudacion.getRecaudacionIngresos() != null) {
                    for (RecaudacionIngreso recaudacionIngreso : recaudacion.getRecaudacionIngresos()) {
                        resumenMes.setRecaudacion(resumenMes.getRecaudacion().add(recaudacionIngreso.getRecaudacion()));
                        resumenMes.setSalarios(resumenMes.getSalarios().add(calculateSalario(recaudacionIngreso.getConductor(), recaudacionIngreso)));
                    }
                }
            }

            BigDecimal sumGasOp = new BigDecimal("0.00");
            BigDecimal sumGasGes= new BigDecimal("0.00");

            for (Gasto gasto : gastos) {
                if (gasto.getTipoGasto().getEs_operacional()) {
                    BigDecimal gasOp = resumenMes.getGastosOp()
                            .computeIfAbsent(gasto.getTipoGasto().getNombre(), (e) -> new BigDecimal("0.00"));
                    resumenMes.getGastosOp().put(gasto.getTipoGasto().getNombre(), gasOp.add(gasto.getImporte()));
                    sumGasOp = sumGasOp.add(gasto.getImporte());
                } else {
                    BigDecimal gasGes = resumenMes.getGastosGes()
                            .computeIfAbsent(gasto.getTipoGasto().getNombre(), (e) -> new BigDecimal("0.00"));
                    resumenMes.getGastosGes().put(gasto.getTipoGasto().getNombre(), gasGes.add(gasto.getImporte()));
                    sumGasGes = sumGasOp.add(gasto.getImporte());
                }
            }

            BigDecimal gasOp = resumenMes.getGastosOp()
                    .computeIfAbsent("SALARIOS", (e) -> new BigDecimal("0.00"));
            gasOp.add(resumenMes.getSalarios());

            resumenMes.setBeneficioOp(resumenMes.getRecaudacion().subtract(sumGasOp));
            resumenMes.setBeneficio(resumenMes.getRecaudacion().subtract(sumGasOp).subtract(sumGasGes));

            resumenMes.setGastosGesTotal(resumenMes.getGastosGes().values().stream().reduce(BigDecimal.ZERO, BigDecimal::add));
            resumenMes.setGastosOpTotal(resumenMes.getGastosOp().values().stream().reduce(BigDecimal.ZERO, BigDecimal::add));

            resumen = resumenMes;

            root = new DefaultTreeNode();
            root.setChildren(gastos.stream().map(e -> {
                DefaultTreeNode wrapper = new DefaultTreeNode(e.getId() + " - " + e.getTipoGasto().getNombre() + " - " + e.getDefinicion());
                wrapper.setChildren(Collections.singletonList(e.buildTree(wrapper)));
                return wrapper;
            }).collect(Collectors.toList()));

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, new Object() {
            }.getClass().getEnclosingMethod().getName(), e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            logger.error(new Object() {
            }.getClass().getEnclosingMethod().getName(), e);
        }

    }

    public ResumenMes getResumen() {
        return resumen;
    }

    public void setResumen(ResumenMes resumen) {
        this.resumen = resumen;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }
}
