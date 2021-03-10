package rccookie.ui.basic;

import greenfoot.*;
import rccookie.game.raycast.Raycast.IgnoreOnRaycasts;
import rccookie.util.ClassTag;

@IgnoreOnRaycasts
public class Fade extends Actor {
    

    static {
        ClassTag.tag(Fade.class, "ui");
    }
    
    public final Color color;
    public final long duration;
    public final boolean fadeIn;

    private long startTime, endTime;

    private Fade(Color color, double duration, boolean fadeIn) {
        this.color = color;
        this.duration = (long)(duration * 1000000000);
        this.fadeIn = fadeIn;
    }


    /**
     * 
     * @param color The color at the beginning
     * @param duration In secondes
     * @return A fadein
     */
    public static final Fade fadeIn(Color color, double duration) {
        return new Fade(color, duration, true);
    }

    /**
     * 
     * @param color The color at the end
     * @param duration In secondes
     * @return A fadeout
     */
    public static final Fade fadeOut(Color color, double duration) {
        return new Fade(color, duration, false);
    }


    @Override
    protected void addedToWorld(World w) {
        startTime = System.nanoTime();
        endTime = startTime + duration;
        GreenfootImage image = new GreenfootImage(w.getWidth(), w.getHeight());
        image.setColor(color);
        image.fill();
        setImage(image);
    }


    @Override
    public void act() {
        long time = System.nanoTime();
        if(time > endTime) {
            getWorld().removeObject(this);
            return;
        }
        if(fadeIn) getImage().setTransparency((int)(255 * (1 - (time - startTime) / (double)duration)));
        else getImage().setTransparency((int)(255 * (time - startTime) / (double)duration));
    }
}
