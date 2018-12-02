package com.github.jmatcj.ld43.stat;

public interface Statable {

    int getStatValue(Stat type);

    void addToStat(Stat type, int delta);
}
