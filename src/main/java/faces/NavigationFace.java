package faces;

import enums.NavigationEnum;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@SessionScoped
@Named(NavigationFace.BEAN_NAME)
public class NavigationFace implements Serializable {

    public static final String BEAN_NAME = "NavigationFace";

    private NavigationEnum navigation;

    @PostConstruct
    public void init() {
        navigation = NavigationEnum.employees;
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
}
