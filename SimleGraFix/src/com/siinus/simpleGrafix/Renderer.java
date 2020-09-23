package com.siinus.simpleGrafix;

import com.siinus.simpleGrafix.gfx.Font;
import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafix.gfx.ImageTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Renderer {
    protected int width, height;
    protected int[] pixels;
    protected int bgColor;

    public Renderer(Window window) {
        width = window.getWidth();
        height = window.getHeight();
        pixels = ((DataBufferInt) window.getImage().getRaster().getDataBuffer()).getData();
    }

    public void clear() {
        Arrays.fill(pixels, bgColor);
    }

    public void process() { }

    public void setPixel(int x, int y, int value) {
        int alpha = ((value >> 24) & 0xff);

        if ((x < 0 || x >= width || y < 0 || y >= height) || alpha == 0) {
            return;
        }

        if (alpha == 0xff) {
            pixels[x + y * (width)] = value;
            return;
        }

        int pColor = pixels[x+ +y * width];

        int r = ((pColor >> 16) & 0xff) - (int) ((((pColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255.0f));
        int g = ((pColor >> 8) & 0xff) - (int) ((((pColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255.0f));
        int b = ((pColor) & 0xff) - (int) ((((pColor) & 0xff) - ((value) & 0xff)) * (alpha / 255.0f));

        pixels[x + y * width] = (r << 16 | g << 8 | b);
    }

    public void drawImage(@NotNull Image image, int offX, int offY) {
        draw(offX, offY, image.getWidth(), image.getHeight(), ((x, y) -> image.getPixels()[x + y * image.getWidth()]));
    }

    public void drawImageTile(@NotNull ImageTile image, int offX, int offY, int tileX, int tileY) {
        draw(offX, offY, image.getTileWidth(), image.getTileHeight(), ((x, y) -> image.getPixels()[x + tileX * image.getTileWidth() + (y + tileY * image.getTileHeight()) * image.getWidth()]));
    }

    public void drawText(@NotNull String text, int offX, int offY, int color, @Nullable Font font) {
        font = font==null?Font.getStandard():font;
        Image fontImage = font.getFontImage();

        int x = offX;
        int y = offY;
        for (char c : text.toCharArray()) {
            if (c=='\n') {
                y+=font.getSize()+5;
                x = offX;
            }
            if (c<font.getStartChar() || c>font.getChars()+font.getStartChar()) {
                continue;
            }
            Font finalFont = font;
            draw(x, y, font.getWidths()[c-font.getStartChar()], font.getImageSize(), ((x1, y1) -> fontImage.getPixels()[x1 + finalFont.getOffsets()[c- finalFont.getStartChar()] + y1 * fontImage.getWidth()]==0xffffffff?color:0));
            x += font.getWidths()[c-font.getStartChar()]+1;
        }
    }

    protected void draw(int offX, int offY, int width, int height, PixelIndexer pixelIndex) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                setPixel(x + offX, y + offY, pixelIndex.get(x, y));
            }
        }
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    @FunctionalInterface
    protected static interface PixelIndexer {
        public int get(int x, int y);
    }
}