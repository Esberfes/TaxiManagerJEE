package com.taxi.converter;

import com.taxi.business.ConductoresBean;
import com.taxi.business.EstadosIngresosBean;
import com.taxi.pojos.Conductor;
import com.taxi.pojos.EstadosIngreso;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

@Named("EstadoConverter")
public class EstadoConverter implements Converter<EstadosIngreso> {

    @Inject
    private EstadosIngresosBean estadosIngresosBean;

    @Override
    public EstadosIngreso getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                return estadosIngresosBean.findSingleByName(value);
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Estado invalido."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, EstadosIngreso value) {
        return value.getNombre();
    }
}
