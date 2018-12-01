package com.github.jmatcj.ld43.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public final class OS {
    enum Type {
        WINDOWS("WIN"),
        MACOS("MAC"),
        LINUX("NUX"),
        UNIX("NIX"),
        OTHER(null);

        private static final Type[] VALID_VALUES = {WINDOWS, MACOS, LINUX, UNIX};

        private String identifier;

        Type(String identifier) {
            this.identifier = identifier;
        }

        public static Type getType(String identifier) {
            return Arrays.stream(VALID_VALUES).filter(t -> identifier.contains(t.identifier)).findFirst().orElse(OTHER);
        }
    }

    private static final String DATA_DIR_NAME = "JMCJ_LDJam43";
    private static final Type TYPE = Type.getType(System.getProperty("os.name").toUpperCase());

    public static Path getDataDir() {
        switch(TYPE) {
            case WINDOWS:
                return Paths.get(System.getenv("APPDATA"), DATA_DIR_NAME);
            case MACOS:
                return Paths.get(System.getProperty("user.home"), "Library", "Application Support", DATA_DIR_NAME);
            case LINUX:
            case UNIX:
            case OTHER:
            default: // Technically shouldn't happen, but I need the compiler to stop complaining.
                return Paths.get(System.getProperty("user.home"), "." + DATA_DIR_NAME);
        }
    }
}
