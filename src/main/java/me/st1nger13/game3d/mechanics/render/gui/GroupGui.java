package me.st1nger13.game3d.mechanics.render.gui;

import me.st1nger13.game3d.mechanics.render.Object;
import me.st1nger13.game3d.mechanics.render.shader.ShaderGui;
import me.st1nger13.game3d.utils.MathHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by st1nger13 on 29.07.16.
 * Group of Gui elements
 */
public class GroupGui extends Gui {
    private Map<Integer, Gui> guiMap ;

    public GroupGui(int id) {
        super(id) ;
        guiMap = new HashMap<>() ;
    }

    public void add(Gui gui) {
        guiMap.put(gui.getID(), gui) ;
        gui.setParent(this) ;
    }

    public void remove(Gui gui) {
        guiMap.remove(gui.getID()) ;
    }

    @Override
    public void prepare(float delta) {
        guiMap.values().forEach(gui -> gui.prepare(delta)) ;
    }

    @Override
    public void draw(ShaderGui shader) {
        guiMap.values().forEach(gui -> gui.draw(shader)) ;
        super.draw(shader) ;
    }

    @Override
    public void delete() {
        guiMap.values().forEach(Object::delete) ;
    }
}
