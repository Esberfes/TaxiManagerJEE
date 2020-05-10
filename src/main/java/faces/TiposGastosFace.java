package faces;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named(TiposGastosFace.BEAN_NAME)
public class TiposGastosFace implements Serializable {

    public static final String BEAN_NAME = "TiposGastosFace";
}
