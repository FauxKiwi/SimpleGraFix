package com.siinus.simpleGrafix;

import com.siinus.simpleGrafix.input.Input;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {
    private int width, height;
    private float scale;

    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy strategy;
    private Graphics graphics;

    private Input input;

    public Window(String title, int width, int height, float scale) {
        super(title);

        this.width = width;
        this.height = height;
        this.scale = scale;

        initFrame();

        image = new BufferedImage(width, height, 1);
        canvas = new Canvas();
        canvas.setSize((int) (width * scale), (int) (height * scale));
        add(canvas, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);

        canvas.createBufferStrategy(2);
        strategy = canvas.getBufferStrategy();
        graphics = strategy.getDrawGraphics();

        input = new Input(this);
    }

    private void initFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(width,height);
        setResizable(false);
    }

    public void update() {
        graphics.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        strategy.show();
        input.update();
    }

    public BufferedImage getImage() {
        return image;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public float getScale() {
        return scale;
    }

    public Input getInput() {
        return input;
    }
}
