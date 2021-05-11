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
        addObject(new Title("SettingsTitle.gif"), getWidth()/2, 125);
        Button pop = new Button(this, Color.ORANGE, 110, 100, "Population", "Var", 350, 50);
        buttons[0] = pop;
        Button map = new Button(this, Color.ORANGE, 110, 200, "Map Size", "Var", 75, 25);
        buttons[1] = map;
        Button Speed = new Button(this, Color.ORANGE, 110, 300, "Speed", "Var", 100, 20);
        buttons[2] = Speed;
        Button setReccomended = new Button(this, Color.ORANGE, 400, 520, "Set Recc", 45, 24);
        buttons[3] = setReccomended;
        Button Exit = new Button(this, Color.ORANGE, 12,552, "Back", "Util", null, 45);   
        buttons[4] = Exit;
        for (int i = 0; i < buttons.length; i++) {
            addObject(buttons[i], 0, 0);
        }        
        addSliders();
    }
    
    public void act() {
        getBackground().setColor(Color.WHITE);
        getBackground().fill();
        GreenfootImage img = new GreenfootImage("TitleScreen.gif");
        setBackground(img);
        getBackground().setColor(new Color(128,128,128));
        getBackground().fillRect(60, 100, 537, 450);
    }

    private void addSliders() {
        Slider popSlider = new Slider(50, 2500, 150, this, "Population", Color.RED, 50, "popSlider", true);
        addObject(popSlider, 350, 125);
        Slider sizeSlider = new Slider(25, 100, 150, this, "Map Size", Color.RED, 400, "sizeSlider", true);
        addObject(sizeSlider, 350, 225);
        Slider speedSlider = new Slider(20, 100, 150, this, "Speed", Color.RED, 400, "speedSlider", true);
        addObject(speedSlider, 350, 325);
        Slider difficultySlider = new Slider(1, 4, 150, this, "Difficulty", Color.RED, 400, "difficultySlider", 2);
        addObject(difficultySlider, 350, 425);
        Slider Mutation = new Slider(0, 1, 150, this, "Mutation", Color.RED, 400, "mutationSlider", .03f);
        {
            Mutation.setroundValues(false);
        }
        addObject(Mutation, 100, 425);
    }
}
