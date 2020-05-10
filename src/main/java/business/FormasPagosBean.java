package business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless(name = FormasPagosBean.BEAN_NAME)
public class FormasPagosBean {

    public static final String BEAN_NAME = "FormasPagosBean";
}
