package rccookie.ui.basic;

import greenfoot.*;
import rccookie.geometry.Vector2D;
import rccookie.util.ClassTag;
import rccookie.game.AdvancedActor;
import rccookie.game.raycast.Raycast.IgnoreOnRaycasts;

/**
 * A slider that lets you select a value in a given range. May be set to only allow integers.
 * 
 * @author RcCookie
 * @version 1.0
 */
@IgnoreOnRaycasts
public class Slider extends AdvancedActor {

    private static final long serialVersionUID = 918128934494787828L;

    static {
        ClassTag.tag(Slider.class, "ui");
    }
    

    public static final int WIDTH = 4;

    private boolean useFractions = true;
    private final double min, max;
    private int length;
    
    private Vector2D slideVector;
    private Handle handle;

    //private double lastValue;
    
    private final Color sliderCol, handleCol;

    public Slider(){
        this(0, 1, 100, Color.GRAY, Color.LIGHT_GRAY);
    }
    
    public Slider(double min, double max){
        this(min, max, 100, Color.GRAY, Color.LIGHT_GRAY);
    }
    
    public Slider(double min, double max, int length){
        this(min, max, length, Color.GRAY, Color.LIGHT_GRAY);
    }
    
    public Slider(double min, double max, int length, Color sliderCol, Color handleCol){
        this.min = min;
        this.max = max;
        this.length = length;
        if(length < 10) length = 10;
        this.sliderCol = sliderCol;
        this.handleCol = handleCol;
        handle = new Handle(this);
        setImage(generateSliderImage());
        slideVector = Vector2D.angledVector(0, length);
        setCollider(null);

        addPressAction(mouse -> {
            handle.setLocation(((MouseInfo)mouse).getX(), ((MouseInfo)mouse).getY());
            if(handle.getX() < minX()) handle.setLocation(minX(), handle.getY());
            else if(handle.getX() > maxX()) handle.setLocation(maxX(), handle.getY());
            if(handle.getY() < minY()) handle.setLocation(handle.getX(), minY());
            else if(handle.getY() > maxY()) handle.setLocation(handle.getX(), maxY());
            handle.value = getValue();
            setValue(handle.value);
        });
        addAddedAction(world -> world.addObject(handle, getX(), getY()));
        //setLastValue(min);
    }


    //private void setLastValue(double lastValue) {
        //this.lastValue = lastValue;
    //}
    
    public void allowFractions(boolean useFractions){
        this.useFractions = useFractions;
    }
    
    public int getIntValue(){
        return (int)(getValue() + 0.5);
    }
    
    public double getValue(){
        if(handle.getWorld() == null) getWorld().addObject(handle, getX(), getY());
        double range = max - min;
        double sliderRange;
        double locFromSlider0;
        if(maxX() - minX() > maxY() - minY()){
            sliderRange = maxX() - minX();
            locFromSlider0 = handle.getX() - minX();
        }
        else{
            sliderRange = maxY() - minY();
            locFromSlider0 = handle.getY() - minY();
        }
        double percentage = locFromSlider0 / sliderRange;
        
        if(useFractions) return min + percentage * range;
        return (int)(min + percentage * range + 0.5);
    }
    
    @Override
    public void setRotation(int angle){
        super.setRotation(angle);
        handle.setRotation(angle);
        double oldValue = getValue();
        slideVector = Vector2D.angledVector(angle, length);
        setValue(oldValue);
    }
    @Override
    public void setLocation(int x, int y){
        double oldValue = getValue();
        super.setLocation(x, y);
        setValue(oldValue);
    }
    
    public void setValue(double value){
        double percentage = (value - min) / (max - min);
        handle.value = value;
        //setLastValue(value);
        try{
            handle.setLocation(
                minX() + (int)(percentage * slideVector.x()),
                minY() + (int)(percentage * slideVector.y())
            );
        }catch(Exception e){}
    }
    
    public int minX(){
        return getX() - (int)(slideVector.x() * 0.5);
    }
    public int minY(){
        return getY() - (int)(slideVector.y() * 0.5);
    }
    public int maxX(){
        return getX() + (int)(slideVector.x() * 0.5);
    }
    public int maxY(){
        return getY() + (int)(slideVector.y() * 0.5);
    }

    public void remove() {
        if(getWorld() == null) return;
        getWorld().removeObject(handle);
        getWorld().removeObject(this);
    }
    




    // --------------------------------------------
    // Handle class
    // --------------------------------------------
    
    @IgnoreOnRaycasts
    public class Handle extends Button {

        private static final long serialVersionUID = 5248272965363967490L;

        public static final int SIZE = 14;

        Vector2D offset;
        public double value;

        public Handle(Slider s){
            super(generateHandleImage(s.handleCol), false);
            addPressAction(info -> updateOffset());
            addReleaseAction(info -> offset = null);
            addAddedAction(world -> value = getValue());
            clickedImage = generateHandleImageClicked(s.handleCol);
        }
        
        public void run(){
            //s.setValue(value);
            if(offset != null){
                try{
                    MouseInfo mouse = Greenfoot.getMouseInfo();
                    setLocation(mouse.getX() + (int)offset.x(), mouse.getY() + (int)offset.y());
                    if(getX() < minX()) setLocation(minX(), getY());
                    else if(getX() > maxX()) setLocation(maxX(), getY());
                    if(getY() < minY()) setLocation(getX(), minY());
                    else if(getY() > maxY()) setLocation(getX(), maxY());
                }catch(Exception e){
                    offset = null;
                }
            }
            value = getValue();
            setValue(value);
        }

        public void updateOffset(){
            try{
                MouseInfo mouse = Greenfoot.getMouseInfo();
                offset = new Vector2D(getX() - mouse.getX(), getY() - mouse.getY());
            }catch(Exception e){
                offset = null;
            }
        }
    }
    
    private GreenfootImage generateSliderImage(){
        GreenfootImage image = new GreenfootImage(length, WIDTH);
        image.setColor(sliderCol);
        image.fillRect(WIDTH / 2, 0, length - WIDTH, WIDTH);
        image.fillOval(0, 0, WIDTH - 1, WIDTH - 1);
        image.fillOval(length - WIDTH, 0, WIDTH - 1, WIDTH - 1);
        return image;
    }
    private static GreenfootImage generateHandleImage(Color handleCol){
        GreenfootImage image = new GreenfootImage(Handle.SIZE, Handle.SIZE);
        image.setColor(handleCol);
        image.fillOval(0, 0, Handle.SIZE - 1, Handle.SIZE - 1);
        return image;
    }
    private static GreenfootImage generateHandleImageClicked(Color handleCol){
        GreenfootImage image = new GreenfootImage(Handle.SIZE, Handle.SIZE);
        image.setColor(handleCol.darker().darker());
        image.fillOval(0, 0, Handle.SIZE - 1, Handle.SIZE - 1);
        return image;
    }
}