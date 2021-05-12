import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.io.*;

public class Cell extends Actor implements Serializable {
    public int i, j;
    public int x, y;
    public int size;
    public boolean Walls[] = new boolean[4]; // top, right, bottom, left
    private MyWorld world = ((MyWorld) getWorld());
    public boolean visited = false;
    public boolean middle;
    private boolean checkedWalls = false;
    public Stack < Cell > neighbors = new Stack < Cell > ();
    public int f, h, g;
    public Cell previous = null; // Each Cell contains a preivous cell in its path, which contains a preivous cell and so on. 
    //Boolean wall;
    Cell(int i, int j) {
        this.i = i;
        this.j = j;
        init();
        Arrays.fill(Walls, Boolean.TRUE);
    }

    Cell(int i, int j, boolean[] walls) {
        this.i = i;
        this.j = j;
        init();
        Walls = walls;
    }

    private void init() {
        world = (MyWorld) MyWorld.world;
        size = world.size;
        x = i * size;
        y = j * size;
    }

    public void show() {
        if (middle) { // Mark goal as green and remove all walls surrounding it, allows for more path options to get to goal
            showNow(Color.GREEN);
            checkWalls();
        }
        // Check if wall should be drawn, if side value is true
        for (int i = 0; i < Walls.length; ++i) {
            if (Walls[i]) addWall(i);
        }

    }

    public void addWall(int wallNumber) {
        switch (wallNumber) {
            case 0: // top
                world.addObject(new Wall(size, false, this, 0), this.x + size / 2, this.y);
                break;
            case 1: // right
                world.addObject(new Wall(size, true, this, 1), this.x + size, this.y + size / 2); 
                break;
            case 2: // bottom
                world.addObject(new Wall(size, false, this, 2), this.x + size / 2, this.y + size); 
                break;
            case 3: // left
                world.addObject(new Wall(size, true, this, 3), this.x, this.y + size / 2);
                break;
        }
    }

    public void simulateLines() {
        world.getBackground().setColor(Color.BLACK);
        int[] locations = new int[] { // pre load offsets, faster to do it in a for loop rather than if statements
                x,
                y,
                x + size,
                y,
                x + size,
                y,
                x + size,
                y + size,
                x + size,
                y + size,
                x,
                y + size,
                x,
                y + size,
                x,
                y
            };
        for (int i = 0; i < Walls.length; i++) {
            int offset = i * 4;
            //if(world.getClass().equals(LevelEditor.class)){
            if (Walls[i]) {
                world.getBackground().drawLine(locations[offset], locations[offset + 1], locations[offset + 2], locations[offset + 3]);
            }
        }
        //Check if wall should be drawn, if side value is true
    }

    public void showNow(Color color) {
        world.getBackground().setColor(color);
        world.getBackground().fillRect(x + 2, y + 2, size - 2, size - 2);
    }

    public void checkWalls() {
        if (!checkedWalls) {
            removeMiddleWalls();
            setStartSpace();
            checkedWalls = true;
        }
    }

    public int getNumber() { // stacks are like backwards arrays, so we want to flip its contents to display the best path 
        int number = (world.bestPath.indexOf(this) - world.bestPath.size()) * -1 - 1; // flip values of an array
        return number;
    }

    private void setStartSpace() {
        if (world.getClass().equals(MyWorld.class)) {
            for (int i = 0; i < 2; i++) {
                world.grid.get(i).removeAllWalls();
                world.grid.get(i + world.cols).removeAllWalls();
            }
        }
    }

    private void removeAllWalls() {
        Arrays.fill(Walls, Boolean.FALSE);
    }

    private int getIndex(int i, int j) {
        if (i < 0 || j < 0 || i > world.cols - 1 || j > world.rows - 1) return -1; // Return null if is not on the gird eg. searching edge of world there is no grid beside it
        return i + j * world.cols; // Turn multi dimensional array into single, formula found online
    }

    private void removeMiddleWalls() {
        removeAllWalls(); // get middle cell and remove walls around
        // Need to remove neighbouring walls aswell in slim chance it will generate a wall for it instead. Override 
        world.grid.get(world.middleIndex - world.cols).Walls[2] = false; // to the top cell, remove bottom wall
        world.grid.get(world.middleIndex + world.cols).Walls[0] = false; // to the bottom cell, remove top wall
        world.grid.get(world.middleIndex - 1).Walls[1] = false; // to the left cell, remove right wall
        world.grid.get(world.middleIndex + 1).Walls[3] = false; // to the right cell, remove left wall
    }
    // Look for walls at each direction
    public boolean checkTop() {
        if (world.grid.get(world.grid.indexOf(this) - world.cols).Walls[2] == true) { // find location of each neighbor
            return true;
        }
        return false;
    }

    public boolean checkBelow() {
        if (world.grid.get(world.grid.indexOf(this) + world.cols).Walls[0] == true) { // find location of each neighbor
            return true;
        }
        return false;
    }

    public boolean checkRight() {
        if (world.grid.get(world.grid.indexOf(this) - 1).Walls[1] == true) { // find location of each neighbor
            return true;
        }
        return false;
    }

    public boolean checkLeft() {
        if (world.grid.get(world.grid.indexOf(this) + 1).Walls[3] == true) { // find location of each neighbor
            return true;
        }
        return false;
    }

    // Get the cells artound current cell that is unvisited
    public Cell checkNeighbors() {
        ArrayList < Cell > neighborsList = new ArrayList < Cell > ();
        Cell top;
        Cell right;
        Cell bottom;
        Cell left;
        // Avoid java being weird about returning -1 as null
        if (getIndex(i, j - 1) != -1) { // find location of each neighbor
            top = world.grid.get(getIndex(i, j - 1));
        } else top = null;
        if (getIndex(i + 1, j) != -1) {
            right = world.grid.get(getIndex(i + 1, j));
        } else right = null;
        if (getIndex(i, j + 1) != -1) {
            bottom = world.grid.get(getIndex(i, j + 1));
        } else bottom = null;
        if (getIndex(i - 1, j) != -1) {
            left = world.grid.get(getIndex(i - 1, j));
        } else left = null;
        // If it actually exists and hasn't been visted add it to the possible options of neighbors
        if (top != null && !top.visited) neighborsList.add(top);
        if (right != null && !right.visited) neighborsList.add(right);
        if (bottom != null && !bottom.visited) neighborsList.add(bottom);
        if (left != null && !left.visited) neighborsList.add(left);
        // Pick random nieghbour, if there is any unvisted ones
        if (neighborsList.size() > 0) {
            Random r = new Random();
            return neighborsList.get(Greenfoot.getRandomNumber(neighborsList.size()));
        } else {
            return null; // must reutrn something if no neighbours, start backtrack if null
        }
    }

    public void addNeighborsForGeneration() {
        // pretty much just checking if there is an avaibale cell next to each space of this cell
        // should be using neighbors.push(world.grid.get((i+world.cols)%world.cols); etc...
        if (i < world.cols - 1) {
            neighbors.push(world.grid.get((getIndex(i + 1, j))));
        }
        if (i > 0) {
            neighbors.push(world.grid.get((getIndex(i - 1, j))));
        }
        if (j < world.cols - 1) {
            neighbors.push(world.grid.get((getIndex(i, j + 1))));
        }
        if (j > 0) {
            neighbors.push(world.grid.get((getIndex(i, j - 1))));
        }
    }

}