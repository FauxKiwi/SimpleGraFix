package com.siinus.simpleGrafixShader;

import com.siinus.simpleGrafix.Renderer;
import com.siinus.simpleGrafix.Window;
import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafix.gfx.ImageTile;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ShaderRenderer extends Renderer {

    protected int[] lightMap;
    protected int[] lightBlock;

    protected int ambientLight = 0xff3f3f3f;

    public ShaderRenderer(Window window) {
        super(window);
        lightMap = new int[pixels.length];
        lightBlock = new int[pixels.length];
    }

    @Override
    public void clear() {
        super.clear();
        Arrays.fill(lightMap, ambientLight);
        Arrays.fill(lightBlock, 0);
    }

    private void setLightMap(int x, int y, int value) {
        if (x < 0 || x > width || y < 0 || y > height) {
            return;
        }
        int baseColor = lightMap[x + y * width];

        int maxR = Math.max((baseColor >> 16) & 0xff, (value >> 16) & 0xff);
        int maxG = Math.max((baseColor >> 8) & 0xff, (value >> 8) & 0xff);
        int maxB = Math.max((baseColor) & 0xff, (value) & 0xff);

        lightMap[x + y * width] = (maxR << 16 | maxG << 8 | maxB);
    }

    private void setLightBlock(int x, int y, int value) {
        if (x < 0 || x > width || y < 0 || y > height) {
            return;
        }
        lightBlock[x + y * width] = value;
    }

    @Override
    public void process() {
        if (lightMap.length != pixels.length) {
            int[] copy = lightMap;
            lightMap = new int[pixels.length];
            int length = Math.min(copy.length, pixels.length);
            System.arraycopy(copy, 0, lightMap, 0, length);
            copy = lightBlock;
            lightBlock = new int[pixels.length];
            System.arraycopy(copy, 0, lightBlock, 0, length);
        }
        for (int i=0; i<lightMap.length; i++) {
            float r = ((lightMap[i] >> 16) & 0xff) / 255.0f;
            float g = ((lightMap[i] >> 8) & 0xff) / 255.0f;
            float b = ((lightMap[i]) & 0xff) / 255.0f;

            pixels[i] = ((int) (((pixels[i] >> 16) & 0xff) * r) << 16 | (int) (((pixels[i] >> 8) & 0xff) * g) << 8 | (int) (((pixels[i]) & 0xff) * b));
        }
    }

    public void drawLight(Light light, int offX, int offY) {
        for (int i=0; i<=light.getRadius()*2; i++) {
            drawLightLine(light, light.getRadius(), light.getRadius(), i, 0, offX, offY);
            drawLightLine(light, light.getRadius(), light.getRadius(), i, light.getRadius()*2, offX, offY);
            drawLightLine(light, light.getRadius(), light.getRadius(), 0, i, offX, offY);
            drawLightLine(light, light.getRadius(), light.getRadius(), light.getRadius()*2, i, offX, offY);
        }
    }

    private void drawLightLine(Light l, int x0, int y0, int x1, int y1, int offX, int offY) {
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

            int color = l.getLightValue(x0, y0);
            if (color==0) {
                return;
            }

            if (lightBlock[x + y * width] == 1) {
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
            this.drawShader(offX, offY, image.getWidth(), image.getHeight(), ((IShaderImage) image).getLightBlock());
        }
    }

    @Override
    public void drawImageTile(@NotNull ImageTile image, int offX, int offY, int tileX, int tileY) {
        super.drawImageTile(image, offX, offY, tileX, tileY);
        if (image instanceof IShaderImage) {
            this.drawShader(offX, offY, image.getTileWidth(), image.getTileHeight(), ((IShaderImage) image).getLightBlock());
        }
    }

    public void makeLightImage(Image image, int offX, int offY) {
        this.drawLightImage(offX, offY, image.getWidth(), image.getHeight(), (x, y) -> image.getPixels()[x + y * image.getWidth()]);
    }

    public void makeLightImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
        this.drawLightImage(offX, offY, image.getWidth(), image.getHeight(), (x, y) -> image.getPixels()[x + tileX * image.getTileWidth() + (y + tileY * image.getTileHeight()) * image.getWidth()]);
    }

    protected void drawShader(int offX, int offY, int width, int height, int lightBlock) {
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                //this.setPixel(x + offX, y + offY, pixelIndex.get(x, y));
                this.setLightBlock(x + offX, y + offY, lightBlock);
            }
        }
    }

    protected void drawLightImage(int offX, int offY, int width, int height, PixelIndexer pixelIndex) {
        for(int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                this.setLightMap(x + offX, y + offY, pixelIndex.get(x, y));
            }
        }
    }

    public int getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(int ambientLight) {
        this.ambientLight = ambientLight;
    }
}
