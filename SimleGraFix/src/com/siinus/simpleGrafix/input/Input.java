package com.siinus.simpleGrafix.input;

import com.siinus.simpleGrafix.Window;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private Window window;

    public static final int KEYS = 256;
    private boolean[] keys = new boolean[KEYS];
    private boolean[] keysLast = new boolean[KEYS];
    private boolean keyDown = false;

    public static final int BUTTONS = 3;
    private boolean[] buttons = new boolean[BUTTONS];
    private boolean[] buttonsLast = new boolean[BUTTONS];
    private boolean buttonDown = false;

    private int mouseX, mouseY;
    private int scroll;

    public Input (Window window) {
        this.window = window;
        mouseX = 0;
        mouseY = 0;
        scroll = 0;

        window.getCanvas().addKeyListener(this);
        window.getCanvas().addMouseListener(this);
        window.getCanvas().addMouseMotionListener(this);
        window.getCanvas().addMouseWheelListener(this);
    }

    public void update() {
        System.arraycopy(keys, 0, keysLast, 0, KEYS);
        System.arraycopy(buttons, 0, buttonsLast, 0, BUTTONS);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyDown = true;
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyDown = false;
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttonDown = true;
        buttons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttonDown = false;
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = (int) (e.getX() / window.getScale());
        mouseY = (int) (e.getY() / window.getScale());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    /* -------------------- User-relevant Methods ------------------------ */

    public boolean isKeyPressed(int keyCode) {
        return keys[keyCode];
    }

    public boolean isKeyDown(int keyCode) {
        return keys[keyCode] && !keysLast[keyCode];
    }

    public boolean isKeyUp(int keyCode) {
        return !keys[keyCode] && keysLast[keyCode];
    }

    public boolean isButtonPressed(int button) {
        return buttons[button];
    }

    public boolean isButtonDown(int button) {
        return buttons[button] && !buttonsLast[button];
    }

    public boolean isButtonUp(int button) {
        return !buttons[button] && buttonsLast[button];
    }

    public int getScroll() {
        return scroll;
    }

    public boolean isKeyDown() {
        return keyDown;
    }

    public boolean isButtonDown() {
        return buttonDown;
    }
}
