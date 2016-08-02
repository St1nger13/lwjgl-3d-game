package me.st1nger13.game3d.states;

import com.google.common.eventbus.Subscribe;
import me.st1nger13.game3d.mechanics.Buses;
import me.st1nger13.game3d.mechanics.managers.ScreenManager;
import me.st1nger13.game3d.mechanics.render.MasterRenderer;
import me.st1nger13.game3d.mechanics.render.gui.GroupGui;
import me.st1nger13.game3d.mechanics.render.gui.Sprite;
import me.st1nger13.game3d.utils.events.KeyboardKeyEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 * Created by st1nger13 on 27.07.16.
 * Main Menu Screen
 */
public class MainMenuScreen implements Screen {
    private MasterRenderer renderEngine ;

    @Override
    public void init() {
        Buses.KEYBOARD.register(this) ;
        renderEngine = new MasterRenderer() ;

        GroupGui group = new GroupGui(0) ;
        Sprite sprite = new Sprite(1, "mainMenu/bg") ;
        sprite.setWidth((float) Display.getHeight() / (float) Display.getWidth()) ;
        sprite.setX(0.5f - sprite.getWidth() / 2) ;
        group.add(sprite) ;

        renderEngine.GUI.setRootElement(group) ;
    }

    @Override
    public void update(float delta) {
        renderEngine.prepare() ;
    }

    @Override
    public void draw() {
        GL11.glClearColor(0f, 0f, 0f, 1f) ;
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT) ;

        renderEngine.draw() ;
    }

    @Override
    public void dispose() {
        Buses.KEYBOARD.unregister(this) ;
    }

    @Subscribe
    public void handleKeyboardKeyEvent(KeyboardKeyEvent event) {
        if(event.keyCode == Keyboard.KEY_SPACE && event.isJustPressed)
            ScreenManager.setScreen(new InGameScreen()) ;
    }
}
