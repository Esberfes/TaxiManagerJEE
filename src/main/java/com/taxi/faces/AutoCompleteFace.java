package com.taxi.faces;

import com.taxi.business.*;
import org.primefaces.PrimeFaces;
import com.taxi.pojos.*;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.taxi.utils.NumberUtils.isInteger;

@RequestScoped
@Named(AutoCompleteFace.BEAN_NAME)
public class AutoCompleteFace implements Serializable {

    public static final String BEAN_NAME = "AutoCompleteFace";

    @Inject
    private EmpresasBean empresasBean;

    @Inject
    private TiposGastosBean tiposGastosBean;

    @Inject
    private LicenciasBean licenciasBean;

    @Inject
    private ConductoresBean conductoresBean;

    @Inject
    private EstadosIngresosBean estadosIngresosBean;

    @Inject
    private FormasPagosBean formasPagosBean;

    @Inject
    private ConceptosGastosBean conceptosGastosBean;

    public List<String> completeEmpesa(String value) {
        try {
            return empresasBean.findByName(value).stream().map(Empresa::getNombre).collect(Collectors.toList());

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error auto completando empresas", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("form:messages");
            return new ArrayList<>();
        }
    }

    public List<String> completeConductor(String value) {
        try {
            return conductoresBean.findEmployeesByFullName(value).stream().map(Conductor::getNombre).collect(Collectors.toList());

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error auto completando conductores", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("form:messages");

            return new ArrayList<>();
        }
    }


    public List<String> completeTipoGasto(String value) {
        try {
            return tiposGastosBean.findByName(value).stream().map(TiposGasto::getNombre).collect(Collectors.toList());

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error auto completando tipos de gastos", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("form:messages");
            return new ArrayList<>();
        }
    }

    public List<String> completeEstadoIngreso(String value) {
        try {
            return estadosIngresosBean.findByName(value).stream().map(EstadosIngreso::getNombre).collect(Collectors.toList());

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error auto completando estados de ingreso de gastos", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("form:messages");
            return new ArrayList<>();
        }
    }


    public List<Integer> completeLicencia(String value) {
        try {
            if (!isInteger(value))
                throw new Exception("Deber ser un numero entero");

            Integer code = Integer.parseInt(value);

            return licenciasBean.findByCodigo(code).stream().map(Licencia::getCodigo).collect(Collectors.toList());

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error auto completando tipos de gastos", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("form:messages");
            return new ArrayList<>();
        }
    }

    public List<String> completeFormaPago(String value) {
        try {
            return formasPagosBean.findByName(value).stream().map(FormasPago::getNombre).collect(Collectors.toList());

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error auto completando formas de pago", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("form:messages");
            return new ArrayList<>();
        }
    }

    public List<String> completeConceptos(String value) {
        try {
            return conceptosGastosBean.findByName(value).stream().map(ConceptosGastos::getNombre).collect(Collectors.toList());

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error auto completando conceptos de gasto", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("form:messages");
            return new ArrayList<>();
        }
    }
}
