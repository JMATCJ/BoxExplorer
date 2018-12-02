package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.stat.Stat;
import com.github.jmatcj.ld43.stat.Statable;
import com.github.jmatcj.ld43.util.Util;
import java.util.EnumMap;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy extends Entity implements Statable {
    private double velocity;
    private Map<Stat, Integer> statMap;

    public Enemy(double xPos, double yPos, double velocity) {
        super(xPos, yPos, 10, 10);
        this.velocity = velocity;
        this.statMap = new EnumMap<>(Stat.class);
        this.statMap.put(Stat.HP, 5);
        this.statMap.put(Stat.ATTACK, 1);
        this.statMap.put(Stat.DEFENSE, 1);
        this.statMap.put(Stat.SPEED, 1);
    }

    @Override
    public int getStatValue(Stat type) {
        return statMap.get(type);
    }

    @Override
    public void addToStat(Stat type, int delta) {
        statMap.put(type, statMap.get(type) + delta);
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
