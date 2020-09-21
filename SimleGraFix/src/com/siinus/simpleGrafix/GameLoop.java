package com.siinus.simpleGrafix;

public class GameLoop implements Runnable {
    private Window window;
    private Renderer renderer;
    private final Program program;

    private Thread thread;

    private volatile boolean running;
    private final double CAP = 1.0/60.0;
    private boolean render;

    public GameLoop(Program program) {
        this.program = program;

        start();
    }

    public void start() {
        window = new Window("Simple GraFix",1280,720,1);
        if (program.icon!=null) {
            window.setIconImage(program.icon.getImage());
        }
        renderer = new Renderer(window);
        window.setVisible(true);
        window.setEnabled(true);

        program.start();

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
        int fps = 0;
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

                if (timeF >= 1.0) {
                    timeF = 0;
                    fps = frames;
                    frames = 0;

                    System.out.println("FPS: "+fps);
                }
            }

            if (render) {
                renderer.clear();

                program.render();

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
        window.setVisible(false);
        System.exit(0);
    }

    public Window getWindow() {
        return window;
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
