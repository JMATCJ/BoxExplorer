package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.paint.Color;

public class Item extends Entity {

    public Item(double xPos, double yPos) {
        super(xPos, yPos, 15, 15);
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.save();
        drawSquare(gc, Color.ORANGE, false);
        gc.restore();
    }

    @Override
    public void handleEvent(InputEvent event, Game g) {

    }

    @Override
    public void update(long ns, Game g) {

    }
}
