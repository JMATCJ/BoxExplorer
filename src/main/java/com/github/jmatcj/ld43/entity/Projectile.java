package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.LDJam43;
import com.github.jmatcj.ld43.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Projectile extends Entity {
    private double dx;
    private double dy;
    private int damage;
    private int bounceCount;
    private boolean playerShot;
    private Color color;

    public Projectile(double startX, double startY, double mouseX, double mouseY, double velocity, int damage, boolean playerShot, Color color) {
        super(startX, startY, 10, 10);
        dx = (xPos > mouseX) ? -Math.cos(Math.atan2(Math.abs(yPos - mouseY), Math.abs(xPos - mouseX))) * velocity : Math.cos(Math.atan2(Math.abs(yPos - mouseY), Math.abs(xPos - mouseX))) * velocity;
        dy = (yPos > mouseY) ? -Math.sin(Math.atan2(Math.abs(yPos - mouseY), Math.abs(xPos - mouseX))) * velocity : Math.sin(Math.atan2(Math.abs(yPos - mouseY), Math.abs(xPos - mouseX))) * velocity;
        this.color = color;
        this.damage = damage;
        this.playerShot = playerShot;
    }

    public int getDamage() {
        return damage;
    }

    public boolean playerIgnore() {
        return playerShot && bounceCount == 0;
    }
    
    public boolean enemyIgnore() {
        return !playerShot;
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.save();
        drawSquare(gc, color, false);
        gc.restore();
    }

    @Override
    public void update(long ns, Game g) {
        if (prevNS == 0) {
            updateNS(ns);
        }

        xPos += dx * Util.getFracOfTimeElapsed(prevNS, ns);
        yPos += dy * Util.getFracOfTimeElapsed(prevNS, ns);

        if (bounceCount >= 3) {
            g.removeEntity(this);
        }
        if (xPos < 0 || xPos > LDJam43.SCREEN_WIDTH - width) {
            dx *= -1;
            bounceCount++;
        }
        if (yPos < 0 || yPos > LDJam43.SCREEN_HEIGHT - height) {
            dy *= -1;
            bounceCount++;
        }

        updateNS(ns);
    }
}
