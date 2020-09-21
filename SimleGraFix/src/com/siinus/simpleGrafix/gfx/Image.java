package com.siinus.simpleGrafix.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Image {
    private BufferedImage image = null;
    private int width, height;
    private int[] pixels;

    public Image(String path) {

        try {
            if (path.startsWith("http://") || path.startsWith("https://")) {
                image = ImageIO.read(new URL(path));
            } else {
                image = ImageIO.read(Image.class.getResourceAsStream(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        width = image.getWidth();
        height = image.getHeight();

        pixels = image.getRGB(0, 0, width, height, null, 0, width);

        image.flush();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return pixels;
    }

    public BufferedImage getImage() {
        return image;
    }
}
