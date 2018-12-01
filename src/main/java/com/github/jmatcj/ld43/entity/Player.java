package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.event.EventListener;
import com.github.jmatcj.ld43.gui.Drawable;
import com.github.jmatcj.ld43.tick.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Player implements EventListener, Updatable, Drawable, Entity {
    private double xPos;
    private double yPos;
    private double velocity;

    public Player(double xPos, double yPos, double velocity) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.velocity =  velocity;
    }

    @Override
    public void handleEvent(InputEvent event, Game g) {
        if (event instanceof KeyEvent) {
            KeyEvent evt = (KeyEvent)event;
            switch(evt.getCode()) {
                case W:
                    yPos -= velocity;
                    break;
                case A:
                    xPos -= velocity;
                    break;
                case S:
                    yPos += velocity;
                    break;
                case D:
                    xPos += velocity;
                    break;
            }
        }

        if (event instanceof MouseEvent) {
            MouseEvent evt = (MouseEvent)event;
            switch(evt.getButton()) {
                case PRIMARY:
                    g.addListener(new Projectile(xPos, yPos, evt.getX(), evt.getY(), 20.0));
                    break;
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.setFill(Color.RED);
        gc.fillRect(xPos, yPos, 10, 10);
    }

    @Override
    public void update(long ns, Game g) {
        // TODO
    }
}
