package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class Player extends Entity {
    private double velocity;

    public Player(double xPos, double yPos, double velocity) {
        super(xPos, yPos);
        this.velocity =  velocity;
    }

    @Override
    public void handleEvent(InputEvent event, Game g) {
        super.handleEvent(event, g);
        if (event instanceof MouseEvent) {
            MouseEvent evt = (MouseEvent)event;
            switch(evt.getButton()) {
                case PRIMARY:
                    g.spawnEntity(new Projectile(xPos, yPos, evt.getX(), evt.getY(), 20.0));
                    break;
            }
        }
    }

    @Override
    public void update(long ns, Game g) {
        Entity collision = checkCollision(g);
        if (g.getKeyDown().contains(KeyCode.W)) {
            yPos -= velocity;
            if (yPos < 0) { yPos = 0; }
            if (collision instanceof Enemy) {
                yPos += velocity;
            }
            if (collision instanceof ItemEntity) {
                g.removeEntity(collision);
                //Add item power up
            }
        }

        if (g.getKeyDown().contains(KeyCode.A)) {
            xPos -= velocity;
            if (xPos < 0) { xPos = 0; }
            if (collision instanceof Enemy) {
                xPos += velocity;
            }
            if (collision instanceof ItemEntity) {
                g.removeEntity(collision);
               //Add item power up
            }
        }

        if (g.getKeyDown().contains(KeyCode.S)) {
            yPos += velocity;
            if (yPos > 710) { yPos = 710; }
            if (collision instanceof Enemy) {
                yPos -= velocity;
            }
            if (collision instanceof ItemEntity) {
                g.removeEntity(collision);
                //Add item power up
            }
        }

        if (g.getKeyDown().contains(KeyCode.D)) {
            xPos += velocity;
            if (xPos > 1270) { xPos = 1270; }
            if (collision instanceof Enemy) {
                xPos -= velocity;
            }
            if (collision instanceof ItemEntity) {
                g.removeEntity(collision);
                //Add item power up
            }
        }
    }
}
