package com.siinus.simpleGrafixShader;

import com.siinus.simpleGrafix.Renderer;
import com.siinus.simpleGrafix.Window;
import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafix.gfx.ImageTile;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ShaderRendererLite extends Renderer {
    protected short[] lightMap;
    protected boolean[] lightBlock;

    private byte ambientLight;

    public ShaderRendererLite(Window window) {
        super(window);
        lightMap = new short[pixels.length];
        lightBlock = new boolean[pixels.length];
    }

    @Override
    public void clear() {
        super.clear();
        Arrays.fill(lightMap, ambientLight);
        Arrays.fill(lightBlock, false);
    }

    private void setLightMap(int x, int y, short value) {
        if (x < 0 || x > width || y < 0 || y > height) {
            return;
        }
        lightMap[x + y * width] = (short) Math.max(lightMap[x + y * width], value);
    }

    private void setLightBlock(int x, int y, boolean value) {
        if (x < 0 || x > width || y < 0 || y > height) {
            return;
        }
        lightBlock[x + y * width] = value;
    }

    @Override
    public void process() {
        if (lightMap.length != pixels.length) {
            short[] copy = lightMap;
            lightMap = new short[pixels.length];
            int length = Math.min(copy.length, pixels.length);
            System.arraycopy(copy, 0, lightMap, 0, length);
            boolean[] copy2 = lightBlock;
            lightBlock = new boolean[pixels.length];
            System.arraycopy(copy2, 0, lightBlock, 0, length);
        }
        for (int i = 0; i < lightMap.length; i++) {
            float lm = lightMap[i] / 255f;
            pixels[i] = ((int) (((pixels[i] >> 16) & 0xff) * lm) << 16 | (int) (((pixels[i] >> 8) & 0xff) * lm) << 8 | (int) (((pixels[i]) & 0xff) * lm));
        }
    }

    public void drawLight(LightLite light, int offX, int offY) {
        for (int i = 0; i <= light.getRadius() * 2; i++) {
            drawLightLine(light, light.getRadius(), light.getRadius(), i, 0, offX, offY);
            drawLightLine(light, light.getRadius(), light.getRadius(), i, light.getRadius() * 2, offX, offY);
            drawLightLine(light, light.getRadius(), light.getRadius(), 0, i, offX, offY);
            drawLightLine(light, light.getRadius(), light.getRadius(), light.getRadius() * 2, i, offX, offY);
        }
    }

    private void drawLightLine(LightLite l, int x0, int y0, int x1, int y1, int offX, int offY) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx - dy;
        int e2;

        while (true) {
            int x = x0 - l.getRadius() + offX;
            int y = y0 - l.getRadius() + offY;

            if (x < 0 || x >= width || y < 0 || y >= height) {
                return;
            }

            if (l.getLightValue(x0, y0) == 0) {
                return;
            }

            if (lightBlock[x + y * width]) {
                return;
            }

            setLightMap(x, y, l.getLightMap()[x0 + y0 * l.getRadius() * 2]);

            if (x0 == x1 && y0 == y1) {
                break;
            }

            e2 = 2 * err;
            if (e2 > -1 * dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    @Override
    public void drawImage(@NotNull Image image, int offX, int offY) {
        super.drawImage(image, offX, offY);
        if (image instanceof IShaderImage) {
            this.drawShader(offX, offY, image.getWidth(), image.getHeight(), ((IShaderImage) image).getLightBlock() > 0);
        }
    }

    @Override
    public void drawImageTile(@NotNull ImageTile image, int offX, int offY, int tileX, int tileY) {
        super.drawImageTile(image, offX, offY, tileX, tileY);
        if (image instanceof IShaderImage) {
            this.drawShader(offX, offY, image.getTileWidth(), image.getTileHeight(), ((IShaderImage) image).getLightBlock() > 0);
        }
    }

    public void makeLightImage(Image image, int offX, int offY) {
        this.drawLightImage(offX, offY, image.getWidth(), image.getHeight(), (x, y) -> (image.getPixels()[x + y * image.getWidth()] >> 24) & 0xff);
    }

    public void makeLightImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
        this.drawLightImage(offX, offY, image.getWidth(), image.getHeight(), (x, y) -> (image.getPixels()[x + tileX * image.getTileWidth() + (y + tileY * image.getTileHeight()) * image.getWidth()] >> 24) & 0xff);
    }

    protected void drawShader(int offX, int offY, int width, int height, boolean lightBlock) {
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                //this.setPixel(x + offX, y + offY, pixelIndex.get(x, y));
                this.setLightBlock(x + offX, y + offY, lightBlock);
            }
        }
    }

    protected void drawLightImage(int offX, int offY, int width, int height, PixelIndexer pixelIndex) {
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                this.setLightMap(x + offX, y + offY, (short) pixelIndex.get(x, y));
            }
        }
    }

    public int getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(byte ambientLight) {
        this.ambientLight = ambientLight;
    }
}
