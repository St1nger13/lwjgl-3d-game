package me.st1nger13.game3d;

import org.lwjgl.LWJGLUtil;

import java.io.File;

/**
 * Created by St1nger13 on 27.07.16.
 * Launches with a proper platform library
 */
public class Launcher {
    public static void main(String[] args) {

        File native_dir_path ;
        String native_dir = "libs/native/" ;

        switch(LWJGLUtil.getPlatform()) {
            case LWJGLUtil.PLATFORM_WINDOWS :
                native_dir_path = new File(native_dir + "windows/") ;
            break ;

            case LWJGLUtil.PLATFORM_LINUX : default :
                native_dir_path = new File(native_dir + "linux/") ;
            break ;

            case LWJGLUtil.PLATFORM_MACOSX :
                native_dir_path = new File(native_dir + "macosx/") ;
            break ;
        }

        System.setProperty("org.lwjgl.librarypath", native_dir_path.getAbsolutePath()) ;

        Game.getGame()
                .create()
                .startLoop() ;
    }
}
