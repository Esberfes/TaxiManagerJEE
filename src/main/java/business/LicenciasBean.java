package business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless(name = LicenciasBean.BEAN_NAME)
public class LicenciasBean {

    public static final String BEAN_NAME = "LicenciasBean";
}
