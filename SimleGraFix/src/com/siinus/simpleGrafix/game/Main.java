package com.siinus.simpleGrafix.game;

import com.siinus.simpleGrafix.Program;
import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafix.gfx.ImageTile;
import com.siinus.simpleGrafix.sfx.SoundClip;

public class Main extends Program {

    ImageTile image;
    SoundClip soundClip;

    public static void main(String[] args) {
        Main program = new Main();
        program.init();
    }

    public Main() {
        image = new ImageTile("/test.png", 128, 128);
        soundClip = new SoundClip("/service-bell_daniel_simion.wav");
        setIconImage(new Image("https://cdn4.iconfinder.com/data/icons/iconsimple-logotypes/512/github-512.png"));
    }

    @Override
    public void start() {
        soundClip.loop();
    }

    @Override
    public void update() {
        if (getInput().isKeyDown(0x41)) {
            System.out.println("A");
        }
    }

    @Override
    public void render() {
        getRenderer().drawImageTile(image, getInput().getMouseX(), getInput().getMouseY(), 0, 0);
        getRenderer().drawImageTile(image, 500, 500, 0,0);
        getRenderer().drawText("Hi!",100,100,0xffff7f00, null);
    }

    @Override
    public void stop() {

    }
}
