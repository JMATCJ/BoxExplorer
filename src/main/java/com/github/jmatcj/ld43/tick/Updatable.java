package com.github.jmatcj.ld43.tick;

import com.github.jmatcj.ld43.Game;

public interface Updatable {

    void update(long ns, Game g);
}
