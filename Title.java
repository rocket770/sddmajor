import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Title here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Title extends Actor
{

    public Title(String img)
    {    
        setImage(img);
        getImage().scale((int)(getImage().getWidth()/2.2),(int)(getImage().getHeight()/2.4)); // set the image to the image passed through the parameters and scale it to a constant as needed, no point in making it modular as it is not intened to be a library routine
    }
    
}
