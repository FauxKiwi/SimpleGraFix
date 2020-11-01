package com.siinus.simpleGrafix.gfx.geom;

import com.siinus.simpleGrafix.Renderer;

public class Rectangle {
    private int ox, oy, w, h, c;

    public Rectangle(int x, int y, int width, int height, int color) {
        ox = x;
        oy = y;
        w = width;
        h = height;
        c = color;
    }

    public void renderOutline(Renderer renderer) {
        for (int x=0; x<w; x++) {
            renderer.setPixel(x + ox, oy, c);
            renderer.setPixel(x + ox, oy + h, c);
        }
        for (int y=0; y<h; y++) {
            renderer.setPixel(ox, y + oy, c);
            renderer.setPixel(ox + w, y + oy, c);
        }
    }

    public void renderFill(Renderer renderer) {
        for (int y=0; y<h; y++) {
            for (int x=0; x<w; x++) {
                renderer.setPixel(x + ox, y + oy, c);
            }
        }
    }

    public int getOx() {
        return ox;
    }

    public void setOx(int ox) {
        this.ox = ox;
    }

    public int getOy() {
        return oy;
    }

    public void setOy(int oy) {
        this.oy = oy;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}
