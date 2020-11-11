package faces;

import enums.NavigationEnum;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named(NavigationFace.BEAN_NAME)
public class NavigationFace implements Serializable {

    public static final String BEAN_NAME = "NavigationFace";

    private NavigationEnum navigation;
    private boolean switchLog;

    @PostConstruct
    public void init() {
        navigation = NavigationEnum.licencias;
        PrimeFaces.current().executeScript("PF('log').hide();");
    }

    public void navigate(NavigationEnum navigation) {
        this.navigation = navigation;
    }

    public NavigationEnum getNavigation() {
        return navigation;
    }

    public void setNavigation(NavigationEnum navigation) {
        this.navigation = navigation;
    }

    public boolean isSwitchLog() {
        return switchLog;
    }

    public void setSwitchLog(boolean switchLog) {
        if (switchLog)
            PrimeFaces.current().executeScript("PF('log').show();");
        else
            PrimeFaces.current().executeScript("PF('log').hide();");

        this.switchLog = switchLog;
    }

}
