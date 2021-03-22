package com.taxi.converter;

import com.taxi.business.ConductoresBean;
import com.taxi.pojos.Conductor;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

@Named("ConductorConverter")
public class ConductorConverter implements Converter<Conductor> {

    @Inject
    private ConductoresBean conductoresBean;

    @Override
    public Conductor getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                return conductoresBean.findEmployeesByFullName(value).stream().findFirst().orElse(null);
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid country."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Conductor value) {
        return value.getNombre();
    }
}
