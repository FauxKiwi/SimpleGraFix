package com.siinus.simpleGrafix.input;

import com.siinus.simpleGrafix.Window;
import org.jetbrains.annotations.NotNull;

import java.awt.event.*;

/**
 * Is used to get various types of input actions.<br>
 * One instance of this class automatically is attached to the main window and can be accessed using {@link Window#getInput()}.
 *
 * @author Simon
 * @since 1.0
 */
public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private final Window window;

    public static final int KEYS = 256;
    private boolean[] keys = new boolean[KEYS];
    private boolean[] keysLast = new boolean[KEYS];
    private boolean keyLast = false;
    private boolean keyDown = false;
    private char lastKey = 0;

    public static final int BUTTONS = 5;
    private boolean[] buttons = new boolean[BUTTONS];
    private boolean[] buttonsLast = new boolean[BUTTONS];
    private boolean buttonLast = false;
    private boolean buttonDown = false;

    private int mouseX, mouseY;
    private int scroll;

    /**
     * Creates a new input object.<br>
     * <b>Warning</b>: This should not be needed to be used.
     *
     * @param window The associated window
     */
    public Input (@NotNull Window window) {
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
        keyLast = keyDown;
        buttonLast = buttonDown;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(@NotNull KeyEvent e) {
        keyDown = true;
        keys[e.getKeyCode()] = true;
        lastKey = e.getKeyChar();
    }

    @Override
    public void keyReleased(@NotNull KeyEvent e) {
        keyDown = false;
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(@NotNull MouseEvent e) {
        buttonDown = true;
        buttons[e.getButton()] = true;
        System.out.println(e.getButton());
    }

    @Override
    public void mouseReleased(@NotNull MouseEvent e) {
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
    public void mouseMoved(@NotNull MouseEvent e) {
        mouseX = (int) (e.getX() / window.getScale());
        mouseY = (int) (e.getY() / window.getScale());
    }

    @Override
    public void mouseWheelMoved(@NotNull MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    /* -------------------- User-relevant Methods ------------------------ */

    /**
     * Checks if the provided key is pressed at the moment.
     *
     * @param keyCode The key (can also be a char)
     * @return If the key is pressed
     */
    public boolean isKeyPressed(int keyCode) {
        return keys[keyCode];
    }

    /**
     * Checks if the provided key is being pushed down at the moment.
     *
     * @param keyCode The key (can also be a char)
     * @return If the key is pushed down
     */
    public boolean isKeyDown(int keyCode) {
        return keys[keyCode] && !keysLast[keyCode];
    }

    /**
     * Checks if the provided key is released at the moment.
     *
     * @param keyCode The key (can also be a char)
     * @return If the key is released
     */
    public boolean isKeyUp(int keyCode) {
        return !keys[keyCode] && keysLast[keyCode];
    }

    /**
     * Checks if the provided mouse button is pressed at the moment.
     *
     * @param button The button (1 is left, 2 is middle, 3 is right)
     * @return If the button is pressed
     */
    public boolean isButtonPressed(int button) {
        return buttons[button];
    }

    /**
     * Checks if the provided mouse button is being pushed down at the moment.
     *
     * @param button The button (1 is left, 2 is middle, 3 is right)
     * @return If the button is pushed down
     */
    public boolean isButtonDown(int button) {
        return buttons[button] && !buttonsLast[button];
    }

    /**
     * Checks if the provided mouse button is released at the moment.
     *
     * @param button The button (1 is left, 2 is middle, 3 is right)
     * @return If the button is released
     */
    public boolean isButtonUp(int button) {
        return !buttons[button] && buttonsLast[button];
    }

    /**
     * Returns the speed of the scroll wheel.<br>
     * 0 if not scrolling, 1 if scrolling upwards, -1 if scrolling downwards.
     *
     * @return The scrolling speed
     */
    public int getScroll() {
        return scroll;
    }

    /**
     * Checks if any key is pressed at the moment.
     *
     * @return If a key is pressed
     */
    public boolean isKeyDown() {
        return keyDown && !keyLast;
    }

    /**
     * Checks if any key is being pushed down at the moment.
     *
     * @return If a key is pushed down
     */
    public boolean isKeyPressed() {
        return keyDown;
    }

    /**
     * Checks if any key is released at the moment.
     *
     * @return If a key is released
     */
    private boolean isKeyUp() {
        return !keyDown && keyLast;
    }

    /**
     * Checks if any mouse button is pressed at the moment.
     *
     * @return If a button is pressed
     */
    public boolean isButtonDown() {
        return buttonDown && !buttonLast;
    }

    /**
     * Checks if any mouse button is being pushed down at the moment.
     *
     * @return If a button is pushed down
     */
    public boolean isButtonPressed() {
        return buttonDown;
    }

    /**
     * Checks if any mouse button is released at the moment.
     *
     * @return If a button is released
     */
    public boolean isButtonUp() {
        return !buttonDown && buttonLast;
    }

    /**
     * Returns the key that was down last.
     *
     * @return The last pressed key
     */
    public char getLastKey() {
        return lastKey;
    }
}
