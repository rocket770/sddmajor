package rccookie.ui.basic;

import greenfoot.Color;
import greenfoot.GreenfootImage;
import rccookie.game.AdvancedActor;

public class Image extends AdvancedActor {

    private static final long serialVersionUID = -5125968660942830686L;

    private static final GreenfootImage NOT_FOUND_IMAGE(String filePath) {
        return new Text("Image \"" + filePath + "\" could not be found", Color.RED, Color.BLACK).getImage();
    }

    private GreenfootImage image;

    public Image(GreenfootImage image) {
        setImage(image);
    }
    public Image(String filePath) {
        try{
            setImage(new GreenfootImage(filePath));
        }
        catch(Exception e) {
            setImage(NOT_FOUND_IMAGE(filePath));
        }
    }
    public Image(int width, int height, Color color) {
        this(coloredImage(width, height, color));
    }
    public Image(int width, int height) {
        this(width, height, Color.WHITE);
    }

    private static final GreenfootImage coloredImage(int width, int height, Color color) {
        GreenfootImage image = new GreenfootImage(width, height);
        image.setColor(color);
        image.fill();
        return image;
    }


    @Override
    public GreenfootImage getImage() {
        return image;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }


    @Override
    public void setImage(GreenfootImage image) {
        this.image = image;
        super.setImage(image);
    }
}
