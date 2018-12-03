package com.github.jmatcj.ld43.world;

import com.github.jmatcj.ld43.Game;
import com.github.jmatcj.ld43.LDJam43;
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
            rooms.add(new Room(i));
        }
        curRoom = 0;

        populateRooms(rng);

        generatePaths(numRooms, rng);
    }

    public Room getCurrentRoom() {
        return rooms.get(curRoom);
    }

    /**
     * Call this when the player moves to the next room.
     * This method will handle loading/unloading everything in both of the rooms
     * @param traveled The direction they went to get to the next room
     * @return The new room the player moved into
     */
    public Room transition(Room.Direction traveled, Game g, Random rng) {
        rooms.get(curRoom).getEntities().forEach(e -> LDJam43.getGame().removeListener(e));
        LDJam43.getGame().removeListener(rooms.get(curRoom));
        curRoom = rooms.get(curRoom).getAdjacentRoom(traveled).getNum();
        LDJam43.getGame().addListener(rooms.get(curRoom));
        rooms.get(curRoom).getEntities().forEach(e -> LDJam43.getGame().addListener(e));
        for (int i = 0; i < rng.nextInt((10) + 1); i++) {
            g.spawnEnemy();
        }
        return getCurrentRoom();
    }

    private void populateRooms(Random rng) {
        // TODO Populate the rooms in the map
    }

    private void generatePaths(int numRooms, Random rng) {
        // Starting room should have adjacent rooms on every side
        for (int i = 1; i <= 4; i++) {
            rooms.get(0).addAdjacentRoom(Room.Direction.VALUES[i - 1], rooms.get(i));
            rooms.get(i).addAdjacentRoom(Room.Direction.getOpposite(Room.Direction.VALUES[i - 1]), rooms.get(0));
        }
        // A matrix to keep track of where rooms are in our grid
        int[][] roomMatrix = new int[numRooms * 2 - 7][numRooms * 2 - 7];
        // Set the starting room and the four rooms that are always adjacent to the starting room
        roomMatrix[roomMatrix.length / 2][roomMatrix.length / 2] = -1;
        roomMatrix[roomMatrix.length / 2 - 1][roomMatrix.length / 2] = 1;
        roomMatrix[roomMatrix.length / 2 + 1][roomMatrix.length / 2] = 2;
        roomMatrix[roomMatrix.length / 2][roomMatrix.length / 2 - 1] = 3;
        roomMatrix[roomMatrix.length / 2][roomMatrix.length / 2 + 1] = 4;
        int roomsSet = 5; // Number of rooms that have been set in the map
        int row = roomMatrix.length / 2 - 1; // The current row we are indexing into
        int col = roomMatrix.length / 2; // The current column we are indexing into
        while (roomsSet < numRooms) { // While we haven't set all the rooms yet
            if (rng.nextBoolean()) { // Should we branch from this room
                Room.Direction dir = Room.Direction.VALUES[rng.nextInt(4)]; // Pick a random direction to branch from
                int roomNum = roomMatrix[row][col] != -1 ? roomMatrix[row][col] : 0; // Get the room number for indexing into the list
                if (rooms.get(roomNum).hasAdjacentRoom(dir)) { // Do we already have an adjacent room
                    switch (dir) { // If so, recurse on that adjacent room
                        case UP:
                            row -= 1;
                            break;
                        case DOWN:
                            row += 1;
                            break;
                        case LEFT:
                            col -= 1;
                            break;
                        case RIGHT:
                            col += 1;
                            break;
                    }
                } else { // If we don't already have an explicit adjacent room
                    // For each direction, first check if there is a room that is spatially adjacent, but no path has been formed yet
                    //   If that is the case, form the path and recurse on that newly adjacent room
                    // If there is no spatially adjacent room, place a new room there and recurse on that new room.
                    switch (dir) {
                        case UP:
                            if (roomMatrix[row - 1][col] != 0) {
                                int other = roomMatrix[row - 1][col] != -1 ? roomMatrix[row - 1][col] : 0;
                                rooms.get(roomNum).addAdjacentRoom(Room.Direction.UP, rooms.get(other));
                                rooms.get(other).addAdjacentRoom(Room.Direction.DOWN, rooms.get(roomNum));
                                row -= 1;
                            } else {
                                roomMatrix[row - 1][col] = roomsSet;
                                rooms.get(roomNum).addAdjacentRoom(Room.Direction.UP, rooms.get(roomsSet));
                                rooms.get(roomsSet).addAdjacentRoom(Room.Direction.DOWN, rooms.get(roomNum));
                                roomsSet++;
                                row -= 1;
                            }
                            break;
                        case DOWN:
                            if (roomMatrix[row + 1][col] != 0) {
                                int other = roomMatrix[row + 1][col] != -1 ? roomMatrix[row + 1][col] : 0;
                                rooms.get(roomNum).addAdjacentRoom(Room.Direction.DOWN, rooms.get(other));
                                rooms.get(other).addAdjacentRoom(Room.Direction.UP, rooms.get(roomNum));
                                row += 1;
                            } else {
                                roomMatrix[row + 1][col] = roomsSet;
                                rooms.get(roomNum).addAdjacentRoom(Room.Direction.DOWN, rooms.get(roomsSet));
                                rooms.get(roomsSet).addAdjacentRoom(Room.Direction.UP, rooms.get(roomNum));
                                roomsSet++;
                                row += 1;
                            }
                            break;
                        case LEFT:
                            if (roomMatrix[row][col - 1] != 0) {
                                int other = roomMatrix[row][col - 1] != -1 ? roomMatrix[row][col - 1] : 0;
                                rooms.get(roomNum).addAdjacentRoom(Room.Direction.LEFT, rooms.get(other));
                                rooms.get(other).addAdjacentRoom(Room.Direction.RIGHT, rooms.get(roomNum));
                                col -= 1;
                            } else {
                                roomMatrix[row][col - 1] = roomsSet;
                                rooms.get(roomNum).addAdjacentRoom(Room.Direction.LEFT, rooms.get(roomsSet));
                                rooms.get(roomsSet).addAdjacentRoom(Room.Direction.RIGHT, rooms.get(roomNum));
                                roomsSet++;
                                col -= 1;
                            }
                            break;
                        case RIGHT:
                            if (roomMatrix[row][col + 1] != 0) {
                                int other = roomMatrix[row][col + 1] != -1 ? roomMatrix[row][col + 1] : 0;
                                rooms.get(roomNum).addAdjacentRoom(Room.Direction.RIGHT, rooms.get(other));
                                rooms.get(other).addAdjacentRoom(Room.Direction.LEFT, rooms.get(roomNum));
                                col += 1;
                            } else {
                                roomMatrix[row][col + 1] = roomsSet;
                                rooms.get(roomNum).addAdjacentRoom(Room.Direction.RIGHT, rooms.get(roomsSet));
                                rooms.get(roomsSet).addAdjacentRoom(Room.Direction.LEFT, rooms.get(roomNum));
                                roomsSet++;
                                col += 1;
                            }
                            break;
                    }
                }
            } else { // If we decided not to branch from here
                // Find a random room and recurse on that one
                // This is terribly inefficient, but I don't care.
                // Our data size is small enough that it shouldn't matter too much.
                int rand_row, rand_col;
                do {
                    rand_row = rng.nextInt(roomMatrix.length);
                    rand_col = rng.nextInt(roomMatrix.length);
                } while (roomMatrix[rand_row][rand_col] == 0);
                row = rand_row;
                col = rand_col;
            }
        }
    }
}