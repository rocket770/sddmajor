import greenfoot.*;
import java.util.List;
/**
 * Write a description of class LevelEditor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LevelEditor extends MyWorld 
{
    /**
     * Constructor for objects of class LevelEditor
     */
    public LevelEditor(List values)
    {    
        super(values, 600, 600);
        canGenGrid = false;
        this.values = values;
        makeCells(false);
        addObject(new FramesPerSecond(),getWidth()-50, 30);
        getButtonVar();
        addObject(new Pointer(),0,0);
        difficulty = 4;
        for(Cell grid: grid) grid.show();
        for(Wall wall: world.getObjects(Wall.class)){
            wall.makeTransparnet();
        }   
        addObject(new Overlay(),getWidth()/2,getHeight()/2);
    }

    public LevelEditor(List values, boolean[] fileOutput)
    {    
        super(values,fileOutput);
        canGenGrid = false;
        this.values = values;
        makeCells(true);
        addObject(new FramesPerSecond(),getWidth()-50, 30);
        getButtonVar();
        addObject(new Pointer(),0,0);
        difficulty = 4;
        addMissingWalls();
        for(Cell grid: grid) grid.show();
        for(Wall wall: world.getObjects(Wall.class)){
            wall.makeTransparnet();
        }
        addObject(new Overlay(),getWidth()/2,getHeight()/2);
    }

    public void act(){
        createGrid();
        getBackground().setColor(Color.WHITE);
        getBackground().fill();
        drawCells();
    }

    private void addMissingWalls(){
        for(Cell grid: grid){
            for(int i = 0; i<grid.Walls.length; ++i){
                if(grid.Walls[i] == false){
                    switch(i){
                        case 0: // top
                        world.addObject(new Wall(size, false, grid, 0), grid.x+size/2,grid.y);

                        case 1: // right
                        world.addObject(new Wall(size, true, grid, 1), grid.x+size,grid.y+size/2); // checked

                        case 2: // bottom
                        world.addObject(new Wall(size, false, grid, 2), grid.x+size/2,grid.y+size);      //checked

                        case 3: // left
                        world.addObject(new Wall(size, true, grid, 3), grid.x,grid.y+size/2);

                    }
                }
            }
        }
    }

}
