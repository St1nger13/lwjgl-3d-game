package me.st1nger13.game3d;

/**
 * Created by st1nger13 on 27.07.16.
 *
 */
public final class GameSettings {
    private boolean isDebugMode ;

    public void debugOn() {
        isDebugMode = true ;
        Game.logger.finest("Turned Debug Mode On") ;
    }

    public void debugOff() {
        isDebugMode = false ;
        Game.logger.finest("Turned Debug Mode Off") ;
    }

    public boolean isDebug() {
        return isDebugMode ;
    }
}
