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
        float scaleArg = (float) Display.getWidth() / (float) Display.getHeight() ;
        sprite.setHeight(scaleArg) ;
        sprite.setY(-(scaleArg - 1)) ;
        group.add(sprite) ;

        renderEngine.GUI.setRootElement(group) ;
    }

    @Override
    public void update(float delta) {
        renderEngine.prepare() ;
    }

    @Override
    public void draw() {
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
