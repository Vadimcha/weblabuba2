package org.example.models;

import java.math.BigDecimal;

public class Result {
    public String run_time;
    public boolean hit_status;
    public BigDecimal x;
    public BigDecimal y;
    public BigDecimal r;
    public String res;
    public String status;
    public String error;

    public Result(String run_time, boolean hit_status, BigDecimal x, BigDecimal y, BigDecimal r, String res, String status, String error) {
        this.run_time = run_time;
        this.hit_status = hit_status;
        this.x = x;
        this.y = y;
        this.r = r;
        this.res = res;
        this.status = status;
        this.error = error;
    }
}
