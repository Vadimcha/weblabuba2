package org.example.models;

import java.math.BigDecimal;

public record RequestData(BigDecimal x, BigDecimal y, BigDecimal r) {
    public Point toPoint() {
        return new Point(x, y, r);
    }
}
