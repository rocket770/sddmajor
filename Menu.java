import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class Menu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Menu extends World
{
    transient public Button[] buttons = new Button[7];
    public Menu()
    {    
        super(600, 600, 1);
        init();
        addSliders();
    }
    

    private void init(){
        Greenfoot.start();
        Greenfoot.setSpeed(100);
        getBackground().setColor(Color.WHITE);
        getBackground().fillRect(0,0,600,400);
        generateButtons();
        showText("The file name of the map will be based of the selected world size. It should automatically update absed on the file name",250,250);
    }

    private void generateButtons(){
        Button pop = new Button(this, Color.BLUE, 50,200, "Population", "Var", 350,50); buttons[0] = pop;
        Button map = new Button(this, Color.RED, 225,200, "Map Size", "Var", 75,25); buttons[1] = map;
        Button Speed = new Button(this, Color.GREEN, 400,200, "Speed", "Var", 100,20); buttons[2] = Speed;
        Button setReccomended = new Button(this, new Color(128,128,128), 139,375, "Set Recc", 50, 24); buttons[3] = setReccomended;
        Button switchWorld = new Button(this, Color.PINK, 313,375, "Enter World", "switchWorld", buttons, new Color(128,128,128), 50); buttons[4] = switchWorld;
        Button importMap = new Button(this, Color.YELLOW, 230,500, "Import Map", "Var", buttons, null, 50);    buttons[5] = importMap;
        Button levelEditor = new Button(this, Color.PINK, 313,575, "Level Editor", "levelEditor", buttons, new Color(128,128,128), 50); buttons[6] = levelEditor;

        for(int i = 0; i<buttons.length; i++){
            addObject(buttons[i],0,0);
        }
        
    }
    
    private void addSliders(){
       Slider popSlider = new Slider(50,2500, 150, this, "Population", new Color(100,100,255), 50);
        addObject(popSlider,50,300);
        Slider speedSlider = new Slider(20,100, 150,this, "Speed", new Color(100,255,100),400);
        addObject(speedSlider,400,300); 
       // Slider difficultySlider = new Slider(0,4, 150, this, "Difficulty", new Color(100,255,100),400);
       // addObject(difficultySlider,400,300);
    }

}
