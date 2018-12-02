package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.LDJam43;
import com.github.jmatcj.ld43.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Player extends Entity {
    private double velocity;

    public Player(double xPos, double yPos, double velocity) {
        super(xPos, yPos, 10, 10);
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
    public void draw(GraphicsContext gc, Game g) {
        gc.save();
        Util.rotate(gc, Math.toDegrees(Math.atan2(yPos - mouseY, xPos - mouseX)), this);
        gc.setFill(Color.RED);
        gc.fillRect(xPos, yPos, width, height);
        gc.setFill(Color.GREEN);
        gc.fillRect(xPos, yPos + width / 2, 3,1);
        gc.restore();
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
            if (collision instanceof Switch) {
                ((Switch) collision).toggleSwitch();
                // Toggle switch
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
            if (collision instanceof Switch) {
                ((Switch) collision).toggleSwitch();
                // Toggle switch
            }
        }

        if (g.getKeyDown().contains(KeyCode.S)) {
            yPos += velocity;
            if (yPos > LDJam43.SCREEN_HEIGHT - 10) { yPos = LDJam43.SCREEN_HEIGHT - 10; }
            if (collision instanceof Enemy) {
                yPos -= velocity;
            }
            if (collision instanceof ItemEntity) {
                g.removeEntity(collision);
                //Add item power up
            }
            if (collision instanceof Switch) {
                ((Switch) collision).toggleSwitch();
                // Toggle switch
            }
        }

        if (g.getKeyDown().contains(KeyCode.D)) {
            xPos += velocity;
            if (xPos > LDJam43.SCREEN_WIDTH - 10) { xPos = LDJam43.SCREEN_WIDTH - 10; }
            if (collision instanceof Enemy) {
                xPos -= velocity;
            }
            if (collision instanceof ItemEntity) {
                g.removeEntity(collision);
                //Add item power up
            }
            if (collision instanceof Switch) {
                ((Switch) collision).toggleSwitch();
                // Toggle switch
            }
        }
    }
}
