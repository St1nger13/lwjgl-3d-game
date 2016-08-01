package me.st1nger13.game3d.mechanics.handlers;

import com.google.common.eventbus.Subscribe;
import me.st1nger13.game3d.mechanics.Buses;
import me.st1nger13.game3d.Game;
import me.st1nger13.game3d.utils.events.KeyboardKeyEvent;
import org.lwjgl.input.Keyboard;

/**
 * Created by st1nger13 on 27.07.16.
 *
 */
public class GlobalKeysHandler {

    public GlobalKeysHandler() {
        Buses.KEYBOARD.register(this) ;
    }

    @Subscribe
    public void handleKeyaboardEvent(KeyboardKeyEvent event) {
        if(event.keyCode == Keyboard.KEY_F3 && event.isJustPressed)
            Game.getGame().getSettings().debugOn() ;
        if(event.keyCode == Keyboard.KEY_F4 && event.isJustPressed)
            Game.getGame().getSettings().debugOff() ;
    }
}
