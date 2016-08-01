package me.st1nger13.game3d.mechanics.render.gui;

import me.st1nger13.game3d.GameHelper;
import me.st1nger13.game3d.mechanics.Mesh;
import me.st1nger13.game3d.mechanics.render.shader.ShaderGui;
import me.st1nger13.game3d.utils.MathHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

/**
 * Created by st1nger13 on 29.07.16.
 * Sprite
 */
public class Sprite extends Gui {
    private Mesh mesh ;
    private Texture texture ;


    public Sprite(int id, String texturePath) {
        super(id) ;
        texture = GameHelper.loader.loadTexture(texturePath) ;

        float[] vertices = {
                -1, 1, 0,
                -1, -1, 0,
                1, -1, 0,
                1, -1, 0,
                1, 1, 0f,
                -1, 1, 0f
        } ;

        float[] textureCoords = {
                0, 1,
                0, 0,
                1, 0,
                1, 0,
                1, 1,
                0, 1,
        } ;

        int[] indices = {0, 1, 3, 3, 1, 2} ;

        mesh = GameHelper.loader.loadToVAO(vertices, textureCoords, indices) ;
    }

    @Override
    public void prepare(float delta) {

    }

    @Override
    public void draw(ShaderGui shader) {
        GL30.glBindVertexArray(mesh.vaoID) ;
        GL20.glEnableVertexAttribArray(0) ;
        GL11.glEnable(GL11.GL_BLEND) ;
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA) ;
        GL11.glDisable(GL11.GL_DEPTH_TEST) ;

        GL13.glActiveTexture(GL13.GL_TEXTURE0) ;
        texture.bind() ;
        Vector2f position = new Vector2f(getNaturalX(), getNaturalY()) ;
        Vector2f scale = new Vector2f(getNaturalWidth(), getNaturalHeight()) ;
        Matrix4f matrix = MathHelper.createTransformationMatrix(position, rotation, scale) ;
        shader.loadTransformation(matrix) ;
        shader.disableColoring() ;

        GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, mesh.numberOfVertices);

        GL11.glEnable(GL11.GL_DEPTH_TEST) ;
        GL11.glDisable(GL11.GL_BLEND) ;
        GL20.glDisableVertexAttribArray(0) ;
        GL30.glBindVertexArray(0) ;

        super.draw(shader) ;
    }

    @Override
    public void delete() {
        mesh.delete() ;
        texture.delete() ;
    }
}
