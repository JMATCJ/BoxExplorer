package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.stat.Stat;
import com.github.jmatcj.ld43.stat.Statable;
import java.util.EnumMap;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.InputEvent;
import javafx.scene.paint.Color;

public class Item extends Entity {
    private Map<Stat, Integer> statChanges;

    public Item(double xPos, double yPos) {
        super(xPos, yPos, 15, 15);
        this.statChanges = new EnumMap<>(Stat.class);
    }

    public void addStatChange(Stat type, int change) {
        statChanges.put(type, change);
    }

    void applyStatChanges(Statable s) {
        for (Map.Entry<Stat, Integer> ent : statChanges.entrySet()) {
            s.addToStat(ent.getKey(), ent.getValue());
        }
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.save();
        drawSquare(gc, Color.ORANGE, false);
        gc.restore();
    }

    @Override
    public void update(long ns, Game g) {
        Entity collision = checkCollision(g);
        if (collision instanceof Player) {
            // TODO Draw text about what this powerup does
        }
    }
}
