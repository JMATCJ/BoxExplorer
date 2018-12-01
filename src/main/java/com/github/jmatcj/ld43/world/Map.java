package com.github.jmatcj.ld43.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private List<Room> rooms;
    private int curRoom;

    // TODO Things a Map will probably need
    //  * Number of rooms
    //  * Maybe something else
    public Map(Random rng) {
        int numRooms = rng.nextInt(11) + 10; // Will generate between 10 - 20 rooms inclusive
        // Create the rooms
        rooms = new ArrayList<>(numRooms);
        for (int i = 0; i < numRooms; i++) {
            rooms.add(new Room(i + 1, Room.Size.values()[rng.nextInt(3)]));
        }
        curRoom = 0;
        // Create the paths between them
        // Starting room should have adjacent rooms on every side
        for (int i = 1; i <= 4; i++) {
            rooms.get(0).addAdjacentRoom(Room.Direction.VALUES[i - 1], rooms.get(i));
            rooms.get(i).addAdjacentRoom(Room.Direction.OPPOSITES[Room.Direction.VALUES[i - 1].ordinal()], rooms.get(0));
        }
        int roomsSet = 5; // Number of rooms we have currently setup
        Room r1 = rooms.get(1); // Room we are currently operating on
        Room.Direction lastDir = Room.Direction.UP; // The last direction we took
        while (roomsSet < numRooms) {
            if (rng.nextBoolean()) { // Should we branch from this room
                Room.Direction dir = Room.Direction.VALUES[rng.nextInt(4)];
                if (r1.hasAdjacentRoom(dir)) {
                    lastDir = dir;
                    r1 = r1.getAdjacentRoom(dir);
                } else {
                    r1.addAdjacentRoom(dir, rooms.get(roomsSet));
                    rooms.get(roomsSet).addAdjacentRoom(Room.Direction.getOpposite(dir), r1);
                    r1 = rooms.get(roomsSet++); // Set the current room to the one we just linked to, and increment the roomsSet count
                    lastDir = dir;
                }
            } else {
                lastDir = Room.Direction.getOpposite(lastDir);
                r1 = r1.getAdjacentRoom(lastDir);
            }
        }
    }

    public Room getCurrentRoom() {
        return rooms.get(curRoom);
    }
}