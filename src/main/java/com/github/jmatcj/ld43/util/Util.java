package com.github.jmatcj.ld43.util;

import com.github.jmatcj.ld43.entity.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;

public class Util {
    /**
     * A single second in nanoseconds
     */
    public static final long SECOND_IN_NS = 1000000000L;

    /**
     * Returns the number of nanoseconds for a number of seconds.
     * @param sec The number of seconds to get nanoseconds for.
     * @return The number of nanoseconds for the specified number of seconds.
     */
    public static long timeInNS(int sec) {
        return sec * SECOND_IN_NS;
    }

    /**
     * Calculates if a specific amount of time has passed.
     * @param startNS The start time in nanoseconds.
     * @param curNS The current time in nanoseconds.
     * @param sec The number of seconds you want to pass for this to be true.
     * @return true if the specified number of seconds have passed, false otherwise.
     */
    public static boolean hasTimeElapsed(long startNS, long curNS, int sec) {
        return startNS + Util.timeInNS(sec) <= curNS;
    }

    /**
     * Get the fraction of time that has elapsed between two NS time values.
     * Should be used for all entity velocities.
     * @param prevNS The previous NS value.
     * @param curNS The current NS value.
     * @return The fraction of time that has passed
     */
    public static double getFracOfTimeElapsed(long prevNS, long curNS) {
        return (curNS - prevNS) / (double)SECOND_IN_NS;
    }

    /**
     * Renders text to the screen. The application default font (Code Bold) will be used.
     * @param gc The GraphicsContext for the Canvas to be drawn to.
     * @param fontColor The color of the font.
     * @param fontSize The size of the font.
     * @param alignment The alignment of the text.
     * @param text The String of text to be drawn to the screen.
     * @param x The X location to start drawing at.
     * @param y The Y location to start drawing at.
     */
    // TODO: Fix this with whatever font we're going to use
    public static void drawText(GraphicsContext gc, Color fontColor, int fontSize, TextAlignment alignment, String text, double x, double y) {
        gc.setFill(fontColor);
        gc.setFont(Font.font("Verdana", FontWeight.BOLD, fontSize));
        //gc.setFont(Font.loadFont(AssetLoader.getFontLoc(), fontSize));
        gc.setTextAlign(alignment);
        gc.fillText(text, x, y);
    }

    /**
     * Rotates an entity around its midpoint
     * @param gc The GraphicsContext we are drawing to
     * @param angle The angle the entity should be rotated to
     * @param e The Entity being rotated
     */
    public static void rotate(GraphicsContext gc, double angle, Entity e) {
        rotate(gc, angle, e.getX() + e.getWidth() / 2, e.getY() + e.getHeight() / 2);
    }

    /**
     * Rotates the GraphicsContext around a given point
     * @param gc The GraphicsContext we are drawing to
     * @param angle The angle of rotation
     * @param px The x-position of the rotation point
     * @param py The y-position of the rotation point
     */
    public static void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }
}
