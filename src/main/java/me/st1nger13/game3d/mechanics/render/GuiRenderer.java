package me.st1nger13.game3d.mechanics.render;

import me.st1nger13.game3d.mechanics.render.gui.Gui;
import me.st1nger13.game3d.mechanics.render.shader.ShaderGui;

/**
 * Created by st1nger13 on 27.07.16.
 * Gui Renderer
 */
public class GuiRenderer extends Renderer {
    private ShaderGui shaderGui ;
    private Gui root ;

    public GuiRenderer() {
        shaderGui = new ShaderGui() ;
    }

    public void setRootElement(Gui gui) {
        root = gui ;
    }

    @Override
    public void prepare() {
    }

    @Override
    public void draw() {
        if(root != null) {
            shaderGui.start() ;
            root.draw(shaderGui) ;
            shaderGui.stop() ;
        }
    }
}
