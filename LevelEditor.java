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
    super(values, fileOutput);
    this.values = values;
    makeCells(true);
    addMissingWalls();
    init();
  }

  public void act() {
    createGrid();
    getBackground().setColor(Color.WHITE);
    getBackground().fill();
    drawCells();
  }

  private void init() {
    canGenGrid = false;
    this.values = values;
    getButtonVar();
    addObject(new Pointer(), 0, 0);
    difficulty = 4;
    for (Cell grid: grid) {
      grid.show();
    }
    for (Wall wall: world.getObjects(Wall.class)) {
      wall.makeTransparnet();
    }
  }

  private void addMissingWalls() {
    for (Cell grid: grid) {
      for (int i = 0; i < grid.Walls.length; ++i) {
        if (grid.Walls[i] == false) {
          grid.addWall(i);
        }
      }
    }
  }

}