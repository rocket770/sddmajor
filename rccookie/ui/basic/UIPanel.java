package rccookie.ui.basic;

import greenfoot.*;
import rccookie.game.AdvancedActor;
import rccookie.game.raycast.Raycast.IgnoreOnRaycasts;
import rccookie.util.ClassTag;

import java.util.HashMap;

@IgnoreOnRaycasts
public class UIPanel extends AdvancedActor {

    private static final long serialVersionUID = 2483357777930844887L;
    
    static {
        ClassTag.tag(UIPanel.class, "ui");
    }
    
    private HashMap<Actor, RelativeLocation> elements;
    private int x, y;
    private boolean coversWorld;
    
    public UIPanel(World cover){
        this(cover.getWidth(), cover.getHeight(), new GreenfootImage(cover.getWidth(), cover.getHeight()));
        coversWorld = true;
    }
    
    public UIPanel(int x, int y) {
        this(x, y, Color.WHITE);
    }

    public UIPanel(int x, int y, Color color){
        this(x, y, coloredImage(x, y, color));
        coversWorld = false;
    }
    
    public UIPanel(World cover, Color color){
        this(cover.getWidth(), cover.getHeight(), coloredImage(cover.getWidth(), cover.getHeight(), color));
        coversWorld = true;
    }
    
    public UIPanel(World cover, GreenfootImage image){
        this(cover.getWidth(), cover.getHeight(), image);
        coversWorld = true;
    }
    
    public UIPanel(int x, int y, GreenfootImage image){
        this.x = x;
        this.y = y;
        setCollider(null);
        elements = new HashMap<Actor, RelativeLocation>();
        setImage(image);
        coversWorld = false;
        addAddedAction(world -> {
            updateElements();
            if(coversWorld) setLocation(x / 2, y / 2);
        });
    }

    private static final GreenfootImage coloredImage(int x, int y, Color color) {
        GreenfootImage image = new GreenfootImage(x, y);
        if(color != null) {
            image.setColor(color);
            image.fill();
        }
        return image;
    }


    public void add(Actor element, double x, double y) {
        add(element, x, y, 0, 0);
    }
    
    public void add(Actor element, double x, double y, double offX, double offY){
        if(element == null) return;
        RelativeLocation loc = new RelativeLocation(x, y, offX, offY);
        elements.put(element, loc);
        if(getWorld() != null){
            addElement(element, loc);
        }
    }

    private void addElement(Actor element, RelativeLocation loc) {
        getWorld().addObject(
            element,
            getX() - (this.x / 2) + (int)(this.x * loc.relative.x() + loc.offset.x()),
            getY() - (this.y / 2) + (int)(this.y * loc.relative.y() + loc.offset.y())
        );
    }

    public void move(Actor element, double x, double y){
        if(element != null && elements.keySet().contains(element)){
            add(element, x, y);
        }
    }
    
    public void resize(int x, int y){
        this.x = x;
        this.y = y;
        GreenfootImage image = getImage();
        image.scale(x, y);
        setImage(image);
        updateElements();
    }
    
    private void updateElements(){
        for(Actor element : elements.keySet()){
            RelativeLocation loc = elements.get(element);
            if(element.getWorld() == null){
                addElement(element, loc);
            }
            else{
                element.setLocation(
                    getX() - (x / 2) + (int)(x * loc.relative.x() + loc.offset.x()),
                    getY() - (y / 2) + (int)(y * loc.relative.y() + loc.offset.y())
                );
            }
        }
    }
    
    @Override
    public void setLocation(int x, int y){
        if(x == getX() && y == getY()) return;
        coversWorld = false;
        super.setLocation(x, y);
        updateElements();
    }
    
    @Override
    public void setRotation(int angle){
        System.err.println("Cannot change angle of ui plane!");
    }



    public void remove() {
        if(getWorld() == null) return;
        for(Actor element : elements.keySet()) {
            if(element instanceof Slider) ((Slider)element).remove();
            else getWorld().removeObject(element);
        }
        getWorld().removeObject(this);
    }



    public int getWidth() {
        return x;
    }
    public int getHeight() {
        return y;
    }
}