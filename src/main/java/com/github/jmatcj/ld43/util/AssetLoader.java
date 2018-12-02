package com.github.jmatcj.ld43.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class AssetLoader {
    private static final String ASSETS_DIR = "/assets/ld43/";
    private static final String IMAGES_DIR = ASSETS_DIR + "images/";
    private static final String MUSIC_DIR = ASSETS_DIR + "music/";
    //private static final String FONT_LOC = ASSETS_DIR + "font/code_bold.otf";
    // name:Image pairs
    private static Map<String, Image> images;
    private static Map<String, Media> music;
    private static String fontLoc;

    public static void initialize(boolean noMusic) throws IOException, URISyntaxException {
        // FONT
        //System.out.println("Loading Code Bold font...");
        //fontLoc = AssetLoader.class.getResource(FONT_LOC).toExternalForm();
        // IMAGES
        images = new HashMap<>();
        URI imagesURI = AssetLoader.class.getResource(IMAGES_DIR).toURI();
        Path path;
        char separator; // The separator is different inside JAR files on Windows
        FileSystem fs = null; // Need a custom file system for JAR files...because reasons...
        if (imagesURI.getScheme().equals("jar")) {
            fs = FileSystems.newFileSystem(imagesURI, Collections.emptyMap());
            path = fs.getPath(IMAGES_DIR);
            separator = '/';
        } else {
            path = Paths.get(imagesURI);
            separator = File.separatorChar;
        }
        Files.walk(path, 1).forEach(p -> {
            if (p.toString().endsWith(".png") || p.toString().endsWith(".jpg")) {
                String key = p.toString().substring(p.toString().lastIndexOf(separator) + 1);
                System.out.println(key + " being loaded...");
                images.put(key, new Image(p.toUri().toString().replace("%2520", "%20"))); // Cleanup bad spaces in path
            }
        });
        // MUSIC
        music = new HashMap<>();
        if (!noMusic) {
            URI musicURI = AssetLoader.class.getResource(MUSIC_DIR).toURI();
            if (musicURI.getScheme().equals("jar")) {
                path = fs.getPath(MUSIC_DIR);
            } else {
                path = Paths.get(musicURI);
            }
            Files.walk(path, 1).forEach(p -> {
                if (p.toString().endsWith(".mp3")) {
                    String key = p.toString().substring(p.toString().lastIndexOf(separator) + 1);
                    System.out.println(key + " being loaded...");
                    music.put(key, new Media(p.toUri().toString().replace("%2520", "%20"))); // Cleanup bad spaces in path
                }
            });
        }
        // CLEANUP
        if (fs != null) {
            fs.close();
        }
    }

    public static Image getImage(String name) {
        return images.getOrDefault(name, null);
    }

    public static Media getMusic(String name) {
        return music.getOrDefault(name, null);
    }

    public static String getFontLoc() {
        return fontLoc;
    }
}
