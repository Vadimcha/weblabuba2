package org.example.helpers;

import org.example.models.Point;

import java.math.BigDecimal;
import java.math.MathContext;

public class Calculator {
    static long runTime;

    public static Boolean calculate(Point point) {
        long startTime = System.nanoTime();
        boolean res = false;

        // определение координатной четверти
        if (point.x.compareTo(BigDecimal.ZERO) >= 0 && point.y.compareTo(BigDecimal.ZERO) >= 0)
            res = isInRectangle(point);

        if (point.x.compareTo(BigDecimal.ZERO) <= 0 && point.y.compareTo(BigDecimal.ZERO) <= 0)
            res = isInCircle(point);

        if (point.x.compareTo(BigDecimal.ZERO) >= 0 && point.y.compareTo(BigDecimal.ZERO) <= 0)
            res = isInTriangle(point);

        long endTime = System.nanoTime();
        runTime = endTime - startTime;
        return res;
    }

    public static String getRunTime() {
        return Long.toString(runTime);
    }

    private static Boolean isInTriangle(Point point) {
        // y >= x - r
        BigDecimal right = point.x.subtract(point.r);
        return point.y.compareTo(right) >= 0;
    }

    private static Boolean isInCircle(Point point) {
        // x² + y² <= r²
        BigDecimal x2 = point.x.multiply(point.x);
        BigDecimal y2 = point.y.multiply(point.y);
        BigDecimal r2 = point.r.multiply(point.r);
        BigDecimal sum = x2.add(y2);
        return sum.compareTo(r2) <= 0;
    }

    private static Boolean isInRectangle(Point point) {
        // x <= r/2 && y <= r
        BigDecimal halfR = point.r.divide(new BigDecimal("2"), MathContext.DECIMAL128);
        return point.x.compareTo(halfR) <= 0 && point.y.compareTo(point.r) <= 0;
    }
}
