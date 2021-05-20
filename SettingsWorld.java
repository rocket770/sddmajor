import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SettingsWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SettingsWorld extends World
{
    private Button[] buttons = new Button[5];
    public static final int POP_DEFAULT = 50;
    public static final int SIZE_DEFAULT = 25;
    public static final int SPEED_DEFAULT = 20;
    public static final float MUTATION_DEFAULT = 0.01f;
    public static final int DIFFICULTY_DEFAULT = 1;
    /**
     * Constructor for objects of class SettingsWorld.
     * 
     */
    public SettingsWorld()
    {// Create a new world with 600x600 cells with a cell size of 1x1 pixels.
        super(600, 600, 1);
        addObject(new Title("SettingsTitle.gif"), getWidth()/2, 125); // just title screen
        Button pop = new Button(this, Color.ORANGE, 110, 185, "Population", "Var", 350, 50); // add all the buttons, sotirng them in an array is just nicer becuase thier location doesnt matter
        buttons[0] = pop;
        Button map = new Button(this, Color.ORANGE, 110, 285, "Map Size", "Var", 75, 25);
        buttons[1] = map;
        Button Speed = new Button(this, Color.ORANGE, 110, 385, "Speed", "Var", 100, 20);
        buttons[2] = Speed;
        Button setReccomended = new Button(this, Color.ORANGE, 451, 552, "Set Recc", 45, 24);
        buttons[3] = setReccomended;
        Button Exit = new Button(this, Color.ORANGE, 12,552, "Back", "Util", null, 45);   
        buttons[4] = Exit;
        for (int i = 0; i < buttons.length; i++) { // batch processing am i right sir
            addObject(buttons[i], 0, 0);
        }        
        addSliders();
    }

    public void act() {
        getBackground().setColor(Color.WHITE); // re fresh the background every frame
        getBackground().fill();
        GreenfootImage img = new GreenfootImage("TitleScreen.gif");
        setBackground(img); // draw new title screen background
        getBackground().setColor(new Color(128,128,128)); // add grey backdrop
        getBackground().fillRect(60, 100, 480, 450);
        for(Slider s: getObjects(Slider.class)) { // repaint all of the sliders with thier colors 
            getBackground().setColor(s.color);
            s.drawSlider();
        }
    }

    private void addSliders() { // add all sliders
        Slider popSlider = new Slider(POP_DEFAULT, 2500, 150, this, "Population", Color.RED, 50, "popSlider", true);
        addObject(popSlider, 325, 200);
        Slider sizeSlider = new Slider(SIZE_DEFAULT, 100, 150, this, "Map Size", Color.RED, 400, "sizeSlider", true);
        addObject(sizeSlider, 325, 300);
        Slider speedSlider = new Slider(SPEED_DEFAULT, 100, 150, this, "Speed", Color.RED, 400, "speedSlider", true);
        addObject(speedSlider, 325, 400);
        Slider difficultySlider = new Slider(DIFFICULTY_DEFAULT, 4, 150, this, "Difficulty", Color.RED, 400, "difficultySlider", 2);
        addObject(difficultySlider, 325, 500);
        Slider mutationRateSlider = new Slider(MUTATION_DEFAULT, 1, 150, this, "Mutation", Color.RED, 400, "mutationSlider", .05f);       
        mutationRateSlider.setroundValues(false);        
        addObject(mutationRateSlider, 100, 500);
        addObject(new Text("Mutation Rate"),mutationRateSlider.x+75,mutationRateSlider.y-25); // sliders dont have titles, so manually add them for the ones that dont have buttons
        addObject(new Text("Difficulty"),difficultySlider.x+75,difficultySlider.y-25);
    }
}
