package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Player extends Entity {
    private double velocity;

    public Player(double xPos, double yPos, double velocity) {
        super(xPos, yPos);
        this.velocity =  velocity;
    }

    public void handleEvent(InputEvent event, Game g) {
        super.handleEvent(event, g);
        if (event instanceof MouseEvent) {
            MouseEvent evt = (MouseEvent)event;
            switch(evt.getButton()) {
                case PRIMARY:
                    g.addListener(new Projectile(xPos, yPos, evt.getX(), evt.getY(), 20.0));
                    break;
            }
        }
    }

    public void update(long ns, Game g) {
        if (g.getKeyDown().contains(KeyCode.W)) {
            yPos -= velocity;
            if (yPos < 0) { yPos = 0; }
        }

        if (g.getKeyDown().contains(KeyCode.A)) {
            xPos -= velocity;
            if (xPos < 0) { xPos = 0; }
        }

        if (g.getKeyDown().contains(KeyCode.S)) {
            yPos += velocity;
            if (yPos > 700) { yPos = 700; }
        }

        if (g.getKeyDown().contains(KeyCode.D)) {
            xPos += velocity;
            if (xPos > 1260) { xPos = 1260; }
        }
    }
}
