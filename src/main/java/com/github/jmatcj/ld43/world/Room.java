package com.github.jmatcj.ld43.world;

import static com.github.jmatcj.ld43.LDJam43.*;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.entity.Entity;
import com.github.jmatcj.ld43.entity.Player;
import com.github.jmatcj.ld43.gui.Drawable;
import com.github.jmatcj.ld43.handler.Updatable;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Room implements Updatable, Drawable {
    private static final int HALLWAY_SIZE = 20;
    private static final int[] HALLWAYS_X = {SCREEN_WIDTH / 2 - HALLWAY_SIZE / 2, SCREEN_WIDTH / 2 - HALLWAY_SIZE / 2, 0, SCREEN_WIDTH - HALLWAY_SIZE};
    private static final int[] HALLWAYS_Y = {0, SCREEN_HEIGHT - HALLWAY_SIZE, SCREEN_HEIGHT / 2 - HALLWAY_SIZE / 2, SCREEN_HEIGHT / 2 - HALLWAY_SIZE / 2};
    
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
    private Set<Entity> entities;
    private java.util.Map<Direction, Room> adjacentRooms;

    public Room(int num) {
        this.num = num;
        this.entities = new HashSet<>();
        this.adjacentRooms = new EnumMap<>(Direction.class);
    }

    public int getNum() {
        return num;
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
        return adjacentRooms.containsKey(dir);
    }

    public Room getAdjacentRoom(Direction dir) {
        return adjacentRooms.get(dir);
    }

    void addAdjacentRoom(Direction dir, Room other) {
        adjacentRooms.put(dir, other);
    }

    @Override
    public void update(long ns, Game g) {
        for (Direction dir : Direction.VALUES) {
            if (hasAdjacentRoom(dir)) {
                Player p = g.player;
                if (p.getX() <= HALLWAYS_X[dir.ordinal()] + HALLWAY_SIZE && p.getX() + p.getWidth() >= HALLWAYS_X[dir.ordinal()] &&
                        p.getY() <= HALLWAYS_Y[dir.ordinal()] + HALLWAY_SIZE && p.getY() + p.getHeight() >= HALLWAYS_Y[dir.ordinal()]) {
                    g.queueRoomTransition(dir);
                    break; // No point in checking the other directions at this point
                }
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        for (Direction dir : Direction.VALUES) {
            if (hasAdjacentRoom(dir)) {
                gc.save();
                gc.setFill(Color.BLACK);
                gc.fillRect(HALLWAYS_X[dir.ordinal()], HALLWAYS_Y[dir.ordinal()], HALLWAY_SIZE, HALLWAY_SIZE);
                gc.restore();
            }
        }
    }

    @Override
    public String toString() {
        return "Room #" + num;
    }
}
