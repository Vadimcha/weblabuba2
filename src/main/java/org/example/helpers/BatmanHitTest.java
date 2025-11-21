package org.example.helpers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

public class BatmanHitTest {
    static long runTime;

    private static final BufferedImage batmanImg;

    static {
        try (InputStream is = BatmanHitTest.class.getResourceAsStream("/batman 1.png")) {
            if (is == null) throw new RuntimeException("batman.png not found in resources!");
            batmanImg = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load batman.png", e);
        }
    }

    /**
     * Проверка попадания точки в силуэт Бэтмена (BigDecimal версия).
     * Полностью синхронизировано с Python и JS.
     */
    public static boolean isInBatman(BigDecimal x, BigDecimal y, BigDecimal R) {
        long startTime = System.nanoTime();

        double xd = x.doubleValue();
        double yd = y.doubleValue();
        double Rd = R.doubleValue();

        double plotSize = batmanImg.getWidth();
        double center = plotSize / 2.0;

        // радиус в пикселях — как в Python и JS
        double rpx = (plotSize - 85.0) / 2.0;
        double scale = rpx / Rd;   // пикселей на 1 единицу координат

        // координаты в системе изображения (0 — сверху)
        double px = center + xd * scale;
        double py = center + yd * scale;

        // инверсия оси Y, как в JS (bottom)
        py = plotSize - py;

        int ix = (int)Math.round(px);
        int iy = (int)Math.round(py);

        // Проверка границ
        if (ix < 0 || iy < 0 || ix >= plotSize || iy >= plotSize)
            return false;

        // Цвет
        int argb = batmanImg.getRGB(ix, iy);
        Color c = new Color(argb, true);

//        System.out.printf("DOT: %d, %d, %d", x, y, R);
//        System.out.println("COLOR: R=" + c.getRed() + " G=" + c.getGreen() + " B=" + c.getBlue());

        long endTime = System.nanoTime();
        runTime = endTime - startTime;
        // детектирование синего
        return  c.getBlue()  > 150 &&
                c.getRed()   < 120 &&
                c.getGreen() < 160;
    }

    public static String getRunTime() {
        return Long.toString(runTime);
    }
}
