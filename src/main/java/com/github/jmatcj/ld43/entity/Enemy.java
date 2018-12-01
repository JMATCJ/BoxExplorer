package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.util.Util;
import com.github.jmatcj.ld43.world.Room;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy implements Entity {

    private double xPos;
    private double yPos;
    private double velocity;
//    private int i = 0;

    public Enemy(double xPos, double yPos, double velocity) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.velocity = velocity;
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.save();
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



        if (Math.abs(xPos - Projectile.getX()) == 0) {
            if (Math.abs(yPos - Projectile.getY()) == 0) {
                killEnemy();
            }
        }
    }

}
