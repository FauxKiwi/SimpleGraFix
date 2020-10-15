package com.siinus.simpleGrafixShader.game;

import com.siinus.simpleGrafix.Program;
import com.siinus.simpleGrafix.gfx.Font;
import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafix.gfx.ImageTile;
import com.siinus.simpleGrafix.sfx.SoundClip;

public class Main extends Program {

    ImageTile image;
    SoundClip soundClip;
    Image semi;
    Font algerian32 = new Font("/algerian32.png", 32, 32);

    public static void main(String[] args) {
        Main program = new Main();
        program.init();
    }

    public Main() {
        //image = new ImageTile("/test.png", 128, 128);
        //soundClip = new SoundClip("/service-bell_daniel_simion.wav");
        //semi = new Image("/semi.png");

        setIconImage(new Image("https://cdn4.iconfinder.com/data/icons/iconsimple-logotypes/512/github-512.png"));
    }

    @Override
    public void start() {
        //soundClip.loop();
        //getWindow().setResizable(false);
        getWindow().setScaleOnResize(true);
        getRenderer().setBgColor(0xffffffff);
    }

    @Override
    public void update() {
        if (getInput().isKeyDown(0x41)) {
            System.out.println("A");
        }
    }

    @Override
    public void render() {
        //getRenderer().drawImage(image, 500, 500);
        //getRenderer().drawImage(semi, getInput().getMouseX()-64, getInput().getMouseY()-64);
        getRenderer().drawText("Hi!\nIch bin Simon, der gutë!\nWer bist dù?",100,100,0xff00ffff, algerian32);
    }

    @Override
    public void stop() {

    }
}
