package com.ifmo.imageserver.Utils;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyComponent extends JComponent {
    private BufferedImage img;

    @Getter
    private BufferedImage tstimg;

    public MyComponent(String filePath, int commandID) {

        img = ImageUtil.loadImage(filePath);
        // применяем один из эффектов
        switch (commandID) {
            case (1):
                System.out.println("to B/W");
                tstimg = ImageUtil.gray(img);
                break;
            case (2):
                System.out.println("to blur");
                tstimg = ImageUtil.blur(img);
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(img, 0, 0, null);
        g2.drawImage(tstimg, img.getWidth()+5, 0, null);
    }
}