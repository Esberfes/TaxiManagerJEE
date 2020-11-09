package utils;

import java.math.BigDecimal;

public abstract class BigDecimalUtils {
    public static BigDecimal ensureNotNull(BigDecimal in) {
        return in == null ? new BigDecimal("0.000") : in;
    }
}
