package rccookie.app;

import java.awt.Image;

import javax.swing.ImageIcon;

import rccookie.geometry.Transform2D;
import rccookie.geometry.Vector2D;

public abstract class Item {
    
    private Transform2D transform;
    private World world;
    private Image image;


    public Item() {
        this(null);
    }
    public Item(Image image) {
        if(image == null) image = new ImageIcon("rccookie/app/images/defaultIcon.png").getImage();
        this.image = image;
    }


    public double getX() {
        return getTransform().location.x();
    }

    public double getY() {
        return getTransform().location.y();
    }

    public Vector2D getLocation() {
        return getTransform().location;
    }

    public double getRotation() {
        return getTransform().rotation;
    }

    public Transform2D getTransform() {
        return transform.clone();
    }


    public void setX(double x) {
        setLocation(x, transform.location.y());
    }

    public void setY(double y) {
        setLocation(transform.location.x(), y);
    }

    public void setLocation(Vector2D location) {
        setLocation(location.x(), location.y());
    }

    public void setLocation(double x, double y) {
        transform.location.set(x, y);
    }

    public void setRotation(double rotation) {
        transform.rotation = rotation;
    }

    public void setTransform(Transform2D transform) {
        setLocation(transform.location);
        setRotation(transform.rotation);
    }
    


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    
    public World getWorld() {
        return world;
    }

    void setWorld(World world) {
        this.world = world;
    }
}
