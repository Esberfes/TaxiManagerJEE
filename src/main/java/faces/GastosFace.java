package faces;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named(GastosFace.BEAN_NAME)
public class GastosFace  implements Serializable {

    public static final String BEAN_NAME = "GastosFace";

}
