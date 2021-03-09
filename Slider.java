import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Slider extends Actor{
    float min, max;
    private int x,y;
    int radius = 50;
    World world;
    float value; 
    int length;
    private double mouseX = 50;
    int lineCount = 4;
    String type;
    GreenfootImage img = new GreenfootImage(12,12);
    private int percentMoved;
    Slider(float min, float max, int length,World world, String type, Color color, int mouseX){
        world.getBackground().setColor(color);
        this.min = min;
        this.mouseX = mouseX;
        this.max = max;
        this.length = length;
        this.world = world;
        this.type = type;
    }

    protected void addedToWorld(World world)
    {
        x = getX();
        y = getY();
        drawSlider();
    }

    public void act(){
        moveSlider();
    }

    private void moveSlider(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        try{
            if(mouse !=null && Greenfoot.mouseDragged(this)){
                mouseX = mouse.getX();
                if(mouseX < x) mouseX = x;
                if(mouseX > x+length) mouseX = x+length;
                setLocation((int)mouseX,y);
                updateButton();
            }
        } catch(Exception e){
            return;
        }
    }

    private void updateButton(){
        percentMoved = (int)(((mouseX-x)/length*(max-min))+min);        // get distance up bar, turn it into a percent, multiply it by the amount of values we have and add the miniunum
        for(int i = 0; i < world.getObjects(Button.class).size(); i++){
            if(world.getObjects(Button.class).get(i).text == type){
                world.getObjects(Button.class).get(i).value = percentMoved;
            }
        }
    }

    public void setValue(int amount){
        value = amount;
        mouseX = ((double)(length*(value-min))/(max-min))+x;    // just re-arranged the percent moved equtaion
        setLocation((int)Math.round(mouseX),y);
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