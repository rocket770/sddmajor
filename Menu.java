import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * Write a description of class Menu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Menu extends World {
    public static List<Value> values = new ArrayList<Value>();
    public Menu() {
        super(600, 600, 1);
        init();
        if(values.isEmpty()) populateEmptyVals();
    }

    public void act() {
        getBackground().setColor(Color.WHITE);
        getBackground().fill();
        GreenfootImage img = new GreenfootImage("TitleScreen.gif");
        setBackground(img);
        getBackground().setColor(new Color(128,128,128));
        getBackground().fillRect(127, 100, 337, 450);
    }

    private void init() {
        Greenfoot.start();
        Greenfoot.setSpeed(100);
        generateButtons();
        addObject(new Title(), getWidth()/2, 125);
        System.out.println("\n");
    }

    private void generateButtons() {
        Button switchWorld = new Button(this, Color.ORANGE, 232, 200, "Enter World", "switchWorld", new Color(128, 128, 128), 45);
        addObject(switchWorld,0,0);
        Button customEditor = new Button(this, Color.ORANGE, 232, 270, "Custom Level", "Util", new Color(128, 128, 128), 45);
        addObject(customEditor, 0,0);
        Button settings = new Button(this, Color.ORANGE, 232, 340, "Settings", "Util", new Color(128, 128, 128), 45);
        addObject(settings, 0,0);
        Button help = new Button(this, Color.ORANGE, 232, 410, "Help", "Util", new Color(128, 128, 128), 45);
        addObject(help, 0,0);
    }

    public Menu(List<Value> values) {
        super(600, 600, 1);
        this.values = values;         
        init();
    }

    public void populateEmptyVals() {
        for(int i = 0; i < 4; ++i){
            Value v = new Value();
            switch(i){
                case 0: 
                v.setID("Population");
                v.setValue(50);
                break;
                case 1: 
                v.setID("Speed");
                v.setValue(25);
                break;
                case 2: 
                v.setID("difficultySlider");
                v.setValue(1);
                break;  
                case 3: 
                v.setID("Map Size");
                v.setValue(25);
                break; 

            }
            values.add(v);
        }
    }

}