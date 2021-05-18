import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World {
    /**
     * Constructor for objects of class MyWorld.
     * 
     *
     **/
    // File loading
    private boolean[] wallArray;
    private boolean[] Walls;
    protected boolean canGenGrid = true;
    private int cooldown = 0;
    private boolean hasAsked = false;
    private String name = "";

    // Constants for settings
    public static final int MAX_MAZE_SCALE = 100;
    public static final int MIN_MAZE_SCALE = 25;
    public static final int MAX_POP_SIZE = 2500;
    public static final int MIN_POP_SIZE = 50;
    public static final int MAX_SPEED = 100;
    public static final int MIN_SPEED = 20;

    //Genetic Evolution
    private int population;
    protected int speed;
    protected List <Value> values;
    private int gx, gy;
    public static MyWorld world;
    public Network network;

    // Reccursive backtracker + Depth first search
    private boolean finishedDrawing = false;
    private boolean createdPop = false;
    private float finishedSum = 0.0f;
    public int middleIndex;
    private Cell currentCell;
    public ArrayList < Cell > grid = new ArrayList < Cell > (); // dynamic sized array
    private ArrayList < Cell > neighborsSearched = new ArrayList < Cell > ();
    private boolean startGen = false;
    public int cols, rows;
    public int size;
    private Text genFinishedText = new Text("");
    private boolean foundMiddle = false;

    // A* Algortihm
    private Cell start;
    private Cell end;
    private Stack < Cell > open = new Stack < Cell > (); // The cells that have been visited by the BackTracker but have not been explored as a viable path
    private Stack < Cell > closed = new Stack < Cell > (); // explored cells in a given path 
    private boolean found = false;
    public Stack < Cell > bestPath;

    // Button
    private Button info;
    private Button saveMap;
    private Button showingRunner;
    private Button exitOption;
    public Button settings;
    public boolean showingBest;
    public int difficulty; 
    private Text[] pathNumbers;
    public float mutationRate;

    // DONT EDIT CODE WHILE A THREAD MAY BE RUNNING (For Mr. Young btw)
    // DONT EDIT CODE WHILE A THREAD MAY BE RUNNING (For Mr. Young btw) 
    // DONT EDIT CODE WHILE A THREAD MAY BE RUNNING (For Mr. Young btw) 
    // A thread always will run in the background but resume code when this world is active. If you do, it may cause a runtime error or decreased FPS, just restart the program!

    public MyWorld(List values) {
        // Create a new world with 600x600 cells with a cell size of 1x1 pixels.
        super(600, 600, 1);
        world = this;
        this.values = values;
        getButtonVar();
        initMap(false, true);

    }

    public MyWorld(List values, boolean[] Walls) {
        // Create a new world with 600x600 cells with a cell size of 1x1 pixels.
        super(600, 600, 1);
        canGenGrid = false;
        world = this;
        this.Walls = Walls;
        this.values = values;
        getButtonVar();
        difficulty = 4;
        initMap(true, true);
    }

    public MyWorld(List values, int x, int y) {
        // Create a new world with 600x600 cells with a cell size of 1x1 pixels.
        super(x, y, 1);
        world = this;
        this.values = values;
        getButtonVar();
        initMap(false, false);
    }

    public void act() {
        createGrid();
        try { // print an error if the maze cant be solved, yeah its annoying having the try catch in a mainline but i dont wanna nest the method. Sorry sir.
            makeAStar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        enableAI();
        //Thread.getAllStackTraces().keySet().forEach((t) -> System.out.println(t.getName() + "\nIs Daemon " + t.isDaemon() + "\nIs Alive " + t.isAlive()));
    }

    private void initMap(boolean preLoad, boolean solveMap) { // basic methods that would fit in each constructer, combined into a single method to clean up code
        cols = getWidth() / size;
        rows = getHeight() / size;
        addObject(genFinishedText, getWidth()/2, 100);
        setPaintOrder(Button.class, getBackground().getClass(), Text.class, Overlay.class, CollisionRayCast.class, Wall.class, mazeRunner.class);
        makeCells(preLoad);
        if (solveMap) {
            currentCell = grid.get(0);
            start = grid.get(0);
        }
        Greenfoot.setSpeed(100);
    }

    protected void saveMap() {
        if (!hasAsked) {
            String s = String.valueOf(size); // Name it based on size value of the world
            name = Greenfoot.ask("Enter Output file Name: ");
        }
        try (FileOutputStream out = new FileOutputStream(new File("./saves/" + name + "." + size + "maze")); BufferedOutputStream outputStreamWriter = new BufferedOutputStream(out)) // More effecient than a regular file output stream
        {
            for (int i = 0; i < grid.size(); i++) { // For each grid, the wall array contains 4 values, so we need to loop through all and mark them in ascending order
                for (int j = 0; j < 4; j++) {
                    outputStreamWriter.write(grid.get(i).Walls[j] ? 1 : 0); // If the wall position is marked as true, write a 1, else 0
                }
            }
            System.out.println("Saved!");
            outputStreamWriter.close(); // Close the file so if it is re-written, it will override it and not create an extra long maze
        } catch (java.io.FileNotFoundException e) { // if cant find save location
            File file = new File("./saves/"); // make new one
            hasAsked = true; // dont repeat
            //Creating the directory
            boolean made = file.mkdirs(); // return true if this worked
            if (made) {
                saveMap();
                System.out.println("Error, can't find './saves/' directory, making one and trying again...");
            } else {
                System.out.println("Sorry! couldnt create the directory. Try again or make one manunally by creating a folder named 'saves' in this programs' folder. ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        hasAsked = false;
    }

    private void enableAI() {
        if (finishedDrawing && found) {
            if (!createdPop) {
                network = new Network(population);
                for(Cell grid: grid) grid.show();
                addObject(new Wall(600, false, null, -1), 300, 0); // borders
                addObject(new Wall(600, false, null, -1), 300, 599);
                addObject(new Wall(600, true, null, -1), 599, 300);
                addObject(new Wall(600, true, null, -1), 0, 300);
                removeObject(genFinishedText);
                createdPop = true;      
            }
            controllPop();

        }
    }

    private float getPercentageGen() {
        for (int i = 0; i < grid.size(); i++) {
            if (grid.get(i).visited) finishedSum += 1;
        }
        return (float)((finishedSum / grid.size()) * 50.0f); //  *100  /2
    }

    protected void getButtonVar() { // export the values from the list we importanded
        for (Value v: values) {
            float val = v.getValue(); // get thier value
            switch (v.getID()) {
                case "Population":
                population = (int)val;
                break;
                case "Map Size":
                size = (int)val;
                break;
                case "Speed":
                speed = (int)val;
                break;
                case "difficultySlider":
                difficulty = (int)val;
                break;
                case "mutationSlider": 
                mutationRate = val;
                break;
            }
        }
        // values.forEach(v -> 
        // System.out.println(v.getID() + " val: " + v.getValue())
        // );

    }

    private void controllPop() {
        // new Thread(
        // new Runnable(){
        // public void run(){
        // while(true) {
        // if (network.allmazeRunnersDead()) {
        // //genetic algorithm
        // network.calculateAllFitnesses();
        // network.naturalSelection();
        // network.mutation();
        // } else if (!settings.pause) {
        // //if any of the dots are still alive then update and then show them
        // network.checkOverlap();
        // network.act();
        // network.show();
        // }
        // }
        // }
        // }).start();

        if (network.allmazeRunnersDead()) {
            //genetic algorithm
            network.calculateAllFitnesses();
            network.naturalSelection();
            network.mutation();
        } else if (!settings.pause) {
            //if any of the dots are still alive then update and then show them
            network.checkOverlap();
            network.act();
            network.show();
        }
    }

    public void toggleBestPath() {
        for (int i = 1; i <pathNumbers.length; i++) {            
            pathNumbers[i].toggle();
        }
    }

    // found this from a youtube video and modified it, gets the difference of the positions of the walls
    private void removeWalls(Cell a, Cell b) {
        //x components
        switch ((a.i - b.i)) {
            case 1: // if the difference is 1, we have a neighbour to the left 
            a.Walls[3] = false; // Remove both walls as there are 2 walls from the neighbour and the currentin the same position
            b.Walls[1] = false;
            case -1: // if difference is -1, it means we have a neghbour to the right
            a.Walls[1] = false;
            b.Walls[3] = false;
        }
        // find walls for y
        switch ((a.j - b.j)) {
            case 1: // if the difference is 1, we have a neighbour to the top 
            a.Walls[0] = false; // top 
            b.Walls[2] = false; // bottom
            case -1: // if difference is -1, it means we have a neghbour to the bottom
            a.Walls[2] = false;
            b.Walls[0] = false;
        }
    }

    //I did it weirdly like this purposely because this makeCells method gets called through a few different functions and it would be alot weirder as I would need to use an 
    // if statement in each many times to check the boolean preLoad and then chose which method to call. 
    protected void makeCells(boolean preLoad) {
        if (preLoad) {
            loadInputtedMap();
        } else {
            drawNewMap();
        }
    }

    protected void drawNewMap(){
        for (int j = 0; j < rows; j++) { //y
            for (int i = 0; i < cols; i++) { //x
                Cell cell = new Cell(i, j); // make square
                grid.add(cell); // add to grid list
            }
        }
    }

    protected void loadInputtedMap() {
        int index = 0;
        for (int j = 0; j < rows; j++) { //y
            for (int i = 0; i < cols; i++) { //x
                wallArray = new boolean[4]; // Create a new array of the next 4 processed bytes
                for (int y = 0; y < wallArray.length; y++) {
                    wallArray[y] = Walls[y + index];
                }
                Cell cell = new Cell(i, j, wallArray); // make square with the new array passed into it
                grid.add(cell); // add to grid list
                index += wallArray.length; // since we saved our walls in order, every 4th byte marks a new Cell
            }
        }
    }
    
     private void makeEasy() {
        for (Cell cell: grid) {
            int randNumber = Greenfoot.getRandomNumber(3); // the lower the difficulty, the higher chance to remove a random wall
            int choice = Greenfoot.getRandomNumber(difficulty);
            if (choice == 0) {
                cell.Walls[randNumber] = false; // although its possible that we may remove an already 'false' wall, thats okay otherwise there would be to big of a change
                try {
                    switch (randNumber) { // get neigboruing cells and remove duplicate wall
                        case 0:
                        grid.get(grid.indexOf(cell) - size).Walls[2] = false;
                        case 1:
                        grid.get(grid.indexOf(cell) + 1).Walls[3] = false;
                        case 2:
                        grid.get(grid.indexOf(cell) + size).Walls[0] = false;
                        case 3:
                        grid.get(grid.indexOf(cell) - 1).Walls[1] = false;
                    }
                } catch (Exception e) {
                    continue; // skip edges
                }
            }
        }
    }

    private void makeButtons() {
        info = new Button(this, new Color(128, 128, 128), 200, 560, "Info", 30, 18);
        addObject(info, 0, 0);

        saveMap = new Button(this, new Color(128, 128, 128), 480, 560, "Save Map", 30, 18);
        addObject(saveMap, 0, 0);

        exitOption = new Button(this, new Color(128, 128, 128), 20, 560, "Exit", 30, 18);
        addObject(exitOption, 0, 0);

        settings = new Button(this, new Color(128, 128, 128), 300, 560, "Settings", 30, 18);
        addObject(settings, 0, 0);
    }

    protected void createGrid() {   //bfs - recursive backtrack starter
        if (neighborsSearched.size() != 0 || !startGen && canGenGrid) {
            initalizeGridSpot();
            // Step 1, Pick random neighbour, mark as visited
            Cell neighbor = currentCell.checkNeighbors();
            if (neighbor != null) {
                neighbor.visited = true;
                // Step 2 add current cell to stack of tracked cells
                neighborsSearched.add(currentCell);
                // Step 3 Remove the walls of conjoining cells
                removeWalls(currentCell, neighbor);
                // Step 4, set to next neighbor position to restart the loop
                currentCell = neighbor;
            } else if (neighborsSearched.size() > 0) { // If the track has run out of cells or come to a dead end, start from the previous spot and search (backtrack movement)
                currentCell = neighborsSearched.remove((neighborsSearched.size()) - 1);
            }
            drawCells();
        } else if (!finishedDrawing) {
            if (difficulty != 4) {
                makeEasy();
            }
            Greenfoot.setSpeed(speed);
            addNeighbour();
            drawCells(); // called again to preload cells for A*
            addObject(new FramesPerSecond(), getWidth() - 70, 30);
            makeButtons();
            finishedDrawing = true;
        }
    }

   

    private void initalizeGridSpot() {
        startGen = true;
        currentCell.visited = true;
        getBackground().setColor(Color.RED);
        String genFinished = Math.round(getPercentageGen()) - 1 > 1 ? "Generating Map: " + (Math.round(getPercentageGen()) - 1) + "%..." : "Generating Map: " + (Math.round(getPercentageGen())) + "%..."; // if statement to never show a negative number in generation
        genFinishedText.setText(genFinished);
        finishedSum = 0;
    }

    public void drawCells() {
        for (int i = 0; i < grid.size(); i++) {
            if (neighborsSearched.size() != 0) {
                currentCell.showNow(Color.PINK);
            }
            grid.get(i).simulateLines();
            if (!foundMiddle && grid.get(i).x == getWidth() / 2 && grid.get(i).y == getHeight() / 2) {
                grid.get(i).middle = true;
                foundMiddle = true;
                middleIndex = i;
                end = grid.get(i);
                gx = grid.get(i).x;
                gy = grid.get(i).y;
                grid.get(i).show();
            } else if(grid.get(i).middle) {
              grid.get(i).showNow(Color.GREEN); 
            }
        }
    }

    // begin A*
    private void addNeighbour() {
        for (int i = 0; i < grid.size(); i++) {
            grid.get(i).addNeighborsForGeneration();
        }
    }

    private void makeAStar() throws Exception { // I've constructed this algorithm from http://mat.uab.cat/~alseda/MasterOpt/AStar-Algorithm.pdf - an A* pathfinding research paper contianing its pseudocode
        if (finishedDrawing && !found) {
            open.push(start); // put the node in the start of the list where the function of f = h (0)
            long startTime = System.nanoTime(); // Mark the current systems time
            while (open.size() > 0) {
                Cell current = open.get(getLowestFScore()); // Take from the open list the node node_current with the lowest
                if (current == end) { // if node_current is node_goal we have found the solution
                    getFoundPath(current);
                    System.out.println("Found best Path in " + (System.nanoTime() - startTime) * 0.000001 + " Milliseconds"); // gets the difference in time since last mark and convert it from nanoseconds to milliseconds 
                }
                open.remove(open.indexOf(current));
                closed.push(current);
                Stack < Cell > neighbours = current.neighbors;
                evelauteNeighbours(neighbours, current);
                //testDrawMap();
            } // becuase of the nature of our recursive backtracker thier is ALWAYS a solution
        }
        if (!found && finishedDrawing) { // if we get to this point a second time during execution, there is no possible solveas
            Greenfoot.setWorld(new Menu());
            throw new Exception("Error, cant find a solution...\nReturning to the menu...");
        }
    }

    private int getLowestFScore() {
        int lowest = 0;
        for (int i = 0; i < open.size(); i++) {
            if (open.get(i).f < open.get(lowest).f) {
                lowest = i;
            }
        }
        return lowest;
    }

    private void getFoundPath(Cell current) {
        bestPath = new Stack < Cell > (); // save path
        Cell pathCell = current;
        bestPath.push(pathCell);
        while (pathCell.previous != null) { // backtrack on previousvisited cells
            bestPath.push(pathCell.previous);
            pathCell = pathCell.previous;
        }
        drawBest();
        found = true;
    }

    private void evelauteNeighbours(Stack < Cell > neighbours, Cell current) {
        for (int i = 0; i < neighbours.size(); i++) {
            Cell neighbour = neighbours.get(i);
            if (!closed.contains(neighbour) && !obstacleBetween(neighbour, current)) { // Check if there is a wall between the cells 
                int newG = current.g + 1;
                if (open.contains(neighbour)) { // see if we have gotten thier faster previously
                    if (newG < neighbour.g) { // if g(node_successor) ≤ successor_current_cost continue
                        neighbour.g = newG; // Set g(node_successor) = successor_current_cost
                    }
                } else {
                    neighbour.g = newG;
                    open.push(neighbour);
                }
                neighbour.h = getHeuristic(neighbour, end);
                neighbour.f = neighbour.g + neighbour.h; //  Set successor_current_cost = g(node_current) + w(node_current, node_successor)
                neighbour.previous = current;
            }
        }
    }

    private void drawBest() {
        pathNumbers = new Text[bestPath.size()];
        for (int i = 1; i < bestPath.size(); i++) {
            pathNumbers[i] = new Text("" + bestPath.get(i).getNumber());
            addObject(pathNumbers[i], bestPath.get(i).x + size / 2, bestPath.get(i).y + size / 2);
        }
        drawCells();
    }

    private boolean obstacleBetween(Cell neighbour, Cell current) {
        try {
            if (grid.indexOf(current) == grid.indexOf(neighbour) + cols && (neighbour.checkBelow() || current.checkTop())) {
                return true;
            }
            if (grid.indexOf(current) == grid.indexOf(neighbour) + 1 && (neighbour.checkLeft() || current.checkRight())) {
                return true;
            }
            if (grid.indexOf(current) == grid.indexOf(neighbour) - cols && (neighbour.checkTop() || current.checkBelow())) {
                return true;
            }
            if (grid.indexOf(current) == grid.indexOf(neighbour) - 1 && (neighbour.checkRight() || current.checkLeft())) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    // void testDrawMap(){
    // getBackground().clear();

    // for(int i = 0; i < grid.size(); i++){
    // grid.get(i).show();
    // }
    // for(int i = 0; i < closed.size(); i++){
    // closed.get(i).showNow(Color.YELLOW);
    // }
    // for(int i = 0; i < open.size(); i++){
    // open.get(i).showNow(Color.BLUE);
    // }
    // //for(int i = 0; i < bestPath.size(); i++){
    // //bestPath.get(i).showNow(Color.RED);
    // //}
    // }
    // It is human goal to travel to the closest point of the end of the maze - https://www.lesswrong.com/posts/CPBmbgYZpsGqkiz2R/problem-solving-with-mazes-and-crayon 
    private int getHeuristic(Cell neighbour, Cell endPoint) { // Manhattan distancne formula
        double distance = Math.abs(neighbour.x - endPoint.x) + Math.abs(neighbour.y - endPoint.y);
        return (int) 1 / ((int) distance + 1);
    }

}