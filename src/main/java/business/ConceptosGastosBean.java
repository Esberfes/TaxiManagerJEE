package business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@LocalBean
@Stateless(name = ConceptosGastosBean.BEAN_NAME)
public class ConceptosGastosBean {

    public static final String BEAN_NAME = "ConceptosGastosBean";
}
