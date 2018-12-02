package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy extends Entity {
    private double velocity;

    public Enemy(double xPos, double yPos, double velocity) {
        super(xPos, yPos, 10, 10);
        this.velocity = velocity;
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.save();
        Util.rotate(gc, Math.toDegrees(Math.atan2(yPos - g.player.getY(), xPos - g.player.getX())), this);
        drawSquare(gc, Color.ORANGE, true);
        gc.restore();
    }

    @Override
    public void update(long ns, Game g) {
        // TODO
        // - Make it so there are multiple enemies
        if (prevNS == 0) {
            updateNS(ns);
        }

        double dx = (xPos > g.player.getX()) ? -Math.cos(Math.atan2(Math.abs(yPos - g.player.getY()), Math.abs(xPos - g.player.getX()))) * velocity : Math.cos(Math.atan2(Math.abs(yPos - g.player.getY()), Math.abs(xPos - g.player.getX()))) * velocity;
        double dy = (yPos > g.player.getY()) ? -Math.sin(Math.atan2(Math.abs(yPos - g.player.getY()), Math.abs(xPos - g.player.getX()))) * velocity : Math.sin(Math.atan2(Math.abs(yPos - g.player.getY()), Math.abs(xPos - g.player.getX()))) * velocity;

        double fracTime = Util.getFracOfTimeElapsed(prevNS, ns);
        xPos += dx * fracTime;
        yPos += dy * fracTime;

        keepInBounds(); // If the enemy has moved out-of-bounds, move them back in

        Entity collision = checkCollision(g);
        if (collision != null) {
            if (collision instanceof Projectile) {
                g.removeEntity(this);
                g.removeEntity(collision);
            }
            if (collision instanceof Player) {
                xPos -= dx * fracTime;
                yPos -= dy * fracTime;
            }
        }

        updateNS(ns);
    }

}
