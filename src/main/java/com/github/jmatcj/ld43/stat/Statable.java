package com.github.jmatcj.ld43.stat;

public interface Statable {

    /**
     * Returns the current value of the specified stat
     * @param type The stat to get the value of
     * @return The value of the specified stat
     */
    int getStatValue(Stat type);

    /**
     * Adds a specific amount to the specified stat
     * @param type The stat to change the value of
     * @param delta The amount to change the value by
     * @return The new value of the stat
     */
    int addToStat(Stat type, int delta);
}
