package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.LDJam43;
import com.github.jmatcj.ld43.gui.Drawable;
import com.github.jmatcj.ld43.handler.EventListener;
import com.github.jmatcj.ld43.handler.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Entity implements Drawable, Updatable, EventListener {
    protected double xPos;
    protected double yPos;
    protected double width;
    protected double height;
    protected double mouseX;
    protected double mouseY;
    protected long prevNS;
    
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

    public void setNewPos(double newX, double newY) {
        this.xPos = newX;
        this.yPos = newY;
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

    protected void updateNS(long curNS) {
        prevNS = curNS;
    }

    protected void drawSquare(GraphicsContext gc, Color c, boolean withFrontIndicator) {
        gc.setFill(c);
        gc.fillRect(xPos, yPos, width, height);
        if (withFrontIndicator) {
            gc.setFill(Color.GREEN);
            gc.fillRect(xPos, yPos + height / 2, 5, 1);
        }
    }

    protected void keepInBounds() {
        if (xPos < 0) {
            xPos = 0;
        } else if (xPos > LDJam43.SCREEN_WIDTH - width) {
            xPos = LDJam43.SCREEN_WIDTH - width - 10;
        }

        if (yPos < 0) {
            yPos = 0;
        } else if (yPos > LDJam43.SCREEN_HEIGHT - height) {
            yPos = LDJam43.SCREEN_HEIGHT - height - 10;
        }
    }

    protected Entity checkCollision(Game g) {
        for (Entity e : g.getLoadedEntities()) {
            if (e != this) {
                if (yPos + height >= e.yPos && yPos <= e.yPos + e.height && xPos + width >= e.xPos && xPos <= e.xPos + e.width) {
                    return e;
                }
            }
        }
        return null;
    }
}
