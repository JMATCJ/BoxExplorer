package com.github.jmatcj.ld43.entity;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.stat.Stat;
import com.github.jmatcj.ld43.stat.Statable;
import com.github.jmatcj.ld43.util.Util;
import java.util.EnumMap;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

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
        statChanges.forEach(s::addToStat);
    }

    private String changesToText() {
        StringBuilder sb = new StringBuilder();
        statChanges.forEach((s, i) -> {
            sb.append(s.name()).append(" ");
            if (i > 0) {
                sb.append("+");
            }
            sb.append(i).append("\n");
        });
        return sb.toString();
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.save();
        drawSquare(gc, Color.ORANGE, false);
        Entity collision = checkCollision(g);
        if (collision instanceof Player) {
            String text = changesToText();
            int lines = text.length() - text.replace("\n", "").length();
            Util.drawText(gc, Color.BLACK, 12, TextAlignment.CENTER, changesToText(), xPos + width / 2, (yPos - (10 * lines)));
        }
        gc.restore();
    }

    @Override
    public void update(long ns, Game g) {

    }
}
