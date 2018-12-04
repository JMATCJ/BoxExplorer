package com.github.jmatcj.ld43;

import com.github.jmatcj.ld43.entity.Enemy;
import com.github.jmatcj.ld43.entity.Entity;
import com.github.jmatcj.ld43.entity.Player;
import com.github.jmatcj.ld43.entity.StairCase;
import com.github.jmatcj.ld43.event.EventListener;
import com.github.jmatcj.ld43.gui.DrawStats;
import com.github.jmatcj.ld43.gui.Drawable;
import com.github.jmatcj.ld43.tick.Updatable;
import com.github.jmatcj.ld43.world.Map;
import com.github.jmatcj.ld43.world.Room;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Game {
    private final Random rng;
    private final Queue<InputEvent> queuedEvents;
    private final Set<EventListener> eventListeners;
    private final Set<Updatable> updateListeners;
    private final Set<Drawable> drawListeners;
    private Set<KeyCode> keyDown;
    private Map currentMap;
    private Room.Direction nextRoom;
    private boolean nextArea = false;
    public Player player = new Player(384.0, 384.0, 100);
    public StairCase stairCase = new StairCase(500.0, 100.0);


    public Game() {
        rng = new Random();
        currentMap = new Map(rng, 0);
        nextRoom = null;
        queuedEvents = new LinkedList<>();
        eventListeners = new CopyOnWriteArraySet<>();
        updateListeners = new CopyOnWriteArraySet<>();
        drawListeners = new CopyOnWriteArraySet<>();
        keyDown = new HashSet<>();

        addListener(new DrawStats());
        addListener(currentMap.getCurrentRoom());
        spawnEntity(player); // TODO Move this later
        spawnEntity(stairCase);
    }
    
    public Random getRNG() {
        return rng;
    }

    public void updateSwitchCount() {
        currentMap.updateSwitchCount();
    }

    public void setNextArea() {
        nextArea = !nextArea;
    }

    public int getTotalSwitches() {
        return currentMap.getTotalSwitches();
    }

    public int getToggledSwitches() {
        return currentMap.getToggledSwitches();
    }

    public void remakeEverything() {
        getLoadedEntities().forEach(e -> LDJam43.getGame().removeListener(e));
        removeListener(currentMap.getCurrentRoom());
        currentMap = new Map(rng, 1);
        addListener(currentMap.getCurrentRoom());
        spawnEntity(player);
        player.setNewPos(384.0, 384.0);
        stairCase = new StairCase(500.0, 100.0);
        spawnEntity(stairCase);
    }

    public boolean getNextArea() {
        return nextArea;
    }

    public void addToSet(KeyEvent evt) {
        keyDown.add(evt.getCode());
    }

    public void removeFromSet(KeyEvent evt) {
        keyDown.remove(evt.getCode());
    }

    public Set<KeyCode> getKeyDown () {
        return keyDown;
    }

    public void spawnEntity(Entity e) {
        currentMap.getCurrentRoom().addEntity(e);
        addListener(e);
    }
    
    public void removeEntity(Entity e) {
        currentMap.getCurrentRoom().removeEntity(e);
        removeListener(e);
    }

    public Collection<Entity> getLoadedEntities() {
        return currentMap.getCurrentRoom().getEntities();
    }

    public void addListener(Object obj) {
        if (obj instanceof EventListener) {
            eventListeners.add((EventListener)obj);
        }
        if (obj instanceof Updatable) {
            updateListeners.add((Updatable)obj);
        }
        if (obj instanceof Drawable) {
            drawListeners.add((Drawable)obj);
        }
    }

    public void removeListener(Object obj) {
        if (obj instanceof EventListener) {
            eventListeners.remove(obj);
        }
        if (obj instanceof Updatable) {
            updateListeners.remove(obj);
        }
        if (obj instanceof Drawable) {
            drawListeners.remove(obj);
        }
    }

    public void queueRoomTransition(Room.Direction dir) {
        nextRoom = dir;
    }

    public void handleRoomTransition() {
        if (nextRoom != null) {
            currentMap.getCurrentRoom().removeEntity(player);
            Room.Direction dir = currentMap.transition(nextRoom);
            currentMap.getCurrentRoom().addEntity(player);
            getLoadedEntities().stream().filter(e -> e instanceof Enemy).forEach(e -> {
                switch (dir) {
                    case RIGHT:
                        e.setNewPos(rng.nextInt(LDJam43.SCREEN_WIDTH / 2) + LDJam43.SCREEN_WIDTH / 2.0, rng.nextInt(LDJam43.SCREEN_HEIGHT));
                        break;
                    case LEFT:
                        e.setNewPos(rng.nextInt(LDJam43.SCREEN_WIDTH / 2), rng.nextInt(LDJam43.SCREEN_HEIGHT));
                        break;
                    case UP:
                        e.setNewPos(rng.nextInt(LDJam43.SCREEN_WIDTH), rng.nextInt(LDJam43.SCREEN_HEIGHT / 2));
                        break;
                    case DOWN:
                        e.setNewPos(rng.nextInt(LDJam43.SCREEN_WIDTH), rng.nextInt(LDJam43.SCREEN_HEIGHT / 2) + LDJam43.SCREEN_HEIGHT / 2.0);
                        break;
                }
            });
            switch(nextRoom) {
                case UP:
                    player.setNewPos(LDJam43.SCREEN_WIDTH / 2.0, LDJam43.SCREEN_HEIGHT - 40);
                    break;
                case DOWN:
                    player.setNewPos(LDJam43.SCREEN_WIDTH / 2.0, 40);
                    break;
                case LEFT:
                    player.setNewPos(LDJam43.SCREEN_WIDTH - 40, LDJam43.SCREEN_HEIGHT / 2.0);
                    break;
                case RIGHT:
                    player.setNewPos(40, LDJam43.SCREEN_HEIGHT / 2.0);
                    break;
            }
            nextRoom = null;
        }
    }

    public void queueEvent(InputEvent event) {
        queuedEvents.offer(event);
    }

    public void update(long ns) {
        while(queuedEvents.peek() != null) {
            InputEvent event = queuedEvents.poll();
            eventListeners.forEach(e -> e.handleEvent(event, this));
        }

        updateListeners.forEach(u -> u.update(ns, this));
    }

    public void draw(GraphicsContext gc) {
        drawListeners.forEach(d -> d.draw(gc, this));
    }
}

