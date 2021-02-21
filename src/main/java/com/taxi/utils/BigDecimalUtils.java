package com.taxi.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public abstract class BigDecimalUtils {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public static BigDecimal percentage(BigDecimal base, BigDecimal pct) {
        return base.multiply(pct).divide(ONE_HUNDRED, RoundingMode.CEILING);
    }

    public static BigDecimal ensureNotNull(BigDecimal in) {
        return in == null ? new BigDecimal("0.00") : in;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString("hola".getBytes(StandardCharsets.UTF_8)));
    }
}
