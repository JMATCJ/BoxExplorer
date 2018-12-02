package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Switch extends Entity {

    private Color color = Color.BLACK;

    public Switch(double xPos, double yPos) {
        super(xPos, yPos, 15.0, 15.0);
    }

    public void toggleSwitch() {
        color = Color.GREEN;
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        super.draw(gc, g);
        gc.save();
        drawSquare(gc, color, false);
        gc.restore();
    }

    @Override
    public void update(long ns, Game g) {
        super.update(ns, g);
    }
}
