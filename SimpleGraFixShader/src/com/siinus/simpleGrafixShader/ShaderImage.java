package com.siinus.simpleGrafixShader;

import com.siinus.simpleGrafix.gfx.Image;

public class ShaderImage extends Image implements IShaderImage {
    private int lightBlock = 0;

    public ShaderImage(String path) {
        super(path);
    }

    public void setLightBlock(int lightBlock) {
        this.lightBlock = lightBlock;
    }

    public int getLightBlock() {
        return lightBlock;
    }
}
