package com.siinus.simpleGrafixShader;

import com.siinus.simpleGrafix.Program;

public abstract class ShaderProgram extends Program {

    protected final void initShader() {
        init();
        gameLoop.setRenderer(new ShaderRenderer(gameLoop.getWindow()));
    }

    protected final ShaderRenderer getShaderRenderer() {
        return (ShaderRenderer) getRenderer();
    }
}
