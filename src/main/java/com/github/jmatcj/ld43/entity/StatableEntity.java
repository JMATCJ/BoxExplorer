package com.github.jmatcj.ld43.entity;

import static com.github.jmatcj.ld43.stat.Stat.*;

import com.github.jmatcj.ld43.stat.Stat;
import com.github.jmatcj.ld43.stat.Statable;
import java.util.EnumMap;
import java.util.Map;

public class StatableEntity extends Entity implements Statable {
    private Map<Stat, Integer> statMap;

    protected StatableEntity(double xPos, double yPos, double width, double height, int baseHealth, int baseAttack, int baseSpeed, int baseBulletSpeed) {
        super(xPos, yPos, width, height);
        this.statMap = new EnumMap<>(Stat.class);
        this.statMap.put(HP, baseHealth);
        this.statMap.put(ATTACK, baseAttack);
        this.statMap.put(SPEED, baseSpeed);
        this.statMap.put(BULLETSPEED, baseBulletSpeed);
    }

    @Override
    public int getStatValue(Stat type) {
        return statMap.get(type);
    }

    @Override
    public int addToStat(Stat type, int delta) {
        int newValue = statMap.get(type) + delta;
        if (newValue < type.getMinValue()) {
            newValue = type.getMinValue();
        }
        statMap.put(type, newValue);
        return newValue;
    }
}
