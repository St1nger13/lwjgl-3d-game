package me.st1nger13.game3d;

import me.st1nger13.game3d.mechanics.loaders.Loader;

import java.io.File;

/**
 * Created by st1nger13 on 29.07.16.
 * V
 */
public class GameHelper {
    @SuppressWarnings("null")
    public static final FileHelper files = new FileHelper(Game.classLoader.getResource("").getPath()) ;
    public static final Loader loader = new Loader() ;

    public static class FileHelper {
        private String rootPath ;

        public FileHelper(String rootPath) {
            this.rootPath = rootPath ;
        }

        public File getFile(String fileName) {
            return new File(rootPath + "/" + fileName) ;
        }
    }
}
