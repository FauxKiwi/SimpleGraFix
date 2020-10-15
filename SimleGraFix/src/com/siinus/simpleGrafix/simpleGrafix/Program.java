package com.siinus.simpleGrafix;

import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafix.input.Input;

/**
 * Represents a program that is executed by this api.<br>
 * An application that uses the api should extend this class in its main class.<br>
 * To start the application, run the {@link Program#init()} method.
 *
 * @author Simon
 * @since 1.0
 */
public abstract class Program {
    protected static GameLoop gameLoop;
    Window window;
    Renderer renderer;
    Image icon = null;
    boolean capFps = true;

    /**
     * Initializes the program and starts the game loop.<br>
     * Should be called once after creating the application to start it.
     */
    protected final void init() {
        gameLoop = new GameLoop(this);
    }

    /**
     * Called at the start of the game loop.
     */
    public abstract void start();

    /**
     * Called every tick of the game loop.
     */
    public abstract void update();

    /**
     * Called every frame of the game loop.<br>
     * Everything that uses the {@link Renderer} belongs in here.
     */
    public abstract void render();

    /**
     * Called at the end of the game loop.
     */
    public abstract void stop();

    /**
     * Returns the window.
     *
     * @return The window
     */
    public final Window getWindow() {
        return window;
    }

    /**
     * Returns the input manager.
     *
     * @return The input manager
     */
    public final Input getInput() {
        return getWindow().getInput();
    }

    /**
     * Returns the renderer.<br>
     * This should be used in {@link Program#render()}, not in {@link Program#update()}.
     *
     * @return The renderer
     */
    public final Renderer getRenderer() {
        return renderer;
    }

    /**
     * Sets the icon image of the window.<br>
     * Should be called in the constructor.
     *
     * @param icon The new icon
     */
    protected final void setIconImage(Image icon) {
        this.icon = icon;
    }

    /**
     * Sets, if the fps should be capped at 60.<br>
     * Should be called in the constructor.
     *
     * @param capFps If the fps should be capped.
     */
    protected final void setCapFps(boolean capFps) {
        this.capFps = capFps;
    }
}
