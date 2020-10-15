package com.siinus.simpleGrafix.gfx;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Represents an image that can be rendered by this api.
 *
 * @author Simon
 * @since 1.0
 */
public class Image {
    private BufferedImage image = null;
    private final int width, height;
    private final int[] pixels;

    /**
     * Creates a new image.
     *
     * @param path The path to the texture
     */
    public Image(@NotNull String path) {
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

    /**
     * Returns the width of the image.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the image.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns all pixels of the image.
     *
     * @return the pixels
     */
    public int[] getPixels() {
        return pixels;
    }

    /**
     * Returns the used {@link BufferedImage} for this image.
     *
     * @return the Java image
     */
    public BufferedImage getImage() {
        return image;
    }
}
