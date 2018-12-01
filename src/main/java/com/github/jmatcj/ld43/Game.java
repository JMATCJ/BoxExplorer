package com.github.jmatcj.ld43;

import com.github.jmatcj.ld43.entity.Enemy;
import com.github.jmatcj.ld43.entity.Entity;
import com.github.jmatcj.ld43.entity.Item;
import com.github.jmatcj.ld43.entity.Player;
import com.github.jmatcj.ld43.event.EventListener;
import com.github.jmatcj.ld43.gui.Drawable;
import com.github.jmatcj.ld43.tick.Updatable;
import com.github.jmatcj.ld43.world.Map;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

import javafx.event.Event;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Game {
    private final Random rng;
    private final Queue<InputEvent> queuedEvents;
    private final Set<EventListener> eventListeners;
    private final Set<Updatable> updateListeners;
    private final Set<Drawable> drawListeners;
    private Set<KeyCode> keyDown;
    private Map currentMap;
    public Entity enemy = new Enemy(800.0, 400.0, 0.1);
    public Player player = new Player(600.0, 300.0, 0.5);
    public Entity item = new Item(300.0, 300.0);

    public Game() {
        rng = new Random();
        currentMap = new Map(rng);
        queuedEvents = new LinkedList<>();
        eventListeners = new CopyOnWriteArraySet<>();
        updateListeners = new CopyOnWriteArraySet<>();
        drawListeners = new CopyOnWriteArraySet<>();
        keyDown = new HashSet<>();

        addListener(player); // TODO Move this later
        addListener(enemy);  // TODO make this its own class like spawn/control enemies
        addListener(item);
    }

    public Random getRNG() {
        return rng;
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

