package com.ifmo.imageserver.Utils;

import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageUtil {

    public static BufferedImage gray(BufferedImage img) { // черно белое изображение
        ColorSpace cs = ColorSpace.getInstance(
                ColorSpace.CS_GRAY);
        BufferedImageOp op = new ColorConvertOp(cs, null);
        return op.filter(img, null);
    }

    public static BufferedImage blur(BufferedImage img) { // размытие изображения
        float val = 1.0f / 9.0f;
        float[] filter = { val, val, val, val, val, val, val, val, val };
        BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, filter));
        return op.filter(img, null);
    }

    public static BufferedImage loadImage(String s) {
        BufferedImage img = null;

        try
        {
            img = ImageIO.read(new File(s));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return img;
    }
}
