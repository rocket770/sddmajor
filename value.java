import greenfoot.*;
/**
 * Write a description of class value here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class value extends Actor  
{
    // just a data store object
    public String id;
    public float value;
    
    
    public float getValue(){
        return value;
    }
    
    public String getID(){
        return id;
    }
    
    public void setValue(float v){
        value = v;
    }
    
    public void setID(String id){
        this.id = id;
    }


}
