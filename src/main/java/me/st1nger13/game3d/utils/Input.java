package me.st1nger13.game3d.utils;

import me.st1nger13.game3d.mechanics.Buses;
import org.lwjgl.input.Keyboard;

/**
 * Created by st1nger13 on 27.07.16.
 * Input
 */
public class Input {
 /*   public enum Key {
        ENTER(Keyboard.KEY_E) ,
        SPACE(Keyboard.KEY_SPACE) ;

        private boolean isPressed ;
        private int keyCode ;

        Key(int keyCode) {
            this.keyCode = keyCode ;
        }

        public boolean isKeyPressed() {
            return isPressed ;
        }

        public void setKeyState(boolean state) {
            isPressed = state ;
        }

        public static Key getKey(int keyCode) {
            for(Key key : values())
                if(key.keyCode == keyCode)
                    return key ;
            return null ;
        }
    }


    public static class Keys {
        public static Key space = Key.SPACE ;
    }
*/
    public static void handleInputs() {
        while(Keyboard.next()) {
            Buses.KEYBOARD.post(Keyboard.getEventKey()) ;
            /*Key key ;
            if((key = Key.getKey(Keyboard.getEventKey())) != null) {
                if(Keyboard.getEventKeyState())
                    key.setKeyState(true) ;
                else
                    key.setKeyState(false) ;
            }*/
        }
    }
}
