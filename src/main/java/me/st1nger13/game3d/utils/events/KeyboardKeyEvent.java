package me.st1nger13.game3d.utils.events;

/**
 * Created by st1nger13 on 27.07.16.
 *
 */
public class KeyboardKeyEvent {
    public int keyCode ;
    public boolean isJustPressed ;

    public KeyboardKeyEvent(int keyCode, boolean isJustPressed) {
        this.keyCode = keyCode ;
        this.isJustPressed = isJustPressed ;
    }
}
