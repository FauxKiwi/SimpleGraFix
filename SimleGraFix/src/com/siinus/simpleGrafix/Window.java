package com.siinus.simpleGrafix;

import com.siinus.simpleGrafix.input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Window {
    private int width, height;
    private final int finalWidth, finalHeight;
    private float scale;
    private final float finalScale;
    private boolean scaleOnResize = false;

    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy strategy;
    private Graphics graphics;

    private Input input;
    private GameLoop gameLoop;

    private boolean resizing = false;

    public Window(GameLoop gameLoop, String title, int width, int height, float scale) {
        finalWidth = width;
        finalHeight = height;
        finalScale = scale;

        this.gameLoop = gameLoop;

        frame = new JFrame(title);

        constructor(width, height, scale, true);

        frame.setLocationRelativeTo(null);
    }

    private void constructor(int width, int height, float scale, boolean initFrame) {
        this.width = width;
        this.height = height;
        this.scale = scale;

        if (initFrame) {
            initFrame();
        }

        image = new BufferedImage((int) (width / scale), (int) (height / scale), 1);

        if (!initFrame) {
            frame.remove(canvas);
        }
        canvas = new Canvas();
        canvas.setSize(width, height);
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();

        canvas.createBufferStrategy(2);
        strategy = canvas.getBufferStrategy();
        graphics = strategy.getDrawGraphics();

        if (!initFrame) {
            gameLoop.getRenderer().width = (int) (width / scale);
            gameLoop.getRenderer().height = (int) (height / scale);
            gameLoop.getRenderer().pixels = ((DataBufferInt) this.getImage().getRaster().getDataBuffer()).getData();

            canvas.removeKeyListener(input);
            canvas.removeMouseListener(input);
            canvas.removeMouseMotionListener(input);
            canvas.removeMouseWheelListener(input);
        }

        input = new Input(this);
    }

    private void initFrame() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(width,height);
        frame.setResizable(true);
    }

    public void update() {
        if (frame.getWidth()-16 != width || frame.getHeight()-39 != height) {
            width = frame.getWidth()-16;
            height = frame.getHeight()-39;
            if (!resizing) {
                resizing = true;
            }
        } else {
            if (resizing) {
                resizing = false;
                if (scaleOnResize) {
                    scale = finalScale * (height<=width?((float) height) / ((float) finalHeight):((float) width) / ((float) finalWidth));
                }
                System.out.println("Resized frame to: "+(frame.getWidth()-16) + " x " + (frame.getHeight()-39));
                constructor(frame.getWidth() - 16, frame.getHeight() - 39, scale, false);
            }
        }
        graphics.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        strategy.show();
        input.update();
    }

    public JFrame getFrame() {
        return frame;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getScale() {
        return scale;
    }

    public Input getInput() {
        return input;
    }

    public BufferStrategy getStrategy() {
        return strategy;
    }

    public Graphics getGraphics() {
        return this.graphics;
    }

    public void setScaleOnResize(boolean scaleOnResize) {
        this.scaleOnResize = scaleOnResize;
    }

    public boolean isScaleOnResize() {
        return scaleOnResize;
    }
}
