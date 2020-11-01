package com.siinus.simpleGrafix.gfx;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class LowRamImage {
    private int width, height;
    private byte[] pixels;

    public LowRamImage(@NotNull String path) throws IOException {
        BufferedImage image;

        if (path.startsWith("http://") || path.startsWith("https://")) {
            image = ImageIO.read(new URL(path));
        } else {
            image = ImageIO.read(Image.class.getResourceAsStream(path));
        }

        width = image.getWidth();
        height = image.getHeight();

        pixels = new byte[width * height * 3];
        int c;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                c = image.getRGB(x, y);
                pixels[(x + y * width) * 3] = (byte) ((c >> 16) & 0xff);
                pixels[(x + y * width) * 3 + 1] = (byte) ((c >> 8) & 0xff);
                pixels[(x + y * width) * 3 + 2] = (byte) (c & 0xff);
            }
        }

        image.flush();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte[] getPixels() {
        return pixels;
    }

    public int getPixelColor(int i) {
        return 0xff000000 | (unsigned(pixels[i*3]) << 16) | (unsigned(pixels[i*3 + 1]) << 8) | unsigned(pixels[i*3 + 2]);
    }

    public short unsigned(byte b) {
        return b<0? (short) (0x100 + (short) b) :b;
    }
}
