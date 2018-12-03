package com.github.jmatcj.ld43.stat;

public enum Stat {
    HP(0),
    ATTACK(1),
    SPEED(1);

    private int minValue;

    Stat(int minValue) {
        this.minValue = minValue;
    }

    public int getMinValue() {
        return minValue;
    }
}
