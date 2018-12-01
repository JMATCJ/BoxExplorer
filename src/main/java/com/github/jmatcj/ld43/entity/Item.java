package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.paint.Color;

public class Item implements Entity {
    private double xPos;
    private double yPos;

    public Item(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.save();
        gc.setFill(Color.ORANGE);
        gc.fillRect(xPos, yPos, 15, 15);
        gc.restore();
    }

    @Override
    public void handleEvent(InputEvent event, Game g) {

    }

    @Override
    public void update(long ns, Game g) {

    }
}
