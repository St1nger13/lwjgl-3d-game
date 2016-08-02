package me.st1nger13.game3d.states;

import me.st1nger13.game3d.mechanics.Buses;
import me.st1nger13.game3d.mechanics.render.MasterRenderer;
import me.st1nger13.game3d.mechanics.render.SkyboxRenderer;
import me.st1nger13.game3d.world.World;
import org.lwjgl.opengl.GL11;

/**
 * Created by st1nger13 on 27.07.16.
 * In Game Screen
 */
public class InGameScreen implements Screen {
    private World world ;
    private MasterRenderer renderEngine ;
    public final SkyboxRenderer SKYBOX = new SkyboxRenderer() ;

    @Override
    public void init() {
        Buses.KEYBOARD.register(this) ;
        renderEngine = new MasterRenderer() ;
        world = new World() ;
        world.load() ;
        renderEngine.ENTITIES.setWorld(world) ;
        SKYBOX.setWorld(world);
    }

    @Override
    public void update(float delta) {}

    @Override
    public void draw() {
        GL11.glClearColor(1f, 0f, 0f, 1f) ;
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT) ;

        SKYBOX.draw() ;
        renderEngine.draw() ;
    }

    @Override
    public void dispose() {

    }
}
