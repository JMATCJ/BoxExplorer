package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.event.EventListener;
import com.github.jmatcj.ld43.gui.Drawable;
import com.github.jmatcj.ld43.tick.Updatable;
import com.github.jmatcj.ld43.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Entity implements Drawable, Updatable, EventListener {
    protected double xPos;
    protected double yPos;
    protected double mouseX;
    protected double mouseY;
    
    protected Entity(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        
        gc.save();
        Util.rotate(gc, Math.toDegrees(Math.atan2(yPos - mouseY, xPos - mouseX)), xPos + 5, yPos + 5);
        gc.setFill(Color.RED);
        gc.fillRect(xPos, yPos, 10, 10);
        gc.setFill(Color.GREEN);
        gc.fillRect(xPos, yPos + 5, 3,1);
        gc.restore();

    }

    @Override
    public void handleEvent(InputEvent event, Game g) {
        if (event instanceof MouseEvent) {
            MouseEvent evt = (MouseEvent)event;
            mouseX = evt.getX();
            mouseY = evt.getY();
        }
    }


    @Override
    public void update(long ns, Game g) {

    }

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
