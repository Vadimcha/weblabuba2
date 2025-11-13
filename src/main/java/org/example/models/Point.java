package org.example.models;

import java.math.BigDecimal;

public class Point {
    public final BigDecimal x;
    public final BigDecimal y;
    public final BigDecimal r;

    public Point(BigDecimal x, BigDecimal y, BigDecimal r) {
        this.x = x;
        this.y = y;
        this.r = r;
    };
}
