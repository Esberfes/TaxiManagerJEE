package faces;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named(EstadosIngresosFace.BEAN_NAME)
public class EstadosIngresosFace implements Serializable {

    public static final String BEAN_NAME = "EstadosIngresosFace";

}
