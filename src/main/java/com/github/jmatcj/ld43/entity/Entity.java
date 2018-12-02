package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.event.EventListener;
import com.github.jmatcj.ld43.gui.Drawable;
import com.github.jmatcj.ld43.tick.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;

public class Entity implements Drawable, Updatable, EventListener {
    protected double xPos;
    protected double yPos;
    protected double width;
    protected double height;
    protected double mouseX;
    protected double mouseY;
    
    protected Entity(double xPos, double yPos, double width, double height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }
    
    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {}

    @Override
    public void handleEvent(InputEvent event, Game g) {
        if (event instanceof MouseEvent) {
            MouseEvent evt = (MouseEvent)event;
            mouseX = evt.getX();
            mouseY = evt.getY();
        }
    }


    @Override
    public void update(long ns, Game g) {}

    protected Entity checkCollision(Game g) {
        for (Entity e : g.getLoadedEntities()) {
            if (e != this) {
                if (yPos + 10 >= e.yPos && yPos <= e.yPos + 10 && xPos + 10 >= e.xPos && xPos <= e.xPos + 10) {
                    return e;
                }
            }
        }
        return null;
    }
}
