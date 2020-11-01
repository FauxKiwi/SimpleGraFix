package com.siinus.simpleGrafix.gfx.geom;

import com.siinus.simpleGrafix.Renderer;

public class Line {
    private int x0, x1, y0, y1;
    private int color;

    public Line(int x0, int x1, int y0, int y1, int color) {
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;
        this.color = color;
    }

    public void render(Renderer renderer) {
        if (x0==x1) {
            for (int y=y0; y<y1; y++) {
                renderer.setPixel(x0, y, color);
            }
            return;
        }
        if (y0==y1) {
            for (int x=x0; x<x1; x++) {
                renderer.setPixel(x, y0, color);
            }
            return;
        }
        boolean xm = x0>x1;
        boolean ym = y0>y1;
        double m = ((double)(y1-y0))/((double)(x1-x0));
        int yt0, yt1 = y0;
        for (int x = x0; xm?x>=x1:x<=x1; x+=xm?-1:1) {
            yt0 = yt1;
            yt1 = (int) (y0+(x-x0)*m);
            for (int y=yt0; ym?y>=yt1:y<=yt1; y+=ym?-1:1) {
                renderer.setPixel(x, y, color);
            }
        }
    }

    public int getX0() {
        return x0;
    }

    public void setX0(int x0) {
        this.x0 = x0;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY0() {
        return y0;
    }

    public void setY0(int y0) {
        this.y0 = y0;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
