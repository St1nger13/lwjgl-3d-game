package me.st1nger13.game3d.mechanics.managers;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;

/**
 * Created by st1nger13 on 27.07.16.
 * Display
 */

public class DisplayManager {
    private static final int DISPLAY_FPS = 60 ;
    private static final int DISPLAY_WIDTH = 1366 ;
    private static final int DISPLAY_HEIGHT = 768 ;

    public static final float FOV = 70 ;         // Field Of View in View Frustum
    public static final float NEAR_PLANE = 0.1f ;
    public static final float FAR_PLANE = 16000 ;

    public static void createDisplay() {
        ContextAttribs attribs = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true) ;
        try {
            boolean isProperModeFound = false ;
            for(DisplayMode mode : Display.getAvailableDisplayModes()) {
                if(mode.getHeight() == Display.getHeight()) {
                    isProperModeFound = true;
                    Display.setDisplayMode(mode) ;
                    break ;
                }
            }
            if(!isProperModeFound)
                Display.setDisplayMode( new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT)) ;

            Display.create(new PixelFormat().withSamples(8).withDepthBits(24), attribs) ;
            setTitle("Cool game!") ;
        } catch( LWJGLException e ) {
            e.printStackTrace() ;
            System.exit(0) ;
        }

        GL11.glViewport(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);
    }

    public static void updateDisplay() {
        Display.sync(DISPLAY_FPS) ;
        Display.update() ;
        GL11.glClearColor(0.2f, 0.6f, 1f, 1f) ;
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT) ;
    }

    public static void destroyDisplay() {
        Display.destroy() ;
    }

    public static void setTitle( String title ) {
        Display.setTitle(title) ;
    }
}