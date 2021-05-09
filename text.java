import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class text here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Text extends Actor {
    /**
     * Act - do whatever the text wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    // All variables are private for encapsulation, may be gotten using the 'getters' with a referenced object
    private String text;
    private int x, y;
    private GreenfootImage textImage;
    private GreenfootImage image;
    private GreenfootImage blank = new GreenfootImage("", 24, new Color(0, 0, 0), new Color(0, 0, 0, 0));
    private int state = 1;
    private Color color = Color.BLACK;
    private int fontSize = 24;

    // Constructors - customize how the text object is created,
    // if a vairbale is left blank, their default values will be used
    public Text(String text) {
        this.text = text; // plain text
    }

    public Text(String text, int fontSize) {
        this.text = text;
        this.fontSize = fontSize; // text with custom font size
    }

    public Text(String text, Color color) {
        this.text = text;
        this.color = color; // text with custom color
    }

    public Text(String text, Color color, int fontSize) {
        this.text = text;
        this.color = color;
        this.fontSize = fontSize; // text with custom color and font size
    }

    protected final void addedToWorld(World world) { // set up text, only run once when added to world. May never be called again with final attribute 
        textImage = new GreenfootImage(text, fontSize, color, new Color(0, 0, 0, 0));
        image = new GreenfootImage(textImage.getWidth() + 12, 48);
        x = getX();
        y = getY();
        rePaint();
    }

    public void toggle() { // public method strictly to toggle the text
        switch (state) {
            case 1:
            hide();
            break;
            case 0:
            rePaint();
            break;
        }
        state = ++state % 2; // reset to 1 or 0
    }

    public void changeText(String text) { // public 'setter' type method to update the text
        this.text = text;
        textImage = new GreenfootImage(text, fontSize, color, new Color(0, 0, 0, 0));
        image = new GreenfootImage(textImage.getWidth() + 12, 36);
        rePaint();
    }

    public void changeFontSize(int size) { // public 'setter' type method to update the size
        fontSize = size;
        textImage = new GreenfootImage(text, size, color, new Color(0, 0, 0, 0));
        image = new GreenfootImage(textImage.getWidth() + 12, 36);
        rePaint();
    }
    
    public void changeBoundarySize(int x, int y) { // public 'setter' type method to update the size
        textImage = new GreenfootImage(text, fontSize, color, new Color(0, 0, 0, 0));
        image = new GreenfootImage(textImage.getWidth() + x, y);
        rePaint();
    }
    
    public String getText() { // public 'getter' type method to return the current text
        return text;
    }

    public int getState() { // public 'getter' type method to return the current toggle state of the text
        return state;
    }

    private void hide() { // public method scritcly to hide the text
        setImage(blank);
    }

    private void rePaint() { // public method scritcly to redraw its contents to the object
        image.drawImage(textImage, 6, 6);
        setImage(image);
    }
}