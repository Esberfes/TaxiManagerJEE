package faces;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named(FormasPagosFace.BEAN_NAME)
public class FormasPagosFace implements Serializable {

    public static final String BEAN_NAME = "FormasPagosFace";
}
