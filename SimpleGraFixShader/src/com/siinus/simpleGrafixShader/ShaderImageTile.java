package com.siinus.simpleGrafixShader;

import com.siinus.simpleGrafix.gfx.ImageTile;

public class ShaderImageTile extends ImageTile implements IShaderImage{
    private int lightBlock = 0;

    public ShaderImageTile(String path, int tileWidth, int tileHeight) {
        super(path, tileWidth, tileHeight);
    }

    @Override
    public void setLightBlock(int lightBlock) {
        this.lightBlock = lightBlock;
    }

    @Override
    public int getLightBlock() {
        return lightBlock;
    }
}
