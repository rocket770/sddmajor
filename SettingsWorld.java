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
        Button pop = new Button(this, Color.BLUE, 150, 100, "Population", "Var", 350, 50);
        buttons[0] = pop;
        Button map = new Button(this, Color.RED, 150, 200, "Map Size", "Var", 75, 25);
        buttons[1] = map;
        Button Speed = new Button(this, Color.GREEN, 150, 300, "Speed", "Var", 100, 20);
        buttons[2] = Speed;
        Button setReccomended = new Button(this, new Color(128, 128, 128), 400, 520, "Set Recc", 45, 24);
        buttons[3] = setReccomended;
        Button Exit = new Button(this, Color.YELLOW, 150,520, "Back", "Util", null, 45);   
        buttons[4] = Exit;
        for (int i = 0; i < buttons.length; i++) {
            addObject(buttons[i], 0, 0);
        }        
        addSliders();
    }

    private void addSliders() {
        Slider popSlider = new Slider(50, 2500, 150, this, "Population", Color.BLUE, 50, "popSlider", true);
        addObject(popSlider, 400, 125);
        Slider sizeSlider = new Slider(25, 100, 150, this, "Map Size", Color.RED, 400, "sizeSlider", true);
        addObject(sizeSlider, 400, 225);
        Slider speedSlider = new Slider(20, 100, 150, this, "Speed", new Color(100, 255, 100), 400, "speedSlider", true);
        addObject(speedSlider, 400, 325);
        Slider difficultySlider = new Slider(1, 4, 150, this, "Difficulty", new Color(100, 255, 100), 400, "difficultySlider", 2);
        addObject(difficultySlider, 400, 425);

    }
}
