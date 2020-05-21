package faces;

import business.EmpresasBean;
import business.TiposGastosBean;
import pojos.Empresa;
import pojos.TiposGasto;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
@Named(AutoCompleteFace.BEAN_NAME)
public class AutoCompleteFace {

    public static final String BEAN_NAME = "AutoCompleteFace";

    @Inject
    private EmpresasBean empresasBean;

    @Inject
    private TiposGastosBean tiposGastosBean;

    public List<String> completeEmpesa(String value) {
        try {
            return empresasBean.findByName(value).stream().map(Empresa::getNombre).collect(Collectors.toList());

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error auto completando empresas", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return new ArrayList<>();
        }
    }

    public List<String> completeTipoGasto(String value) {
        try {
            return tiposGastosBean.findByName(value).stream().map(TiposGasto::getNombre).collect(Collectors.toList());

        } catch (Throwable e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error auto completando tipos de gastos", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return new ArrayList<>();
        }
    }
}
