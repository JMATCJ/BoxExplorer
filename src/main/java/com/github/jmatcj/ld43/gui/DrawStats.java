package com.github.jmatcj.ld43.gui;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.LDJam43;
import com.github.jmatcj.ld43.stat.Stat;
import com.github.jmatcj.ld43.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class DrawStats implements Drawable {
    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.save();
        Util.drawText(gc, Color.BLACK, 24, TextAlignment.LEFT, "Health = " + LDJam43.getGame().player.getStatValue(Stat.HP), 20, 40);
        Util.drawText(gc, Color.BLACK, 24, TextAlignment.LEFT, "Attack = " + LDJam43.getGame().player.getStatValue(Stat.ATTACK), 20, 80);
        Util.drawText(gc, Color.BLACK, 24, TextAlignment.LEFT, "Speed  = " + LDJam43.getGame().player.getStatValue(Stat.SPEED), 20, 120);
        Util.drawText(gc, Color.BLACK, 24, TextAlignment.LEFT, "Proj Speed  = " + LDJam43.getGame().player.getStatValue(Stat.SPEED), 20, 160);
        gc.restore();
    }
}
