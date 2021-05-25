import greenfoot.*;
/**
 * Write a description of class value here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Value extends Actor  
{
    // just a data store object
    private String ID;
    private float value;
    
    
    public Value() {
      getImage().setTransparency(0);   
    }
    
    public float getValue() {
        return value; // return value
    } 
    
    public String getID() {
        return ID; // return id
    }
    
    public void setValue(float v) {
        value = v; // set this objects value 
    }
    
    public void setID(String ID) {
        this.ID = ID; // set this objects id so it can be references later
    }

    // For Mr. Young: It may seem a lot more sketchy using an object for each value of data to be stored. During testing, I found no need to use an interface to create an abstract-layered value object.
    // I also found that this method here is much more efficient that saving the menu world and passing it through the MyWorld using a world-type paramter, this way I wouldnt have to search through 
    // each ecsess object such as the buttons or sliders. It may seem useless, but it saves alot of loading time this way. I also figured using 'getters' instead of vairabel references were more conventional this way.
}
