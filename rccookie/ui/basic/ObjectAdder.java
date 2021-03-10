package rccookie.ui.basic;

import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import greenfoot.World;
import rccookie.game.raycast.Raycast.IgnoreOnRaycasts;
import rccookie.util.ClassTag;

/**
 * A simple class that takes in an object to add to the world using the mouse.
 */
@IgnoreOnRaycasts
public class ObjectAdder extends Actor {

    static {
        ClassTag.tag(ObjectAdder.class, "ui");
    }
    

    private Actor object;

    public ObjectAdder(Actor objectToAdd) {
        object = objectToAdd;
        setImage(object.getImage());
    }


    @Override
    protected void addedToWorld(World world) {
        try{
            MouseInfo mouse = Greenfoot.getMouseInfo();
            setLocation(mouse.getX(), mouse.getY());
        }catch(NullPointerException e) {}
    }


    public void act() {
        try{
            MouseInfo mouse = Greenfoot.getMouseInfo();
            setLocation(mouse.getX(), mouse.getY());
            if(mouse.getButton() == 1) {
                getWorld().addObject(object, getX(), getY());
                getWorld().removeObject(this);
                object = null;
            }
            else if(mouse.getButton() == 3) {
                getWorld().removeObject(this);
                object = null;
            }
        }catch(NullPointerException e) {}
    }
}