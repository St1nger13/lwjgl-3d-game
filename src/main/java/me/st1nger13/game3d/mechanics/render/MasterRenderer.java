package me.st1nger13.game3d.mechanics.render;

/**
 * Created by st1nger13 on 27.07.16.
 * Master Renderer
 */
public class MasterRenderer extends Renderer {
    public final GuiRenderer GUI = new GuiRenderer() ;
    public final EntityRenderer ENTITIES = new EntityRenderer() ;


    public MasterRenderer() {
    }

    @Override
    public void prepare() {
        GUI.prepare() ;
        ENTITIES.prepare() ;
    }

    @Override
    public void draw() {
        ENTITIES.draw() ;
        GUI.draw() ;
    }
}
