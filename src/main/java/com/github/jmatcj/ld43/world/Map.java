package com.github.jmatcj.ld43.world;

import java.util.Random;
import java.util.Set;

public class Map {
    private Set<Room> rooms;

    // TODO Things a Map will probably need
    //  * Number of rooms
    //  * Maybe something else
    public Map(Random rng) {
        int numRooms = rng.nextInt(11) + 5; // Will generate between 5 - 15 rooms inclusive
        for (int i = 0; i < numRooms; i++) {
            //TODO Generate the rooms
        }
    }
}