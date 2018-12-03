package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import static com.github.jmatcj.ld43.stat.Stat.*;

public class Enemy extends StatableEntity {
    private double velocity;

    public Enemy(double xPos, double yPos, double velocity) {
        super(xPos, yPos, 10, 10, 5, 1, 1);
        this.velocity = velocity;
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.save();
        Util.drawText(gc, Color.BLACK, 12, TextAlignment.CENTER, getStatValue(HP) + "", (int)xPos + 5, (int)yPos - 5);
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
        xPos += dx * fracTime * ((getStatValue(SPEED) - 1) / 10.0 + 1);
        yPos += dy * fracTime * ((getStatValue(SPEED) - 1) / 10.0 + 1);

        keepInBounds(); // If the enemy has moved out-of-bounds, move them back in

        Entity collision = checkCollision(g);
        if (collision != null) {
            if (collision instanceof Projectile) {
                Projectile p = (Projectile)collision;
                g.removeEntity(p);
                if (addToStat(HP, -p.getDamage()) == 0) {
                    g.removeEntity(this);
                }
            }
            if (collision instanceof Player) {
                xPos -= dx * fracTime;
                yPos -= dy * fracTime;
            }
        }

        updateNS(ns);
    }
}
