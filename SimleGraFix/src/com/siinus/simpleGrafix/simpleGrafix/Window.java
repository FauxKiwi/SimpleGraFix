package com.siinus.simpleGrafix;

import com.siinus.simpleGrafix.input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * The window of the game.
 * An instance of this class is attached to the main window and can be accessed using {@link GameLoop#getWindow()}.
 *
 * @author Simon
 * @since 1.0
 */
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

    /**
     * Creates a new renderer object.<br>
     * <b>Warning</b>: This should not be needed to be used.
     *
     * @param gameLoop The associated game loop
     * @param title The title
     * @param width The width
     * @param height The height
     * @param scale The scale (how many real pixels are one pixel in the program)
     */
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

    /**
     * Updates the window.<br>
     * Called in the game loop every tick.
     */
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

    /**
     * Returns the associated {@link JFrame}.
     *
     * @return The JFrame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Returns the rendered {@link BufferedImage}.
     *
     * @return The image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Returns the associated {@link Canvas}.
     *
     * @return The canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Returns the width of the window.
     *
     * @return The width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the window.
     *
     * @return The height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the scale of the window.
     *
     * @return The scale
     */
    public float getScale() {
        return scale;
    }

    /**
     * Returns the associated {@link Input} manager.
     *
     * @return The input manager
     */
    public Input getInput() {
        return input;
    }

    /**
     * Returns the associated {@link BufferStrategy}.
     *
     * @return The buffer strategy
     */
    public BufferStrategy getStrategy() {
        return strategy;
    }

    /**
     * Returns the associated {@link Graphics}.
     *
     * @return The Java graphics
     */
    public Graphics getGraphics() {
        return this.graphics;
    }

    /**
     * Sets if the window should scale on resizing. Defaults to false.
     *
     * @param scaleOnResize If the window scales on resize
     */
    public void setScaleOnResize(boolean scaleOnResize) {
        this.scaleOnResize = scaleOnResize;
    }

    /**
     * Returns if the window scales on resizing.
     *
     * @return If the window scales on resize.
     */
    public boolean isScaleOnResize() {
        return scaleOnResize;
    }
}
