package com.taxi.faces;

import com.taxi.enums.NavigationEnum;
import com.taxi.singletons.TaxiLogger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@SessionScoped
@Named(NavigationFace.BEAN_NAME)
@Interceptors(TaxiLogger.class)
public class NavigationFace implements Serializable {

    public static final String BEAN_NAME = "NavigationFace";

    private NavigationEnum navigation;

    @PostConstruct
    public void init() {
        navigation = NavigationEnum.licencias;
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
