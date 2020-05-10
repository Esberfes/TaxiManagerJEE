package faces;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named(ConceptosGastosFace.BEAN_NAME)
public class ConceptosGastosFace implements Serializable {

    public static final String BEAN_NAME = "ConceptosGastosFace";
}
