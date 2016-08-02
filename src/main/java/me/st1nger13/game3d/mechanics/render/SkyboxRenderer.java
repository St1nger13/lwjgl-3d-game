package me.st1nger13.game3d.mechanics.render;

import me.st1nger13.game3d.GameHelper;
import me.st1nger13.game3d.mechanics.Mesh;
import me.st1nger13.game3d.mechanics.managers.DisplayManager;
import me.st1nger13.game3d.mechanics.render.shader.ShaderSkybox;
import me.st1nger13.game3d.utils.MathHelper;
import me.st1nger13.game3d.world.World;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by st1nger13 on 27.07.16.
 * Gui Renderer
 */
public class SkyboxRenderer extends Renderer {
    private ShaderSkybox shaderSkybox ;
    private World world ;
    private static final float SIZE = 9000f ;
    private static float blendFactor = 1f ;

    private static final float[] VERTICES = {
            -SIZE,  SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            -SIZE,  SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE,  SIZE
    };

    private static String[] TEXTURE_FILES = {
            "right",
            "left",
            "top",
            "bottom",
            "back",
            "front"
    } ;

    private Mesh cube ;
    private int textureDayID ;

    private static int angle = 0 ;

    public SkyboxRenderer() {
        shaderSkybox = new ShaderSkybox() ;

        cube = GameHelper.loader.loadToVAO(VERTICES, 3) ;
        textureDayID = GameHelper.loader.loadCubeMapTexture(TEXTURE_FILES) ;

        Matrix4f projectionMatrix = MathHelper.createProjectionMatrix( Display.getWidth(), Display.getHeight(),
                DisplayManager.FAR_PLANE, DisplayManager.NEAR_PLANE, DisplayManager.FOV ) ;

        shaderSkybox.start() ;
        shaderSkybox.loadProjectionMatrix(projectionMatrix) ;
        shaderSkybox.stop() ;
    }

    public void setWorld(World world) {
        this.world = world ;
    }

    @Override
    public void prepare() {
    }

    @Override
    public void draw() {
        shaderSkybox.start() ;
        shaderSkybox.loadViewMatrix(world.getCamera()) ;
        Matrix4f matrix = MathHelper.createTransformationMatrix(new Vector3f(0,0,0), new Vector3f(angle, 0, 0), 1) ;
        shaderSkybox.loadTransformationMatrix(matrix) ;
        shaderSkybox.loadSkyColor(0.3f, 0.6f, 1f) ;
        GL30.glBindVertexArray(cube.vaoID) ;
        GL20.glEnableVertexAttribArray(0) ;

        GL13.glActiveTexture(GL13.GL_TEXTURE0) ;
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureDayID) ;
        shaderSkybox.loadBlendFactor(blendFactor) ;

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.numberOfVertices) ;
        GL20.glDisableVertexAttribArray(0) ;
        GL30.glBindVertexArray(0) ;
        shaderSkybox.stop() ;

        if( angle > 360 ) angle = 0 ;
    }
}
