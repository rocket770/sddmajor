package rccookie.ui.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import greenfoot.*;
import rccookie.game.raycast.Raycast.IgnoreOnRaycasts;
import rccookie.ui.basic.Button;


@IgnoreOnRaycasts
public class Switch extends Button {
    private static final long serialVersionUID = -3903122439666473764L;

    private GreenfootImage on, off, clickedOn, clickedOff, hoveredOn, hoveredOff;

    private boolean state = false;

    private final List<Consumer<Boolean>> switchActions = new ArrayList<>();
    
    public Switch(){
        this(20);
    }

    public Switch(int size){
        super(generateImageOff(size), false);
        addClickAction(info -> switchState());

        on = generateImageOn(size);
        off = generateImageOff(size);
        clickedOn = new GreenfootImage(on);
        clickedOn.setColor(CLICK_COLOR);
        clickedOn.fill();
        clickedOff = new GreenfootImage(off);
        clickedOff.setColor(CLICK_COLOR);
        clickedOff.fill();
        hoveredOn = new GreenfootImage(on);
        hoveredOn.scale((int)(on.getWidth() * 1.08), (int)(on.getHeight() * 1.08));
        hoveredOff = new GreenfootImage(off);
        hoveredOff.scale((int)(off.getWidth() * 1.08), (int)(off.getHeight() * 1.08));

        createAndSetImages();
        updateAnimations();
    }
    
    private static GreenfootImage generateImageOff(int size){
        if(size < 10) size = 10;
        GreenfootImage image = generateBackgroundImage(size);
        image.drawImage(generateHandleImage(Color.RED.darker(), size), 1, 1);
        return image;
    }
    private static GreenfootImage generateImageOn(int size){
        if(size < 10) size = 10;
        GreenfootImage image = generateBackgroundImage(size);
        image.drawImage(generateHandleImage(Color.GREEN, size), size + 1, 1);
        return image;
    }
    private static GreenfootImage generateBackgroundImage(int size){
        GreenfootImage image = new GreenfootImage(2 * size, size);
        image.setColor(Color.LIGHT_GRAY);
        image.fill();
        image.setColor(Color.DARK_GRAY);
        image.drawRect(0, 0, 2 * size - 1, size - 1);
        return image;
    }
    private static GreenfootImage generateHandleImage(Color c, int size){
        GreenfootImage image = new GreenfootImage(size - 2, size - 2);
        image.setColor(c);
        image.fill();
        image.setColor(c.darker());
        image.drawRect(0, 0, size - 3, size - 3);
        return image;
    }
    
    public void setState(boolean state){
        this.state = state;
        createAndSetImages();
        updateAnimations();
    }
    public void switchState(){
        setState(!getState());
        onSwitch(getState());
    }


    @Override
    protected void createAndSetImages() {
        if(state) {
            image = on;
            hoveredImage = hoveredOn;
            clickedImage = clickedOn;
        }
        else {
            image = off;
            hoveredImage = hoveredOff;
            clickedImage = clickedOff;
        }
    }

    protected void onSwitch(boolean state) {

    }
    
    public boolean getState(){
        return state;
    }


    public Switch addSwitchAction(Consumer<Boolean> newState) {
        if(newState == null) return this;
        switchActions.add(newState);
        return this;
    }

    public Switch removeSwitchAction(Consumer<Boolean> action) {
        switchActions.remove(action);
        return this;
    }
}