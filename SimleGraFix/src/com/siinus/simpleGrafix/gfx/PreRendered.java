package com.siinus.simpleGrafix.gfx;

import com.siinus.simpleGrafix.Renderer;

public class PreRendered {
    private final int[][] imageData;
    private final int h, w;
    private final int fps;
    private final int fpf;
    private int frame;
    private int tick;
    private final int maxFrames;

    public PreRendered(int[][] imageData, int width, int height, int fps) {
        this.imageData = imageData;
        w = width;
        h = height;
        this.fps = fps;
        maxFrames = imageData.length;
        fpf = 60 / fps;
        tick = fpf;
    }

    public void nextFrame() {
        frame = frame < maxFrames - 1 ? frame+1 : 0;
    }

    public void update() {
        if (tick < fpf) {
            tick++;
        }
        if (tick >= fpf) {
            tick = 0;
            nextFrame();
        }
    }

    public void render(Renderer renderer, int offX, int offY) {
        renderer.draw(offX, offY, w, h, ((x, y) -> imageData[frame][x + y * w]));
    }
}
