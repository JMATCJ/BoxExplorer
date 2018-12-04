package com.github.jmatcj.ld43.world;

import com.github.jmatcj.ld43.LDJam43;
import com.github.jmatcj.ld43.entity.Enemy;
import com.github.jmatcj.ld43.entity.Entity;
import com.github.jmatcj.ld43.entity.Item;
import com.github.jmatcj.ld43.entity.Ladder;
import com.github.jmatcj.ld43.entity.Switch;
import com.github.jmatcj.ld43.stat.Stat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;

public class Map {
    private static int powerMeter;
    private List<Room> rooms;
    private int curRoom;
    private int totalSwitches;
    private int toggledSwitches;
    private Ladder ladder;

    // TODO Things a Map will probably need
    //  * Number of rooms
    //  * Maybe something else
    public Map(Random rng, int powerMeter) {
        int numRooms = rng.nextInt(11) + 10; // Will generate between 10 - 20 rooms inclusive
        // Create the rooms
        rooms = new ArrayList<>(numRooms);
        for (int i = 0; i < numRooms; i++) {
            rooms.add(new Room(i));
        }
        curRoom = 0;
        toggledSwitches = 0;

        Map.powerMeter += powerMeter;

        populateRooms(rng);

        generatePaths(numRooms, rng);
    }

    public Room getCurrentRoom() {
        return rooms.get(curRoom);
    }

    public int getTotalSwitches() {
        return totalSwitches;
    }

    public int getToggledSwitches() {
        return toggledSwitches;
    }

    public void updateSwitchCount() {
        toggledSwitches++;
        if (toggledSwitches == totalSwitches) {
            ladder.activate();
        }
    }

    public void loadCurrentRoom() {
        LDJam43.getGame().addListener(rooms.get(curRoom));
        rooms.get(curRoom).getEntities().forEach(e -> LDJam43.getGame().addListener(e));
    }

    public void unloadCurrentRoom() {
        rooms.get(curRoom).getEntities().forEach(e -> LDJam43.getGame().removeListener(e));
        LDJam43.getGame().removeListener(rooms.get(curRoom));
    }

    /**
     * Call this when the player moves to the next room.
     * This method will handle loading/unloading everything in both of the rooms
     * @param traveled The direction they went to get to the next room
     */
    public void transition(Room.Direction traveled) {
        unloadCurrentRoom();
        curRoom = rooms.get(curRoom).getAdjacentRoom(traveled).getNum();
        loadCurrentRoom();
    }

    private void populateRooms(Random rng) {
        // Spawn Enemies
        int tmp;
        for (int i = 1; i < rooms.size(); i++) {
            double d = rng.nextDouble();
            if (d <= 0.05) { // No enemies (5% chance)
                continue;
            } else if (d <= 0.55) { // 1 - 3 enemies (50% chance)
                tmp = rng.nextInt(3) + 1;
            } else if (d <= 0.90) { // 4 - 6 enemies (35% chance)
                tmp = rng.nextInt(3) + 4;
            } else { // 7 - 8 enemies (10% chance)
                tmp = rng.nextInt(2) + 7;
            }
            for (int k = 0; k < tmp; k++) {
                Entity enemy = new Enemy(rng.nextInt((LDJam43.SCREEN_WIDTH) + 1), rng.nextInt((LDJam43.SCREEN_HEIGHT) + 1), 50, 5 + powerMeter, 1 + powerMeter, 1 + powerMeter * 5, 1 + powerMeter);
                rooms.get(i).addEntity(enemy);
            }
        }
        // Spawn Switches
        totalSwitches = rng.nextInt(3) + 3;
        for (int i = 0; i < totalSwitches; i++) {
            Entity switchEntity = new Switch(rng.nextInt(LDJam43.SCREEN_WIDTH - 50) + 50, rng.nextInt(LDJam43.SCREEN_HEIGHT - 50) + 50);
            rooms.get(rng.nextInt(rooms.size() - 1) + 1).addEntity(switchEntity);
        }
        // Spawn Items
        int numItems = (rooms.size() <= 14) ? rng.nextInt(2) + 1 : rng.nextInt(3) + 1;
        List<Stat> list = Arrays.asList(Stat.values());
        for (int i = 0; i < numItems; i++) {
            Collections.shuffle(list);
            double d = rng.nextDouble();
            if (d <= 0.5) { // Common
                Item item = new Item(rng.nextDouble() * (LDJam43.SCREEN_WIDTH - Item.ITEM_SIZE), rng.nextDouble() * (LDJam43.SCREEN_HEIGHT - Item.ITEM_SIZE), Color.GRAY);
                item.addStatChange(list.get(0), rng.nextInt(2) + 1);
                item.addStatChange(list.get(1), -(rng.nextInt(2) + 1));
                rooms.get(rng.nextInt(rooms.size() - 1) + 1).addEntity(item);
            } else if (d <= 0.8) { // Uncommon
                Item item = new Item(rng.nextDouble() * (LDJam43.SCREEN_WIDTH - Item.ITEM_SIZE), rng.nextDouble() * (LDJam43.SCREEN_HEIGHT - Item.ITEM_SIZE), Color.LIMEGREEN);
                item.addStatChange(list.get(0), rng.nextInt(3) + 1);
                item.addStatChange(list.get(1), rng.nextInt(3) + 1);
                item.addStatChange(list.get(2), -(rng.nextInt(3) + 1));
                item.addStatChange(list.get(3), -(rng.nextInt(3) + 1));
                rooms.get(rng.nextInt(rooms.size() - 1) + 1).addEntity(item);
            } else if (d <= 0.95) { // Rare
                Item item = new Item(rng.nextDouble() * (LDJam43.SCREEN_WIDTH - Item.ITEM_SIZE), rng.nextDouble() * (LDJam43.SCREEN_HEIGHT - Item.ITEM_SIZE), Color.BLUE);
                item.addStatChange(list.get(0), rng.nextInt(4) + 1);
                item.addStatChange(list.get(1), rng.nextInt(4) + 1);
                item.addStatChange(list.get(2), -(rng.nextInt(4) + 1));
                rooms.get(rng.nextInt(rooms.size() - 1) + 1).addEntity(item);
            } else { // Epic
                Item item = new Item(rng.nextDouble() * (LDJam43.SCREEN_WIDTH - Item.ITEM_SIZE), rng.nextDouble() * (LDJam43.SCREEN_HEIGHT - Item.ITEM_SIZE), Color.PURPLE);
                item.addStatChange(list.get(0), rng.nextInt(5) + 1);
                item.addStatChange(list.get(1), rng.nextInt(5) + 1);
                item.addStatChange(list.get(2), rng.nextInt(5) + 1);
                item.addStatChange(list.get(3), -(rng.nextInt(5) + 1));
                rooms.get(rng.nextInt(rooms.size() - 1) + 1).addEntity(item);
            }
        }
        rooms.get(0).addEntity(ladder = new Ladder());
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