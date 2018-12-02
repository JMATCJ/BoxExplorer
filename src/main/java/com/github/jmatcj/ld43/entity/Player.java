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
                    g.spawnEntity(new Projectile(xPos, yPos, evt.getX(), evt.getY(), 1250));
                    break;
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.save();
        Util.rotate(gc, Math.toDegrees(Math.atan2(yPos - mouseY, xPos - mouseX)), this);
        drawSquare(gc, Color.RED, true);
        gc.restore();
    }

    @Override
    public void update(long ns, Game g) {
        if (prevNS == 0) {
            updateNS(ns);
        }
        double frameVelocity = velocity * Util.getFracOfTimeElapsed(prevNS, ns);
        Entity collision = checkCollision(g);
        if (g.getKeyDown().contains(KeyCode.W)) {
            yPos -= frameVelocity;
            if (collision instanceof Enemy) {
                yPos += frameVelocity;
            }

        }

        if (g.getKeyDown().contains(KeyCode.A)) {
            xPos -= frameVelocity;
            if (collision instanceof Enemy) {
                xPos += frameVelocity;
            }
        }

        if (g.getKeyDown().contains(KeyCode.S)) {
            yPos += frameVelocity;
            if (collision instanceof Enemy) {
                yPos -= frameVelocity;
            }
        }

        if (g.getKeyDown().contains(KeyCode.D)) {
            xPos += frameVelocity;
            if (collision instanceof Enemy) {
                xPos -= frameVelocity;
            }
        }

        keepInBounds(); // If the player moved out-of-bounds, move them back in

        // Since this code should always be the same, no matter which way we're moving, we can keep it once down here
        if (collision instanceof ItemEntity) {
            g.removeEntity(collision);
            //Add item power up
        }
        if (collision instanceof Switch) {
            ((Switch) collision).toggleSwitch();
        }

        updateNS(ns);
    }
}
