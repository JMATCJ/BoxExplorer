package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.LDJam43;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Projectile extends Entity {
    private double dx;
    private double dy;
    private int bounceCount = 0;

    public Projectile(double startX, double startY, double mouseX, double mouseY, double velocity) {
        super(startX, startY, 10, 10);

        dx = (xPos > mouseX) ? -Math.cos(Math.atan2(Math.abs(yPos - mouseY), Math.abs(xPos - mouseX))) * velocity : Math.cos(Math.atan2(Math.abs(yPos - mouseY), Math.abs(xPos - mouseX))) * velocity;
        dy = (yPos > mouseY) ? -Math.sin(Math.atan2(Math.abs(yPos - mouseY), Math.abs(xPos - mouseX))) * velocity : Math.sin(Math.atan2(Math.abs(yPos - mouseY), Math.abs(xPos - mouseX))) * velocity;
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.setFill(Color.BLUE);
        gc.fillRect(xPos, yPos, 10, 10);
    }

    @Override
    public void update(long ns, Game g) {
        xPos += dx;
        yPos += dy;

        if (bounceCount == 3) {
            g.removeEntity(this);
        }
        if (xPos < 0 || xPos > LDJam43.SCREEN_WIDTH - 10) {
            dx *= -1;
            bounceCount++;
        }
        if (yPos < 0 || yPos > LDJam43.SCREEN_HEIGHT - 10) {
            dy *= -1;
            bounceCount++;
        }
    }
}
