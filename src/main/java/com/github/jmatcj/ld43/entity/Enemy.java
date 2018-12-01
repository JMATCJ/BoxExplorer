package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy implements Entity {

    private double xPos;
    private double yPos;
    private double dx;
    private double dy;
    private double velocity;

    public Enemy(double xPos, double yPos, double velocity) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.velocity = velocity;
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.save();
        Util.rotate(gc, Math.toDegrees(Math.atan2(yPos - g.player.getY(), xPos - g.player.getX())), xPos + 5, yPos + 5);
        gc.setFill(Color.ORANGE);
        gc.fillRect(xPos, yPos, 10, 10);
        gc.setFill(Color.GREEN);
        gc.fillRect(xPos, yPos + 5, 3, 1);
        gc.restore();
    }

    public void killEnemy() {
        //Game.removeListener(Game.enemy);
    }

    @Override
    public void update(long ns, Game g) {
        // TODO
        // - Make it so when the enemy is shot it dies
        // - Make it so there are multiple enemies
        // - Make them move in a better manner so its not trash

        dx = (xPos > g.player.getX()) ? -Math.cos(Math.atan2(Math.abs(yPos - g.player.getY()), Math.abs(xPos - g.player.getX()))) * velocity : Math.cos(Math.atan2(Math.abs(yPos - g.player.getY()), Math.abs(xPos - g.player.getX()))) * velocity;
        dy = (yPos > g.player.getY()) ? -Math.sin(Math.atan2(Math.abs(yPos - g.player.getY()), Math.abs(xPos - g.player.getX()))) * velocity : Math.sin(Math.atan2(Math.abs(yPos - g.player.getY()), Math.abs(xPos - g.player.getX()))) * velocity;

        xPos += dx;
        yPos += dy;
    }

}
