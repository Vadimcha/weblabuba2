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

    // ===== Геттеры для JSP EL =====
    public String getRun_time() {
        return run_time;
    }

    public boolean isHit_status() {
        return hit_status;
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public BigDecimal getR() {
        return r;
    }

    public String getRes() {
        return res;
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return String.format("X=%s, Y=%s, R=%s, Результат=%s, Время выполнения=%s нс",
                x, y, r, res, run_time);
    }
}
