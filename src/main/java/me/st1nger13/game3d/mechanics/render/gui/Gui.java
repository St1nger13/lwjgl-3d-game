package me.st1nger13.game3d.mechanics.render.gui;

import me.st1nger13.game3d.GameHelper;
import me.st1nger13.game3d.mechanics.Mesh;
import me.st1nger13.game3d.mechanics.render.Object;
import me.st1nger13.game3d.mechanics.render.shader.ShaderGui;
import me.st1nger13.game3d.utils.MathHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

/**
 * Created by st1nger13 on 29.07.16.
 * Parent Gui Element
 */
public abstract class Gui implements Object {
    private int id ;
    private Mesh debugMesh ;
    protected boolean isDebug ;
    protected Gui parent ;
    protected float x ;
    protected float y ;
    protected float scaleX ;
    protected float scaleY ;
    protected float rotation ;

    public Gui(int id) {
        this.id = id ;
        scaleX = 1 ;
        scaleY = 1 ;
        rotation = 0 ;

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

        debugMesh = GameHelper.loader.loadToVAO(vertices, textureCoords, indices) ;
    }

    public void draw(ShaderGui shader) {
        if(isDebug) {
            GL30.glBindVertexArray(debugMesh.vaoID) ;
            GL20.glEnableVertexAttribArray(0) ;
            GL11.glDisable(GL11.GL_DEPTH_TEST) ;
            Vector2f position = new Vector2f(getNaturalX(), getNaturalY()) ;
            Vector2f scale = new Vector2f(getNaturalWidth(), getNaturalHeight()) ;
            Matrix4f matrix = MathHelper.createTransformationMatrix(position, rotation, scale) ;
            shader.loadTransformation(matrix) ;

            shader.loadColor(new Vector4f(1f, 0, 0, 1)) ;
            GL11.glDrawArrays(GL11.GL_LINE_STRIP, 0, debugMesh.numberOfVertices) ;

            GL11.glEnable(GL11.GL_DEPTH_TEST) ;
            GL20.glDisableVertexAttribArray(0) ;
            GL30.glBindVertexArray(0) ;
        }
    }

    public void setParent(Gui gui) {
        parent = gui ;
    }

    public void setWidth(float width) {
        scaleX = width ;
        x = - (1 - scaleX) ;
    }

    public void setHeight(float height) {
        scaleY = height ;
        y = 1 - scaleY ;
    }

    /**
     * In range 0f - 1f
     * @param xArg
     */
    public void setX(float xArg) {
        xArg *= 2 ;
        x += xArg ;
    }

    /**
     * In range 0f - 1f
     * @param yArg
     */
    public void setY(float yArg) {
        yArg *= 2 ;
        y -= yArg ;
    }

    public void debugOn() {
        isDebug = true ;
    }

    public void debugOff() {
        isDebug = false ;
    }

    public int getID() {
        return id ;
    }

    public float getX() {
        return x ;
    }

    public float getY() {
        return y ;
    }

    public float getWidth() {
        return scaleX ;
    }

    public float getHeight() {
        return scaleY ;
    }

    public float getRotation() {
        return rotation ;
    }

    public Gui getParent() {
        return parent ;
    }

    public float getNaturalX() {
        return (parent != null) ? x + parent.getNaturalX() : x ;
    }

    public float getNaturalY() {
        return (parent != null) ? y + parent.getNaturalY() : y ;
    }

    public float getNaturalWidth() {
        return (parent != null) ? scaleX * parent.getNaturalWidth() : scaleX ;
    }

    public float getNaturalHeight() {
        return (parent != null) ? scaleY * parent.getNaturalHeight() : scaleY ;
    }

    abstract void prepare(float delta) ;
}
