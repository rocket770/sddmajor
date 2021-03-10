import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class text here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class text extends Actor
{
    /**
     * Act - do whatever the text wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public String text;
    private int x,y;
    private GreenfootImage textImage;
    private GreenfootImage image;
    private GreenfootImage blank = new GreenfootImage("", 24, new Color(0, 0,0), new Color(0, 0, 0, 0));
    public int state =1;
    private Color color = Color.BLACK;
    private int fontSize = 24;
    public text(String text){
        this.text = text;
    }
    
    public text(String text, int fontSize){
        this.text = text;
        this.fontSize = fontSize;
    }

    public text(String text, Color color){
        this.text = text;
        this.color = color;
    }
    
    public text(String text, Color color, int fontSize){
        this.text = text;
        this.color = color;
        this.fontSize = fontSize;
    }

    protected void addedToWorld(World world){
        textImage = new GreenfootImage(text, fontSize, color, new Color(0, 0, 0, 0));
        image = new GreenfootImage(textImage.getWidth()+12, 36);
        
        x= getX();
        y = getY();
        rePaint();
    }

    public void toggle(){
        switch(state){
            case 1: hide(); break; 
            case 0: rePaint();  break;
        }
        state = ++state%2; // reset to 1 or 0
    }
    
    public void changeText(String text){
        this.text = text;
        textImage = new GreenfootImage(text, fontSize, color, new Color(0, 0, 0, 0));
        image = new GreenfootImage(textImage.getWidth()+12, 36);
        rePaint();
    }
    
    public String getText(){
        return text;
    }

    private void hide(){
        setImage(blank);
    }

    private void rePaint(){
        image.drawImage(textImage, 6, 6);
        setImage(image);
    }
}
