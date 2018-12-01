package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.LDJam43;
import com.github.jmatcj.ld43.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Player implements Entity {
    private double xPos;
    private double yPos;
    private double mouseX;
    private double mouseY;
    private double velocity;

    public Player(double xPos, double yPos, double velocity) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.velocity =  velocity;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    @Override
    public void handleEvent(InputEvent event, Game g) {
        if (event instanceof MouseEvent) {
            MouseEvent evt = (MouseEvent)event;
            mouseX = evt.getX();
            mouseY = evt.getY();
            switch(evt.getButton()) {
                case PRIMARY:
                    g.addListener(new Projectile(xPos, yPos, evt.getX(), evt.getY(), 20.0));
                    break;
            }
        }
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
