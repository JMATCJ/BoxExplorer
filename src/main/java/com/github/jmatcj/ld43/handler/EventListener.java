package com.github.jmatcj.ld43.handler;

import com.github.jmatcj.ld43.Game;
import javafx.scene.input.InputEvent;

public interface EventListener {

    void handleEvent(InputEvent event, Game g);
}
