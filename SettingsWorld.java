import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SettingsWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SettingsWorld extends World
{
    Button[] buttons = new Button[5];
    /**
     * Constructor for objects of class SettingsWorld.
     * 
     */
    public SettingsWorld()
    {// Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 600, 1);
        Button pop = new Button(this, Color.BLUE, 50, 200, "Population", "Var", 350, 50);
        buttons[0] = pop;
        Button map = new Button(this, Color.RED, 225, 200, "Map Size", "Var", 75, 25);
        buttons[1] = map;
        Button Speed = new Button(this, Color.GREEN, 400, 200, "Speed", "Var", 100, 20);
        buttons[2] = Speed;
        Button setReccomended = new Button(this, new Color(128, 128, 128), 150, 520, "Set Recc", 45, 24);
        buttons[3] = setReccomended;
        Button Exit = new Button(this, Color.YELLOW, 400,100, "Back", "Util", null, 45);   
        buttons[4] = Exit;
         for (int i = 0; i < buttons.length; i++) {
            addObject(buttons[i], 0, 0);
        }
        
        addSliders();
    }
    
     private void addSliders() {
        Slider popSlider = new Slider(50, 2500, 150, this, "Population", new Color(100, 100, 255), 50, "popSlider", true);
        addObject(popSlider, 50, 300);
        Slider speedSlider = new Slider(20, 100, 150, this, "Speed", new Color(100, 255, 100), 400, "speedSlider", true);
        addObject(speedSlider, 400, 300);
        Slider difficultySlider = new Slider(1, 4, 150, this, "Difficulty", new Color(100, 255, 100), 400, "difficultySlider", false, 2);
        addObject(difficultySlider, 300, 400);
        Slider mutationSlider = new Slider(0, 1, 150, this, "MutationRate", new Color(100, 255, 100), 400, "mutationSlider", false, 2);
        addObject(mutationSlider, 100, 400);
    }
}
