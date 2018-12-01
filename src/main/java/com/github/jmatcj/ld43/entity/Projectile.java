package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Projectile implements Entity {
    private double x;
    private double y;
    private double dx;
    private double dy;
    
    public double getX() { return x; }
    public double getY() { return y; }

    public Projectile(double startX, double startY, double mouseX, double mouseY, double velocity) {
        this.x = startX;
        this.y = startY;

        dx = (x > mouseX) ? -Math.cos(Math.atan2(Math.abs(y - mouseY), Math.abs(x - mouseX))) * velocity : Math.cos(Math.atan2(Math.abs(y - mouseY), Math.abs(x - mouseX))) * velocity;
        dy = (y > mouseY) ? -Math.sin(Math.atan2(Math.abs(y - mouseY), Math.abs(x - mouseX))) * velocity : Math.sin(Math.atan2(Math.abs(y - mouseY), Math.abs(x - mouseX))) * velocity;
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, 10, 10);
    }

    @Override
    public void update(long ns, Game g) {
        x += dx;
        y += dy;
    }
}
