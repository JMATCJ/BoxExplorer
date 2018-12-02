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
            rooms.add(new Room(i, Room.Size.values()[rng.nextInt(3)]));
        }
        curRoom = 0;
        // Create the paths between them
        // Starting room should have adjacent rooms on every side
        for (int i = 1; i <= 4; i++) {
            rooms.get(0).addAdjacentRoom(Room.Direction.VALUES[i - 1], rooms.get(i));
            rooms.get(i).addAdjacentRoom(Room.Direction.OPPOSITES[Room.Direction.VALUES[i - 1].ordinal()], rooms.get(0));
        }
        int roomsSet = 5; // Number of rooms we have currently setup
        int curBase = 1;
        Room r1 = rooms.get(curBase); // Room we are currently operating on
        while (roomsSet < numRooms) {
            if (rng.nextBoolean()) { // Should we branch from this room
                Room.Direction dir = Room.Direction.VALUES[rng.nextInt(4)];
                if (r1.hasAdjacentRoom(dir)) { // Does this room already have a room adjacent in this direction
                    r1 = r1.getAdjacentRoom(dir); // Start operating on that room
                } else {
                    r1.addAdjacentRoom(dir, rooms.get(roomsSet)); // Make it adjacent with the next room in the list
                    rooms.get(roomsSet).addAdjacentRoom(Room.Direction.getOpposite(dir), r1);
                    roomsSet++;
                    curBase = (curBase % 4) + 1;
                    r1 = rooms.get(curBase); // Set the current room to the one we just linked to, and increment the roomsSet count
                }
            } else { // Recurse back to the previous room we were in and try again
                r1 = rooms.get(rng.nextInt(roomsSet));
                //curBase = (curBase % 4) + 1;
                //r1 = rooms.get(curBase); // Set the current room to the one we just linked to, and increment the roomsSet count
            }
        }
    }

    public Room getCurrentRoom() {
        return rooms.get(curRoom);
    }
}