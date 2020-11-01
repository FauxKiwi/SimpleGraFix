package com.siinus.simpleGrafix.game;

import com.siinus.simpleGrafix.Program;
import com.siinus.simpleGrafix.gfx.*;
import com.siinus.simpleGrafix.gfx.geom.Line;
import com.siinus.simpleGrafix.gfx.geom.Rectangle;
import com.siinus.simpleGrafix.input.InputUtils;
import com.siinus.simpleGrafix.sfx.SoundClip;

import java.io.IOException;

public class Main extends Program {

    int t = 0xff;

    Image image;
    LowRamImage image2;
    Rectangle rectangle;
    ImageGif gif;
    SoundClip soundClip;
    Image semi;
    Font algerian32 = new Font("/exclude/algerian32.png", 32, 32);

    Line line = new Line(0,1000,500,500, 0xff000000);


    private int timer = 0;

    public static void main(String[] args) throws IOException {
        Main program = new Main();
        program.init();
    }

    public Main() throws IOException {
        //image = new ImageTile("/test.png", 128, 128);
        //soundClip = new SoundClip("/service-bell_daniel_simion.wav");
        //semi = new Image("/semi.png");
        gif = new ImageGif("/exclude/tenor.gif");

        image = new Image("https://aldi.com/images/aldi_sued_logo.png");
        image2 = new LowRamImage("https://aldi.com/images/aldi_sued_logo.png");
        rectangle = new Rectangle(50, 50, 500, 500, 0xff0000ff);

        //setIconImage(new Image("https://cdn4.iconfinder.com/data/icons/iconsimple-logotypes/512/github-512.png"));
    }

    @Override
    public void start() {
        //soundClip.loop();
        //getWindow().setResizable(false);
        getWindow().setScaleOnResize(true);
        setCapFps(false);
        getRenderer().setBgColor(0xffffffff);
    }

    @Override
    public void update() {
        line.setX0(getInput().getMouseX());
        line.setY0(getInput().getMouseY());
        if (timer==0) {
            timer=10;
        } else {
            image.setTransparency(t = t>0 ? t-1 : 0xff);
            timer--;
        }
        gif.nextImage();
    }

    @Override
    public void render() {
        //getRenderer().drawImage(image, 500, 500);
        //getRenderer().drawImage(image, 500, 500);
        //getRenderer().drawImage(semi, getInput().getMouseX()-64, getInput().getMouseY()-64);
        getRenderer().drawImage(gif, getInput().getMouseX()-200, getInput().getMouseY()-200);
        getRenderer().drawText("FPS: "+gameLoop.getFps(), 800, 100, 0xffff0000, algerian32);
        //getRenderer().drawText("Hi!\nIch bin Simon, der gutë!\nWer bist dù?",100,100,0xff00ffff, algerian32);
        getRenderer().drawImage(image2, 525, 525);
        getRenderer().drawImage(image, 500, 500);
        rectangle.renderFill(getRenderer());
    }

    @Override
    public void stop() {

    }
}
