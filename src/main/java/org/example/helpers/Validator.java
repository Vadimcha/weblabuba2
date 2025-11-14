package org.example.helpers;


import org.example.models.RequestData;

import java.math.BigDecimal;

public class Validator {

    private static final BigDecimal X_MIN = new BigDecimal("-5.0");
    private static final BigDecimal X_MAX = new BigDecimal("3.0");
    private static final BigDecimal Y_MIN = new BigDecimal("-3.0");
    private static final BigDecimal Y_MAX = new BigDecimal("5.0");
    private static final BigDecimal R_MIN = new BigDecimal("1.0");
    private static final BigDecimal R_MAX = new BigDecimal("5.0");

    public static void validate(RequestData data) {
        BigDecimal x = data.x();
        BigDecimal y = data.y();
        BigDecimal r = data.r();

        if (x == null || x.compareTo(X_MIN) < 0 || x.compareTo(X_MAX) > 0) {
            throw new IllegalArgumentException(
                    "x должен быть числом в диапазоне [" + X_MIN + ", " + X_MAX + "]"
            );
        }

        if (y == null || y.compareTo(Y_MIN) < 0 || y.compareTo(Y_MAX) > 0) {
            throw new IllegalArgumentException(
                    "y должен быть числом в диапазоне [" + Y_MIN + ", " + Y_MAX + "]"
            );
        }

        if (r == null || r.compareTo(R_MIN) < 0 || r.compareTo(R_MAX) > 0) {
            throw new IllegalArgumentException(
                    "r должен быть числом в диапазоне [" + R_MIN + ", " + R_MAX + "]"
            );
        }
    }
}
