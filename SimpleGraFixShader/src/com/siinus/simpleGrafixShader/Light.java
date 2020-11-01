package com.siinus.simpleGrafixShader;

public class Light {
    private int radius, color;
    private int[] lightMap;

    public Light(int radius, int color) {
        this.radius = radius;
        this.color = color;
        lightMap = new int[radius * radius * 4];

        for (int y=0; y<radius*2; y++) {
            for (int x=0; x<radius*2; x++) {
                double distance = Math.sqrt((x-radius) * (x-radius) + (y-radius) * (y-radius));

                if (distance < radius) {
                    double mPower = 1 - (distance / radius);
                    lightMap[x + y * radius * 2] = ((int) (((color >> 16) & 0xff) * mPower) << 16 | (int) (((color >> 8) & 0xff) * mPower) << 8 | (int) (((color) & 0xff) * mPower));;
                } else {
                    lightMap[x + y * radius * 2] = 0;
                }
            }
        }
    }

    public int getLightValue(int x, int y) {
        if (x < 0 || x >= radius * 2 || y < 0 || y >= radius * 2) {
            return 0;
        }
        return lightMap[x + y * radius * 2];
    }

    public int getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }

    public int[] getLightMap() {
        return lightMap;
    }
}
