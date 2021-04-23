import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Slider extends Actor{
    private float min, max;
    private int x,y;
    private int radius = 50;
    private World world;
    public float value; 
    private int length;
    private double sliderX = 50;
    private int lineCount = 4;
    public String type;
    private GreenfootImage img = new GreenfootImage(12,12);
    private int percentMoved;
    private Value v;
    private String ID;
    public boolean LinkedToButton;
    private Text t;
    public int reccomended;
    Slider(float min, float max, int length,World world, String type, Color color, int sliderX, String ID, boolean LinkedToButton){
        world.getBackground().setColor(color);
        value = min;
        this.min = min;
        this.sliderX = sliderX;
        this.max = max;
        this.length = length;
        this.world = world;
        this.type = type;
        this.ID = ID;
        this.LinkedToButton = LinkedToButton;
    }
    
    Slider(float min, float max, int length,World world, String type, Color color, int sliderX, String ID, boolean LinkedToButton, int reccomended){
        world.getBackground().setColor(color);
        value = min;
        this.min = min;
        this.sliderX = sliderX;
        this.max = max;
        this.length = length;
        this.world = world;
        this.type = type;
        this.ID = ID;
        this.LinkedToButton = LinkedToButton;
        this.reccomended = reccomended;
    }

    protected void addedToWorld(World world)
    {
        x = getX();
        y = getY();
        drawSlider();
        if(!LinkedToButton){
            v = new Value();
            getWorld().addObject(v,0,0);
            v.setID(ID);
            v.setValue(value);
            t = new Text(""+value);
            getWorld().addObject(t,x+75,y+30);
        }
    }

    public void act(){
        moveSlider();
    }

    private void moveSlider(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        try{
            if(mouse !=null && Greenfoot.mouseDragged(this)){
                sliderX = mouse.getX();
                if(sliderX < x) sliderX = x;
                if(sliderX > x+length) sliderX = x+length;
                setLocation((int)sliderX,y);
                if(LinkedToButton){
                    updateButton();
                } else updateSlider(); 
            }
        } catch(Exception e){
            return;
        }
    }

    private void updateButton(){
        percentMoved = (int)(((sliderX-x)/length*(max-min))+min);        // get distance up bar, turn it into a percent, multiply it by the amount of values we have and add the miniunum
        for(int i = 0; i < world.getObjects(Button.class).size(); i++){
            if(world.getObjects(Button.class).get(i).text == type){
                world.getObjects(Button.class).get(i).value = percentMoved;
            }
        }
    }
    
    private void updateSlider(){
        value = (int)(((sliderX-x)/length*(max-min))+min);   
        v.setValue(value);
        t.changeText(""+value);
    }
    

    public void setValue(int amount){
        value = amount;
        sliderX = ((double)(length*(value-min))/(max-min))+x;    // just re-arranged the percent moved equtaion, in terms of mX honestly i didnt think it would work
        setLocation((int)Math.round(sliderX),y);
    }
    
    public void setReccomended(){
        setValue(reccomended);
        v.setValue(value);
        t.changeText(""+value);
    }

    private void drawSlider(){
        world.getBackground().drawLine(x,y,x+length,y);
        world.showText(""+min, x,y+15);
        world.showText(""+max, x+length,y+15);
        makeCircle(12);
        drawIncrementLines();
    }

    private void makeCircle(int radius){
        img.setColor(Color.RED);
        img.fillOval(0,0,radius,radius);
        setImage(img);
    }

    private void drawIncrementLines(){
        for(int i = 0; i<5;i++){
            world.getBackground().drawLine(x+(i*length/lineCount-1),y,x+(i*length/lineCount-1),y+10);
        }
    }
}