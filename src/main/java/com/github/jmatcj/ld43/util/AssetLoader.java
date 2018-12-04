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
import javafx.scene.media.Media;

public class AssetLoader {
    private static final String ASSETS_DIR = "/assets/ld43/";
    private static final String MUSIC_DIR = ASSETS_DIR + "music/";
    private static Map<String, Media> music;

    public static void initialize(boolean noMusic) throws IOException, URISyntaxException {
        // MUSIC
        music = new HashMap<>();
        FileSystem fs = null; // Need a custom file system for JAR files...because reasons...
        if (!noMusic) {
            Path path;
            URI musicURI = AssetLoader.class.getResource(MUSIC_DIR).toURI();
            char separator; // The separator is different inside JAR files on Windows
            if (musicURI.getScheme().equals("jar")) {
                fs = FileSystems.newFileSystem(musicURI, Collections.emptyMap());
                path = fs.getPath(MUSIC_DIR);
                separator = '/';
            } else {
                path = Paths.get(musicURI);
                separator = File.separatorChar;
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

    public static Media getMusic(String name) {
        return music.getOrDefault(name, null);
    }
}
