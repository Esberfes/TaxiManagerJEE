package faces;

import business.EmpresasBean;
import business.LicenciasBean;
import business.TiposGastosBean;
import org.primefaces.PrimeFaces;
import pojos.Empresa;
import pojos.Licencia;
import pojos.TiposGasto;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static utils.NumberUtils.isInteger;

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
}
