package com.taxi.converter;

import com.taxi.business.LicenciasBean;
import com.taxi.business.TiposGastosBean;
import com.taxi.pojos.Licencia;


import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
@Named("LicenciaConverter")
public class LicenciaConverter implements Converter<Licencia> {

    @Inject
    private LicenciasBean licenciasBean;

    @Override
    public Licencia getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                return licenciasBean.findSingleByCodigo(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Licencia invalida."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Licencia value) {
        return String.valueOf(value.getCodigo());
    }
}
