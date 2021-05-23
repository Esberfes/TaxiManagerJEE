package com.taxi.converter;

import com.taxi.business.ConductoresBean;
import com.taxi.business.TiposGastosBean;
import com.taxi.pojos.Conductor;
import com.taxi.pojos.TiposGasto;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

@Named("TipoGastoConverter")
public class TipoGastoConverter implements Converter<TiposGasto> {

    @Inject
    private TiposGastosBean tiposGastosBean;

    @Override
    public TiposGasto getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                return tiposGastosBean.findSingleByName(value);
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Tipo de gasto invalido."));
            }
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, TiposGasto value) {
        return value.getNombre();
    }
}
