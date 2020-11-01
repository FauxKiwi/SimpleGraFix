package com.siinus.simpleGrafix.gfx;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageGif extends Image {
    private ImageInputStream in;
    private ImageReader reader;
    private int frame = 0;
    private int count;

    private BufferedImage[] images;

    /**
     * Creates a new gif image.
     *
     * @param path The path to the texture
     */
    public ImageGif(@NotNull String path) {
        super(path);
        try {
            reader = ImageIO.getImageReadersBySuffix("GIF").next();
            in = ImageIO.createImageInputStream(ImageGif.class.getResourceAsStream(path));
            reader.setInput(in);
            count = reader.getNumImages(true);
            images = new BufferedImage[count];
            new Thread(() -> {
                try {
                    loadImages();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadImages() throws IOException {
        frame = 0;
        while (frame < count) {
            images[frame] = reader.read(frame, reader.getDefaultReadParam());
            frame++;
        }
        frame = 0;
    }

    public void nextImage() {
        if (frame >= count) {
            frame = 0;
        }
        setImage(images[frame++]);
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int getFrame() {
        return frame;
    }

    public int getCount() {
        return count;
    }
}
