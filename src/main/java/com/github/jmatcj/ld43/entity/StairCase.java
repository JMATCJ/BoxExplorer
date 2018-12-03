package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class StairCase extends Entity{
    
    private Color color = Color.RED;

    public StairCase(double xPos, double yPos) {
        super(xPos, yPos, 25.0, 25.0);
    }
    
    public void activate() {
        color = Color.GREEN;
        //Move to next level logic
    }

    public boolean isActivated() {
        return color == Color.GREEN;
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
