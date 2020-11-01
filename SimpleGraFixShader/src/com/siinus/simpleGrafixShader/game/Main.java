package com.siinus.simpleGrafixShader.game;

import com.siinus.simpleGrafix.Program;
import com.siinus.simpleGrafix.gfx.Font;
import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafix.gfx.ImageTile;
import com.siinus.simpleGrafix.gfx.PreRendered;
import com.siinus.simpleGrafix.sfx.SoundClip;
import com.siinus.simpleGrafixShader.*;

public class Main extends Program {
    int t = 0xff;

    ShaderImageTile image;
    Image image2;
    SoundClip soundClip;
    //Image semi;
    Font algerian32 = new Font("/algerian32.png", 32, 32);
    LightLite light = new LightLite(1000, 0xff);


    public static void main(String[] args) {
        Main program = new Main();
        program.init();
    }

    public Main() {
        image = new ShaderImageTile("https://aldi.com/images/aldi_sued_logo.png", 128, 128);
        image2 = new ShaderImageTile("https://aldi.com/images/aldi_sued_logo.png", 128, 128);
        image.setLightBlock(1);

        setIconImage(new Image("https://cdn4.iconfinder.com/data/icons/iconsimple-logotypes/512/github-512.png"));
    }

    @Override
    public void start() {
        setCapFps(false);
        getWindow().setScaleOnResize(true);
    }

    @Override
    public void update() {
        getRenderer().setBgColor(0xffffffff);
        if (getInput().isKeyDown(0x41)) {
            System.out.println("A");
        }
        image.setTransparency(t);
        t--;
        if (t<0) {
            t=0xff;
        }
    }

    @Override
    public void render() {
        getRenderer().drawImage(image2, 525, 525);
        getRenderer().drawImage(image, 500, 500);
        //((ShaderRenderer) getRenderer()).makeLightImage(image, 500, 500);
        ((ShaderRendererLite) getRenderer()).drawLight(light, getInput().getMouseX(), getInput().getMouseY());
        ((ShaderRendererLite) getRenderer()).makeLightImage(image2, 525, 525);
        getRenderer().drawText(gameLoop.getFps()+"", 100, 100, 0xffff0000, algerian32);
    }

    @Override
    public void stop() {

    }

    @Override
    protected void init() {
        super.init();
        renderer = new ShaderRendererLite(window);
        ((ShaderRendererLite) renderer).setAmbientLight((byte) 0x3f);
        gameLoop.setRenderer(renderer);
    }
}
