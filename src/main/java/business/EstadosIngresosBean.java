package business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless(name = EstadosIngresosBean.BEAN_NAME)
public class EstadosIngresosBean {

    public static final String BEAN_NAME = "EstadosIngresosBean";
}
