package com.siinus.simpleGrafix;

import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafix.input.Input;

public abstract class Program {
    protected static GameLoop gameLoop;
    Image icon = null;

    protected final void init() {
        gameLoop = new GameLoop(this);
    }

    public abstract void start();

    public abstract void update();

    public abstract void render();

    public abstract void stop();

    public final Window getWindow() {
        return gameLoop.getWindow();
    }

    public final Input getInput() {
        return getWindow().getInput();
    }

    public final Renderer getRenderer() {
        return gameLoop.getRenderer();
    }

    protected final void setIconImage(Image icon) {
        this.icon = icon;
    }
}
