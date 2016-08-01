package me.st1nger13.game3d.mechanics.managers;

import me.st1nger13.game3d.states.Screen;

import java.util.Stack;

/**
 * Created by st1nger13 on 27.07.16.
 * ScreenManager
 */
public class ScreenManager {
    private static Stack<Screen> screens = new Stack<>() ;

    public static void setScreen(Screen screen) {
        if(screen == null)
            throw new IllegalStateException("Screen can't be NULL!") ;
        if(screens.size() > 0)
            screens.peek().dispose() ;
        screen.init() ;
        screens.push(screen) ;
    }

    public static void popScreen() {
        if(screens.size() > 1) {
            screens.peek().dispose() ;
            screens.pop() ;
        }
    }

    public static Screen getCurrentScreen() {
        return screens.peek() ;
    }
}
