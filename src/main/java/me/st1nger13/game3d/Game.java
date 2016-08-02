package me.st1nger13.game3d;

import me.st1nger13.game3d.mechanics.Buses;
import me.st1nger13.game3d.mechanics.managers.DisplayManager;
import me.st1nger13.game3d.mechanics.managers.ScreenManager;
import me.st1nger13.game3d.states.MainMenuScreen;
import me.st1nger13.game3d.utils.events.KeyboardKeyEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.logging.Logger;

/**
 * Created by st1nger13 on 27.07.16.
 * Main Game Class
 */
public class Game {
    public static Logger logger = Logger.getLogger(Game.class.getName()) ;
    public static ClassLoader classLoader = Game.class.getClassLoader() ;
    private static Game instance ;
    private GameSettings settings ;

    static {
        instance = new Game() ;
    }

    public static Game getGame() {
        return instance ;
    }

    private Game() {}

    public Game create() {
        settings = new GameSettings() ;
        DisplayManager.createDisplay() ;
        ScreenManager.setScreen(new MainMenuScreen()) ;
        return this ;
    }

    public void startLoop() {
        while(!Display.isCloseRequested()) {
            DisplayManager.updateDisplay() ;

            ScreenManager.getCurrentScreen().draw() ;

            // Handle inputs
            // TODO: Temporary
            while(Keyboard.next()) {
                if(Keyboard.getEventKeyState())
                    Buses.KEYBOARD.post(new KeyboardKeyEvent(Keyboard.getEventKey(), true)) ;
                else
                    Buses.KEYBOARD.post(new KeyboardKeyEvent(Keyboard.getEventKey(), false)) ;
            }
        }

        GameHelper.loader.cleanUp() ;DisplayManager.destroyDisplay() ;
    }
}
