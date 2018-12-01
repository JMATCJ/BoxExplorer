package com.github.jmatcj.ld43.world;

import com.github.jmatcj.ld43.entity.Entity;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

public class Room {
    private enum Size {
        SMALL(10, 10),
        MEDIUM(15, 15),
        LARGE(20, 20);

        private int width;
        private int height;

        Size(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }

    private Size size;
    private Set<Entity> entities;
    private java.util.Map<Direction, Room> adjacentRooms;

    public Room(Size size) {
        this.size = size;
        this.entities = new HashSet<>();
        this.adjacentRooms = new EnumMap<>(Direction.class);
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    public boolean hasAdjacentRoom(Direction dir) {
        return adjacentRooms.containsKey(dir);
    }

    public Room getAdjacentRoom(Direction dir) {
        return adjacentRooms.get(dir);
    }
}
