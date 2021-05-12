import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Slider extends Actor {
    private float min, max;
    private int x, y;
    private int radius = 50;
    private World world;
    public float value;
    private int length;
    private double sliderX = 50;
    public String type;
    private GreenfootImage img = new GreenfootImage(12, 12);
    private float percentMoved;
    private Value v;
    private String ID;
    public boolean LinkedToButton;
    private Text t;
    public float reccomended;
    private int lastValue = 25;
    private int lineCount = 4;
    private final int INCREMENTS = lineCount + 1;
    private boolean roundValues = true;
    public Color color;
    Slider(float min, float max, int length, World world, String type, Color color, int sliderX, String ID, boolean LinkedToButton) {
        world.getBackground().setColor(color);
        this.color = color;
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

    Slider(float min, float max, int length, World world, String type, Color color, int sliderX, String ID, float reccomended) {
        world.getBackground().setColor(color);
        this.color = color;
        value = min;
        this.min = min;
        this.sliderX = sliderX;
        this.max = max;
        this.length = length;
        this.world = world;
        this.type = type;
        this.ID = ID;
        this.LinkedToButton = false;
        this.reccomended = reccomended;
    }

    protected void addedToWorld(World world) {
        x = getX();
        y = getY();
        drawSlider();
        if (!LinkedToButton) {
            v = new Value();
            getWorld().addObject(v, 0, 0);
            v.setID(ID);
            v.setValue(value);
            t = new Text("" + value);
            getWorld().addObject(t, x + 75, y + 30);
        }
    }

    public void act() {
        moveSlider();
    }

    private void moveSlider() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        try {
            if (mouse != null && Greenfoot.mouseDragged(this)) {
                sliderX = mouse.getX();
                if (sliderX < x) sliderX = x;
                if (sliderX > x + length) sliderX = x + length;
                setLocation((int) sliderX, y);
                if (LinkedToButton) {
                    updateButton();
                } else updateSlider();
            }
        } catch (Exception e) {
            return;
        }
    }

    private void updateButton() {
        percentMoved = (float)(((sliderX - x) / length * (max - min)) + min); // get distance up bar, turn it into a percent, multiply it by the amount of values we have and add the miniunum
        if(ID == "sizeSlider") {
            if(!(600 % percentMoved != 0) && percentMoved !=40) {
                lastValue = (int) percentMoved;
            } else percentMoved = (percentMoved % getWorld().getWidth() == 0 && percentMoved !=40)?++percentMoved:lastValue;
        }
        for (int i = 0; i < world.getObjects(Button.class).size(); i++) {
            if (world.getObjects(Button.class).get(i).text == type) {
                world.getObjects(Button.class).get(i).value = (int)percentMoved;
            }
        }
    }

    private void updateSlider() {
        value = (roundValues) ? (int)(((sliderX - x) / length * (max - min)) + min) : (float)(((sliderX - x) / length * (max - min)) + min);
        v.setValue(value);
        t.setText("" + value);
    }

    public void setValue(float amount) {
        value = amount;
        sliderX = ((double)(length * (value - min)) / (max - min)) + x; // just re-arranged the percent moved equtaion, in terms of mX honestly i didnt think it would work
        setLocation((int) Math.round(sliderX), y);
    }
    
    public void setroundValues(boolean roundValues) {
        this.roundValues = roundValues;
    }

    public void setReccomended() {
        setValue(reccomended);
        v.setValue(value);
        t.setText("" + value);
    }

    public void drawSlider() {
        world.getBackground().drawLine(x, y, x + length, y);
        world.showText("" + min, x, y + 15);
        world.showText("" + max, x + length, y + 15);
        makeCircle(12);
        drawIncrementLines();
    }

    private void makeCircle(int radius) {
        img.setColor(Color.RED);
        img.fillOval(0, 0, radius, radius);
        setImage(img);
    }

    private void drawIncrementLines() {
        for (int i = 0; i < INCREMENTS; i++) {
            world.getBackground().drawLine(x + (i * length / lineCount - 1), y, x + (i * length / lineCount - 1), y + 10);
        }
    }
}