package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.event.EventListener;
import com.github.jmatcj.ld43.gui.Drawable;
import com.github.jmatcj.ld43.tick.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;

public interface Entity extends Drawable, Updatable, EventListener {
    
	@Override
    default void draw(GraphicsContext gc, Game g) {

    }

    @Override
    default void handleEvent(InputEvent event, Game g) {

    }


    @Override
    default void update(long ns, Game g) {

    }
}
