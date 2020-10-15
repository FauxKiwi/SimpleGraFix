package com.siinus.simpleGrafix;

/**
 * The main class of the api: The game loop.<br>
 * This is a runnable that is executed by a thread and updates and renders the program.<br>
 * It also holds the {@link Window} and the {@link Renderer}.
 *
 * @author Simon
 * @since 1.0
 */
public class GameLoop implements Runnable {
    private Window window;
    private Renderer renderer;
    private final Program program;

    private Thread thread;

    private volatile boolean running;
    private final double CAP = 1.0/60.0;
    private boolean capFps = true;
    private boolean render;

    private int fps = 0;

    GameLoop(Program program) {
        this.program = program;

        start();
    }

    public void start() {
        window = new Window(this, "Simple GraFix",960,509,0.5f);
        if (program.icon!=null) {
            window.getFrame().setIconImage(program.icon.getImage());
        }
        renderer = new Renderer(window);
        window.getFrame().setVisible(true);
        window.getFrame().setEnabled(true);

        program.window = window;
        program.renderer = renderer;

        program.start();

        capFps = program.capFps;

        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        program.stop();
        running = false;
    }

    @Override
    public synchronized void run() {
        running = true;

        double time1;
        double time2 = System.nanoTime() / 1000000000.0;
        double timeP;
        double timeU = 0;

        double timeF = 0;
        int frames = 0;

        while (running) {
            render = false;

            time1 = System.nanoTime() / 1000000000.0;
            timeP = time1 - time2;
            time2 = time1;

            timeU += timeP;
            timeF += timeP;

            while (timeU >= CAP) {
                timeU -= CAP;
                render = true;

                program.update();
                window.getInput().update();

                if (timeF >= 1.0) {
                    timeF = 0;
                    fps = frames;
                    frames = 0;
                }
            }

            if (!capFps || render) {
                renderer.clear();

                program.render();
                renderer.process();

                window.update();

                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        dispose();
    }

    private void dispose() {
        window.getFrame().setVisible(false);
        System.exit(0);
    }

    /**
     * Returns the window of this program.
     *
     * @return The window
     */
    public Window getWindow() {
        return window;
    }

    /**
     * Returns the renderer of this program.
     *
     * @return The renderer
     */
    public Renderer getRenderer() {
        return renderer;
    }

    /**
     * Sets the window of this program.<br>
     * <b>Warning</b>: Do not use if you don't know what you're doing!
     *
     * @param window The new window
     */
    public void setWindow(Window window) {
        this.window = window;
    }

    /**
     * Sets the renderer of this program.<br>
     * <b>Warning</b>: Do not use if you don't know what you're doing!
     *
     * @param renderer The new renderer
     */
    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
}
