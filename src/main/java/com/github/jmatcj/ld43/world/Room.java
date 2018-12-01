package com.github.jmatcj.ld43.world;

import com.github.jmatcj.ld43.entity.Entity;

import java.util.HashSet;
import java.util.Set;


public class Room {
    private enum Size {
        SMALL(10, 10), MEDIUM(15, 15), LARGE(20, 20);

        private int width;
        private int height;

        Size(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    Size size;
    Set<Entity> entities;

    public Room(Size size) {
        this.size = size;
        entities = new HashSet<>();
    }
}
