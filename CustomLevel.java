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
        levelEditor = new Button(this, Color.PINK, 300,500, "New Map", "levelEditor",new Color(128,128,128), 45); 
        addObject(levelEditor,0,0);
        Button importMap = new Button(this, Color.YELLOW, 250,200, "Import Map", "Var", null, 45);   
        addObject(importMap,0,0);
        Button loadMap = new Button(this, Color.YELLOW, 250,300, "Load Map", "Var", null, 45);   
        addObject(loadMap,0,0);
    }
}