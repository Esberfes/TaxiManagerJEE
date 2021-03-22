package com.taxi.utils;

import com.taxi.pojos.Conductor;
import com.taxi.pojos.RecaudacionIngreso;

import java.math.BigDecimal;

import static com.taxi.utils.BigDecimalUtils.percentage;

public abstract class RecaudacionUtils {

    public static BigDecimal calculateSalario(Conductor conductor, RecaudacionIngreso recaudacionIngreso){
        BigDecimal total = conductor.getComplemento_iva();

        if(recaudacionIngreso.getRecaudacion() != null)
            if (recaudacionIngreso.getRecaudacion().doubleValue() <= conductor.getT065().doubleValue()) {
                total = total.add(percentage(recaudacionIngreso.getRecaudacion(), new BigDecimal(35)));
            } else if (recaudacionIngreso.getLiquido().doubleValue() <= conductor.getT060().doubleValue()) {
                total = total.add(percentage(recaudacionIngreso.getRecaudacion(), new BigDecimal(40)));
            } else if (recaudacionIngreso.getRecaudacion().doubleValue() <= conductor.getT055().doubleValue()) {
                total = total.add(percentage(recaudacionIngreso.getRecaudacion(), new BigDecimal(45)));
            } else {
                total = total.add(percentage(recaudacionIngreso.getRecaudacion(), new BigDecimal(50)));
            }

        return total;
    }
}
