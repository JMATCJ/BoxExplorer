package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ladder extends Entity {
    private Color color;

    public Ladder() {
        super(500.0, 100.0, 25.0, 25.0);
        this.color = Color.RED;
    }
    
    public void activate() {
        color = Color.GREEN;
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
