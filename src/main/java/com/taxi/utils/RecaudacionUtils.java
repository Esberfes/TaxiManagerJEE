package com.taxi.utils;

import com.taxi.pojos.Conductor;
import com.taxi.pojos.Recaudacion;
import com.taxi.pojos.RecaudacionIngreso;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static com.taxi.utils.BigDecimalUtils.percentage;

public abstract class RecaudacionUtils {

    public static final Comparator<RecaudacionIngreso> RECAUDACION_INGRESO_COMPARATOR = (o1, o2) -> {
        if (o1.getDia() > o2.getDia())
            return 1;
        if (o1.getDia() < o2.getDia())
            return -1;
        if (o1.getDia().equals(o2.getDia()) && o1.getTurno().equalsIgnoreCase("tarde"))
            return 1;
        if (o1.getDia().equals(o2.getDia()) && o1.getTurno().equalsIgnoreCase(o2.getTurno()))
            return 0;

        return -1;
    };

    public static BigDecimal calculateSalario(Conductor conductor, RecaudacionIngreso recaudacionIngreso) {
        BigDecimal total = conductor.getComplemento_iva();

        if (recaudacionIngreso.getRecaudacion() != null)
            if (recaudacionIngreso.getRecaudacion().doubleValue() <= conductor.getT000().doubleValue()) {
                total = total.add(percentage(recaudacionIngreso.getRecaudacion(), new BigDecimal(0)));
            } else if (recaudacionIngreso.getRecaudacion().doubleValue() <= conductor.getT065().doubleValue()) {
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

    public static BigDecimal calculateEfectivo(RecaudacionIngreso recaudacionIngreso, Recaudacion recaudacion) {
        getRecaudacion(recaudacionIngreso, recaudacion);

        BigDecimal liquido = recaudacionIngreso.getLiquido();

        if (recaudacionIngreso.getTarjeta() != null)
            liquido = liquido.subtract(recaudacionIngreso.getTarjeta());

        if (recaudacionIngreso.getApp() != null)
            liquido = liquido.subtract(recaudacionIngreso.getApp());

        return recaudacionIngreso.getPagos() != null ? liquido.subtract(recaudacionIngreso.getPagos()) : liquido;
    }

    public static void setRecaudacion(List<RecaudacionIngreso> entities) {
        entities.sort(RECAUDACION_INGRESO_COMPARATOR);

        for (int i = 0; i < entities.size(); i++) {
            try {
                RecaudacionIngreso ingresosEntity = entities.get(i);

                if (i == 0) {
                    ingresosEntity.setRecaudacion(ingresosEntity.getNumeracion().subtract(ingresosEntity.getRecaudacionObj().getNumeracion_inicio()));
                } else {
                    RecaudacionIngreso ingresosEntityLast = entities.get(i - 1);
                    ingresosEntity.setRecaudacion(ingresosEntity.getNumeracion().subtract(ingresosEntityLast.getNumeracion()));
                }

                if (ingresosEntity.getAnulados() != null)
                    ingresosEntity.setRecaudacion(ingresosEntity.getRecaudacion().subtract(ingresosEntity.getAnulados()));

                ingresosEntity.setLiquido(calculateLiquido(ingresosEntity));

            } catch (Throwable e) {

            }
        }
    }

    public static BigDecimal calculateLiquido(RecaudacionIngreso ingresos) {
        try {
            BigDecimal total = ingresos.getRecaudacion();
            BigDecimal liquido = total.add(ingresos.getConductor().getComplemento_iva());

            if (ingresos.getT() == null) {
                if (ingresos.getRecaudacion().doubleValue() <= ingresos.getConductor().getT065().doubleValue() && ingresos.getConductor().getT065().doubleValue() != 0D) {
                    BigDecimal selected = new BigDecimal(35);
                    ingresos.setT(selected);
                    liquido = liquido.subtract(percentage(ingresos.getRecaudacion(), selected));
                } else if (ingresos.getRecaudacion().doubleValue() <= ingresos.getConductor().getT060().doubleValue() && ingresos.getConductor().getT060().doubleValue() != 0D) {
                    BigDecimal selected = new BigDecimal(40);
                    liquido = liquido.subtract(percentage(ingresos.getRecaudacion(), selected));
                    ingresos.setT(selected);
                } else if (ingresos.getRecaudacion().doubleValue() <= ingresos.getConductor().getT055().doubleValue() && ingresos.getConductor().getT055().doubleValue() != 0D) {
                    BigDecimal selected = new BigDecimal(45);
                    liquido = liquido.subtract(percentage(ingresos.getRecaudacion(), selected));
                    ingresos.setT(selected);
                } else if (ingresos.getConductor().getT050().doubleValue() != 0D) {
                    BigDecimal selected = new BigDecimal(50);
                    liquido = liquido.subtract(percentage(ingresos.getRecaudacion(), selected));
                    ingresos.setT(selected);
                }
            } else {
                liquido = liquido.subtract(percentage(ingresos.getRecaudacion(), ingresos.getT()));
            }

            return liquido;
        } catch (Throwable e) {
            throw e;
        }
    }

    public static BigDecimal getRecaudacion(RecaudacionIngreso recaudacionIngreso, Recaudacion recaudacion) {
        List<RecaudacionIngreso> recaudacionIngresos = recaudacion.getRecaudacionIngresos();
        recaudacionIngresos.add(recaudacionIngreso);
        recaudacionIngresos.forEach(r -> r.setRecaudacionObj(recaudacion));

        recaudacionIngresos.sort(RECAUDACION_INGRESO_COMPARATOR);

        for (int i = 0; i < recaudacionIngresos.size(); i++) {
            try {
                RecaudacionIngreso ingresosEntity = recaudacionIngresos.get(i);

                if (i == 0) {
                    ingresosEntity.setRecaudacion(ingresosEntity.getNumeracion().subtract(ingresosEntity.getRecaudacionObj().getNumeracion_inicio()));
                } else {
                    RecaudacionIngreso ingresosEntityLast = recaudacionIngresos.get(i - 1);
                    ingresosEntity.setRecaudacion(ingresosEntity.getNumeracion().subtract(ingresosEntityLast.getNumeracion()));
                }

                if (ingresosEntity.getAnulados() != null)
                    ingresosEntity.setRecaudacion(ingresosEntity.getRecaudacion().subtract(ingresosEntity.getAnulados()));

                ingresosEntity.setLiquido(calculateLiquido(ingresosEntity));

            } catch (Throwable e) {
                return new BigDecimal("0.00");
            }
        }

        return recaudacionIngreso.getRecaudacion();
    }
}
