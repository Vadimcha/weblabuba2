package org.example.helpers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

public class BatmanHitTest {

    private static final BufferedImage batmanImg;

    static {
        try (InputStream is = BatmanHitTest.class.getResourceAsStream("/batman.png")) {
            if (is == null) throw new RuntimeException("batman.png not found in resources!");
            batmanImg = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load batman.png", e);
        }
    }

    /**
     * Проверка попадания точки в силуэт Бэтмена (BigDecimal версия).
     */
    public static boolean isInBatman(BigDecimal x, BigDecimal y, BigDecimal R) {

        double xd = x.doubleValue();
        double yd = y.doubleValue();
        double Rd = R.doubleValue();

        double plot_size = batmanImg.getWidth();

        double center = plot_size / 2;

        double rpx = (plot_size - 85) / 2;  // R в пикселях
        double scale = rpx / yd;

        double px = center + xd * scale;
        double py = center + yd * scale;

        int ix = (int) Math.round(px);
        int iy = (int) Math.round(py);

        if (ix < 0 || iy < 0 || ix >= plot_size || iy >= plot_size)
            return false;

        int argb = batmanImg.getRGB(ix, iy);
        Color c = new Color(argb, true);
        System.out.println("COLOR" + c.getRGB());

        boolean isBlue =
                c.getBlue() > 150 &&
                        c.getRed()  < 120 &&
                        c.getGreen() < 160;

        return isBlue;
    }
}
