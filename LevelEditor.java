import greenfoot.*;
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
    public LevelEditor(Button[] buttons)
    {    
        super(buttons, 600, 600);
        canGenGrid = false;
        this.buttons = buttons;
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

    public void act(){
        createGrid();
        getBackground().setColor(Color.WHITE);
        getBackground().fill();
        drawCells();
    }

}
