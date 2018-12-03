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
    public static final double ITEM_SIZE = 15;

    private Map<Stat, Integer> statChanges;
    private Color color;

    public Item(double xPos, double yPos, Color color) {
        super(xPos, yPos, ITEM_SIZE, ITEM_SIZE);
        this.statChanges = new EnumMap<>(Stat.class);
        this.color = color;
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
        drawSquare(gc, color, false);
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
