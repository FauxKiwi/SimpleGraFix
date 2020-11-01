package com.siinus.simpleGrafixShader;

public class LightLite {
    private int radius;
    private short[] lightMap;

    public LightLite(int radius, int intensity) {
        this.radius = radius;
        lightMap = new short[radius * radius * 4];

        for (int y=0; y<radius*2; y++) {
            for (int x=0; x<radius*2; x++) {
                double distance = Math.sqrt((x-radius) * (x-radius) + (y-radius) * (y-radius));

                if (distance < radius) {
                    double mPower = 1 - (distance / radius);
                    lightMap[x + y * radius * 2] = (short) (mPower * intensity);
                } else {
                    lightMap[x + y * radius * 2] = 0;
                }
            }
        }
    }

    public short getLightValue(int x, int y) {
        if (x < 0 || x >= radius * 2 || y < 0 || y >= radius * 2) {
            return 0;
        }
        return lightMap[x + y * radius * 2];
    }

    public int getRadius() {
        return radius;
    }

    public short[] getLightMap() {
        return lightMap;
    }
}
