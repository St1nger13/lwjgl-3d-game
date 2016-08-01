package me.st1nger13.game3d.mechanics.render;

import jdk.nashorn.internal.runtime.WithObject;
import me.st1nger13.game3d.mechanics.Mesh;
import me.st1nger13.game3d.mechanics.managers.DisplayManager;
import me.st1nger13.game3d.utils.MathHelper;
import me.st1nger13.game3d.world.Entity;
import me.st1nger13.game3d.mechanics.render.shader.ShaderEntity;
import me.st1nger13.game3d.world.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;
import java.util.Set;

/**
 * Created by st1nger13 on 27.07.16.
 * Gui Renderer
 */
public class EntityRenderer extends Renderer {
    private ShaderEntity shaderEntity ;
    private Matrix4f projectionMatrix ;
    private World world ;

    public EntityRenderer() {
        shaderEntity = new ShaderEntity() ;
        projectionMatrix = MathHelper.createProjectionMatrix( Display.getWidth(), Display.getHeight(),
                DisplayManager.FAR_PLANE, DisplayManager.NEAR_PLANE, DisplayManager.FOV ) ;
    }

    public void setWorld(World world) {
        this.world = world ;
    }

    @Override
    public void prepare() {
    }

    @Override
    public void draw() {
        if(world != null) {
            world.getCamera().move() ;

            float speed = .5f ;

            if(Keyboard.isKeyDown(Keyboard.KEY_W))
                world.getCamera().getPosition().z += speed ;
            if(Keyboard.isKeyDown(Keyboard.KEY_S))
                world.getCamera().getPosition().z -= speed ;
            if(Keyboard.isKeyDown(Keyboard.KEY_D))
                world.getCamera().getPosition().x += speed ;
            if(Keyboard.isKeyDown(Keyboard.KEY_A))
                world.getCamera().getPosition().x -= speed ;

            if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
                world.getCamera().getPosition().y += 1 ;
            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                world.getCamera().getPosition().y -= 1 ;

            if(Keyboard.isKeyDown(Keyboard.KEY_E))
                world.getCamera().setRoll(world.getCamera().getRoll() + 0.5f) ;
            if(Keyboard.isKeyDown(Keyboard.KEY_Q))
                world.getCamera().setRoll(world.getCamera().getRoll() - 0.5f) ;

            //-------------------------

            if(Keyboard.isKeyDown(Keyboard.KEY_J)) {
                if(Keyboard.isKeyDown(Keyboard.KEY_UP))
                    world.e.increaseRotation(0.5f, 0, 0) ;
                if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
                    world.e.increaseRotation(-0.5f, 0, 0) ;
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_K)) {
                if(Keyboard.isKeyDown(Keyboard.KEY_UP))
                    world.e.increaseRotation(0, 0.5f, 0) ;
                if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
                    world.e.increaseRotation(0, -0.5f, 0) ;
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_L)) {
                if(Keyboard.isKeyDown(Keyboard.KEY_UP))
                    world.e.increaseRotation(0, 0, 0.5f) ;
                if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
                    world.e.increaseRotation(0, 0, -0.5f) ;
            }
            //------------

            if(Keyboard.isKeyDown(Keyboard.KEY_U)) {
                if(Keyboard.isKeyDown(Keyboard.KEY_UP))
                    world.e.increasePosition(1, 0, 0);
                if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
                    world.e.increasePosition(-1, 0, 0);
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_I)) {
                if(Keyboard.isKeyDown(Keyboard.KEY_UP))
                    world.e.increasePosition(0, 1, 0);
                if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
                    world.e.increasePosition(0, -1, 0);
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_O)) {
                if(Keyboard.isKeyDown(Keyboard.KEY_UP))
                    world.e.increasePosition(0, 0, 1);
                if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
                    world.e.increasePosition(0, 0, -1);
            }

            //------------

            if(Keyboard.isKeyDown(Keyboard.KEY_N)) {
                if(Keyboard.isKeyDown(Keyboard.KEY_UP))
                    world.e.setScale(world.e.getScale() + 0.2f) ;
                if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
                    world.e.setScale(world.e.getScale() - 0.2f) ;
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_P)) {
                Vector3f p = world.e.getPosition() ;
                Vector3f r = world.e.getRotation() ;
                System.out.println("Pos " + p.x + " " + p.y + " " + p.z) ;
                System.out.println("Rot " + r.x + " " + r.y + " " + r.z) ;
                System.out.println("Scale " + world.e.getScale()) ;
                System.out.println("Camera " + world.getCamera().getPosition().x + " " + world.getCamera().getPosition().y + " " + world.getCamera().getPosition().z) ;
            }

            //-------------------------


            List<Entity> entityList = world.getEntities() ;
            if(entityList != null) {
                shaderEntity.start() ;
                {
                    Mesh mesh ;

                    for (Entity entity : entityList) {
                        mesh = entity.getMesh() ;
                        GL11.glEnable(GL11.GL_CULL_FACE) ;
                        GL11.glCullFace(mesh.cullFace) ;

                        GL11.glEnable(GL11.GL_DEPTH_TEST);

                        GL30.glBindVertexArray(mesh.vaoID ) ;
                        GL20.glEnableVertexAttribArray(0) ;
                        GL20.glEnableVertexAttribArray(1) ;
                        GL20.glEnableVertexAttribArray(2) ;

                        if(entity.isHasTransparency) {
                            GL11.glEnable(GL11.GL_BLEND) ;
                            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA) ;

                            if(entity.isHasTransparencyChannel)
                                shaderEntity.enableTransparencyChannel() ;
                        } else
                            shaderEntity.disableTransparencyChannel() ;

                        entity.getTexture().bind() ;
                        shaderEntity.loadProjectionMatrix(projectionMatrix) ;
                        Matrix4f matrix = MathHelper.createTransformationMatrix(entity.getPosition(),
                                entity.getRotation(), entity.getScale()) ;
                        shaderEntity.loadTransformation(matrix) ;
                        shaderEntity.loadViewMatrix(world.getCamera()) ;
                        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.numberOfVertices, GL11.GL_UNSIGNED_INT, 0) ;

                        if(entity.isHasTransparency) {
                            GL11.glDisable(GL11.GL_BLEND);
                            shaderEntity.disableTransparencyChannel();
                        }

                        GL20.glDisableVertexAttribArray(2) ;
                        GL20.glDisableVertexAttribArray(1) ;
                        GL20.glDisableVertexAttribArray(0) ;
                        GL30.glBindVertexArray(0) ;
                    }

                    GL11.glDisable(GL11.GL_CULL_FACE) ;
                }
                shaderEntity.stop() ;
            }
        }
    }
}
