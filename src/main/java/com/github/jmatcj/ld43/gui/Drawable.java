package com.github.jmatcj.ld43.gui;

import com.github.jmatcj.ld43.Game;
import javafx.scene.canvas.GraphicsContext;

public interface Drawable {

    void draw(GraphicsContext gc, Game g);
}
