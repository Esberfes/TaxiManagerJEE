package com.taxi.faces;

import com.taxi.enums.NavigationEnum;
import com.taxi.faces.out.OutConductoresFace;
import com.taxi.faces.out.OutLicenciasFace;
import com.taxi.faces.out.OutResumenMensual;
import com.taxi.singletons.TaxiLogger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

@SessionScoped
@Named(SessionData.BEAN_NAME)
public class SessionData implements Serializable {

    public static final String BEAN_NAME = "SessionData";

    private NavigationEnum navigation;
    private int mes;
    private int ano;

    @Inject
    private OutConductoresFace outConductoresFace;
    @Inject
    private OutLicenciasFace outLicenciasFace;
    @Inject
    private OutResumenMensual outResumenMensual;
    @PostConstruct
    public void init() {
        navigation = NavigationEnum.licencias;
        Calendar calendar = new GregorianCalendar();
        ano = calendar.get(Calendar.YEAR) % 100;
        mes = calendar.get(Calendar.MONTH) + 1;
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

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void refresh() {
        outConductoresFace.refresh();
        outLicenciasFace.refresh();
        outResumenMensual.refresh();

    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
