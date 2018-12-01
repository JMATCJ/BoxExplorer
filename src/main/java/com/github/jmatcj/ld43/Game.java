package com.github.jmatcj.ld43;

import com.github.jmatcj.ld43.entity.Player;
import com.github.jmatcj.ld43.event.EventListener;
import com.github.jmatcj.ld43.gui.Drawable;
import com.github.jmatcj.ld43.tick.Updatable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;

public class Game {
    private final Queue<InputEvent> queuedEvents;
    private final Set<EventListener> eventListeners;
    private final Set<Updatable> updateListeners;
    private final Set<Drawable> drawListeners;

    public Game() {
        queuedEvents = new LinkedList<>();
        eventListeners = new HashSet<>();
        updateListeners = new HashSet<>();
        drawListeners = new HashSet<>();

        addListener(new Player(128.0, 128.0)); // TODO Move this later
    }

    void addListener(Object obj) {
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

    void removeListener(Object obj) {
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

    void queueEvent(InputEvent event) {
        synchronized (queuedEvents) {
            queuedEvents.offer(event);
        }
    }

    void update(long ns) {
        synchronized (queuedEvents) {
            while(queuedEvents.peek() != null) {
                InputEvent event = queuedEvents.poll();
                eventListeners.forEach(e -> e.handleEvent(event, this));
            }
        }

        updateListeners.forEach(u -> u.update(ns, this));
    }

    void draw(GraphicsContext gc) {
        drawListeners.forEach(d -> d.draw(gc, this));
    }
}

