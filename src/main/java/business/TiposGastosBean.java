package business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless(name = TiposGastosBean.BEAN_NAME)
public class TiposGastosBean {

    public static final String BEAN_NAME = "TiposGastosBean";
}
