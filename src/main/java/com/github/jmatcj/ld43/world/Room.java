package com.github.jmatcj.ld43.world;

import com.github.jmatcj.ld43.entity.Entity;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

public class Room {
    public enum Size {
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

        public static final Direction[] VALUES    = {UP, DOWN, LEFT, RIGHT};
        public static final Direction[] OPPOSITES = {DOWN, UP, RIGHT, LEFT};

        public static Direction getOpposite(Direction dir) {
            return OPPOSITES[dir.ordinal()];
        }
    }

    private int num;
    private Size size;
    private Set<Entity> entities;
    private java.util.Map<Direction, Room> adjacentRooms;

    public Room(int num, Size size) {
        this.num = num;
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

    public Collection<Entity> getEntities() {
        return entities;
    }

    public boolean hasAdjacentRoom(Direction dir) {
        return adjacentRooms.get(dir) != null;
    }

    public Room getAdjacentRoom(Direction dir) {
        return adjacentRooms.get(dir);
    }

    void addAdjacentRoom(Direction dir, Room other) {
        adjacentRooms.put(dir, other);
    }

    @Override
    public String toString() {
        return "Room #" + num;
    }
}
