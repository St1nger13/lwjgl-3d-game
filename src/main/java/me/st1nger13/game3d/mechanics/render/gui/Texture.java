package me.st1nger13.game3d.mechanics.render.gui;

import me.st1nger13.game3d.GameHelper;
import me.st1nger13.game3d.mechanics.render.Object;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 * Created by st1nger13 on 29.07.16.
 * Texture
 */
public class Texture implements Object {
    private static int bindedTextureID ;
    private int textureID ;

    public Texture(int textureID) {
        this.textureID = textureID ;
    }

    public int getID() {
        return textureID ;
    }

    public void bind() {
        if(bindedTextureID != textureID) {
            bindedTextureID = textureID ;
            GL13.glActiveTexture( GL13.GL_TEXTURE0 ) ;
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID) ;
        }
    }

    public void unbind() {
        bindedTextureID = 0 ;
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0) ;
    }

    @Override
    public void delete() {
        GameHelper.loader.deleteTexture(textureID) ;
    }
}
