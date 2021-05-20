import greenfoot.*;
import java.util.List;
/**
 * Write a description of class LevelEditor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LevelEditor extends MyWorld {
    /**
     * Constructor for objects of class LevelEditor
     */
    public LevelEditor(List values) {
        super(values, 600, 600);
        init();
        makeCells(false);
    }

    public LevelEditor(List values, boolean[] fileOutput) {
        super(values, fileOutput); // load file output into super class constuctor
        this.values = values; // load world settings in super's values
        makeCells(true); // call make cells function
        addMissingWalls(); // when a map is loaded, place the empty walls with a transparent wall one
        init();
    }

    public void act() {
        createGrid();
        getBackground().setColor(Color.WHITE); // refresh the background every frame
        getBackground().fill();
        drawCells();
    }

    private void init() {
        canGenGrid = false;
        this.values = values;
        getButtonVar();
        addObject(new Pointer(), 0, 0); // add mouse pointer that checks wall collisions
        difficulty = 4; // set maze diffucilty to 4 (max) so no walls are radnomly removed
        for (Cell grid: grid) {
            grid.show(); // add all background lines
        }
        for (Wall wall: world.getObjects(Wall.class)) {
           wall.makeTransparnet(); // make then go slightly see through when clicked
        }
    }

    private void addMissingWalls() {
        for (Cell grid: grid) { // for every grid
            for (int i = 0; i < grid.Walls.length; ++i) { // for every grid's walls
                if (grid.Walls[i] == false) { // if there is no wall, add a transparent wall at that position
                    grid.addWall(i);
                }
            }
        }
    }

}