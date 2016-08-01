package me.st1nger13.game3d.mechanics;

import me.st1nger13.game3d.GameHelper;
import me.st1nger13.game3d.mechanics.render.Object;
import org.lwjgl.opengl.GL11;

/**
 * Created by st1nger13 on 29.07.16.
 * The Mesh loaded into GPU using VAO list.
 */
public class Mesh implements Object {
    public int vaoID ;
    public int numberOfVertices ;
    public int cullFace ;

    public Mesh(int vaoID, int numberOfVertices)
    {
        this.vaoID = vaoID ;
        this.numberOfVertices = numberOfVertices ;
        this.cullFace = GL11.GL_BACK ;
    }

    @Override
    public void delete() {
        GameHelper.loader.deleteVAO(vaoID) ;
    }
}
