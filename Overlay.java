import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Overlay extends Actor {
    MyWorld world;
    Overlay() {
        GreenfootImage img = new GreenfootImage(600, 600); // a new transparent image that covers the entire screen
        img.fillRect(0, 0, 600, 600);
        img.setColor(new Color(128, 128, 128)); // make it grey
        img.setTransparency(50); // make it transparent
        setImage(img);
    }

}