import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class CustomLevel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CustomLevel extends World
{
    public List<Value> values;
    public static Text fileText;
    public Button levelEditor; // this is the only button that needs to be acessed from another class
    /**
     * Constructor for objects of class CustomLevel.
     * 
     */
    public CustomLevel(List<Value> values)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 600, 1);
        this.values = values;
        addObject(new Title("CustomLevelTitle.gif"), getWidth()/2, 125);
        Button importMap = new Button(this, Color.ORANGE, 232,200, "Import Map", "Util", null, 45);   
        addObject(importMap,0,0);
        fileText = new Text("Selected File: Null", 22);
        addObject(fileText,  295, 290);
        Button loadMap = new Button(this, Color.ORANGE, 232,340, "Load Map", "Util", null, 45);   
        addObject(loadMap,0,0);
        levelEditor = new Button(this, Color.ORANGE, 232,410, "New Map", "Util",new Color(128,128,128), 45); 
        addObject(levelEditor,0,0);
        Button Exit = new Button(this, Color.ORANGE, 12,552, "Back", "Util", null, 45);   
        addObject(Exit,0,0);
    }

    public void act() {
        getBackground().setColor(Color.WHITE);
        getBackground().fill();
        GreenfootImage img = new GreenfootImage("TitleScreen.gif");
        setBackground(img);
        getBackground().setColor(new Color(128,128,128));
        getBackground().fillRect(127, 100, 337, 450);   
    }
}
