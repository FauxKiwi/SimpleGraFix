package com.siinus.simpleGrafix;

import com.siinus.simpleGrafix.gfx.Font;
import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafix.gfx.ImageTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.Arrays;

public class Renderer {
    private int width, height;
    private int[] pixels;

    public Renderer(Window window) {
        width = window.getWidth();
        height = window.getHeight();
        pixels = ((DataBufferInt) window.getImage().getRaster().getDataBuffer()).getData();
    }

    public void clear() {
        Arrays.fill(pixels, 0);
    }

    public void setPixel(int x, int y, int value) {
        if ((x < 0 || x >= width || y < 0 || y >= height) || (value >> 6 & 0xff) == 0) {
            return;
        }

        pixels[x + y * (width)] = value;
    }

    public void drawImage(@NotNull Image image, int offX, int offY) {
        draw(offX, offY, image.getWidth(), image.getHeight(), ((x, y) -> image.getPixels()[x + y * image.getWidth()]));
    }

    public void drawImageTile(@NotNull ImageTile image, int offX, int offY, int tileX, int tileY) {
        draw(offX, offY, image.getTileWidth(), image.getTileHeight(), ((x, y) -> image.getPixels()[x + tileX * image.getTileWidth() + (y + tileY * image.getTileHeight()) * image.getWidth()]));
    }

    public void drawText(@NotNull String text, int offX, int offY, int color, @Nullable Font font) {
        font = font==null?Font.STANDARD:font;
        Image fontImage = font.getFontImage();

        int x = offX;
        int y = offY;
        for (char c : text.toCharArray()) {
            if (c=='\n') {
                y+=font.getSize()+5;
            }
            if (c<0x20 || c>font.getChars()+0x20) {
                continue;
            }
            Font finalFont = font;
            draw(x, y, font.getWidths()[c-0x20], font.getSize(), ((x1, y1) -> fontImage.getPixels()[x1 + finalFont.getOffsets()[c-0x20] + y1 * fontImage.getWidth()]==0xffffffff?color:0));
            x += font.getWidths()[c-0x20]+1;
        }
    }

    private void draw(int offX, int offY, int width, int height, PixelIndexer pixelIndex) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                setPixel(x + offX, y + offY, pixelIndex.get(x, y));
            }
        }
    }

    @FunctionalInterface
    private static interface PixelIndexer {
        public int get(int x, int y);
    }
}