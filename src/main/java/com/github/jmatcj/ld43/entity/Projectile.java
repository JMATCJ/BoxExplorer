package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Projectile extends Entity {
    private double dx;
    private double dy;

    public Projectile(double startX, double startY, double mouseX, double mouseY, double velocity) {
        super(startX, startY);

        dx = (xPos > mouseX) ? -Math.cos(Math.atan2(Math.abs(yPos - mouseY), Math.abs(xPos - mouseX))) * velocity : Math.cos(Math.atan2(Math.abs(yPos - mouseY), Math.abs(xPos - mouseX))) * velocity;
        dy = (yPos > mouseY) ? -Math.sin(Math.atan2(Math.abs(yPos - mouseY), Math.abs(xPos - mouseX))) * velocity : Math.sin(Math.atan2(Math.abs(yPos - mouseY), Math.abs(xPos - mouseX))) * velocity;
    }

    public void draw(GraphicsContext gc, Game g) {
        gc.setFill(Color.BLUE);
        gc.fillRect(xPos, yPos, 10, 10);
    }

    public void update(long ns, Game g) {
        xPos += dx;
        yPos += dy;
    }
}
