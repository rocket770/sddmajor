import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Overlay extends Actor {
    MyWorld world;
    Overlay() {
        GreenfootImage img = new GreenfootImage(600, 600);
        img.fillRect(0, 0, 600, 600);
        img.setColor(new Color(128, 128, 128));
        img.setTransparency(50);
        setImage(img);
    }

}