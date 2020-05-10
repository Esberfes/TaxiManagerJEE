package faces;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named(LicenciasFace.BEAN_NAME)
public class LicenciasFace implements Serializable {

    public static final String BEAN_NAME = "LicenciasFace";
}
