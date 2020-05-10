package faces;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named(EmpresasFace.BEAN_NAME)
public class EmpresasFace implements Serializable {

    public static final String BEAN_NAME = "EmpresasFace";
}
