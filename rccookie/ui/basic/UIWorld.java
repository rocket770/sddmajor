package rccookie.ui.basic;

import greenfoot.*;
import rccookie.game.util.Time;
import rccookie.ui.advanced.*;
import rccookie.util.ClassTag;

import java.io.Serializable;
import java.util.HashMap;

public class UIWorld extends World implements Serializable {

    private static final long serialVersionUID = 6103735650468166075L;

    static {
        ClassTag.tag(UIWorld.class, "ui");
    }
    

    @SuppressWarnings("rawtypes")
    public static Class[] UI_CLASSES = {FpsDisplay.class, Fade.class, Button.class, DropDownMenu.Background.class, Slider.class, Text.class, UIPanel.class};

    private HashMap<Actor, RelativeLocation> elements;
    private Time time;

    @SuppressWarnings("rawtypes")
    private Class[] customPaintOrder;

    public UIWorld(int x, int y, int cellSize){
        this(x, y, cellSize, false);
    }
    
    public UIWorld(int x, int y, int cellSize, boolean bounded){
        super(x, y, cellSize, bounded);
        elements = new HashMap<Actor, RelativeLocation>();

        time = new Time();
        addObject(time, 0, 0);
        setPaintOrder();
    }
    
    public void add(Actor element, double x, double y){
        add(element, x, y, 0, 0);
    }
    
    public void add(Actor element, double x, double y, double offX, double offY){
        if(element == null) return;
        RelativeLocation loc = new RelativeLocation(x, y, offX, offY);
        elements.put(element, loc);
        addElement(element, loc);
    }

    private void addElement(Actor element, RelativeLocation loc) {
        addObject(
            element,
            (int)(getWidth() * loc.relative.x() + loc.offset.x()),
            (int)(getHeight() * loc.relative.y() + loc.offset.y())
        );
    }


    @Override
    public void addObject(Actor object, int x, int y) {
        setPaintOrder(customPaintOrder);
        super.addObject(object, x, y);
    }

    /**
     * Defines the order of painting. UI elements are automatically at the top. To override their position,
     * specificly set them to the position in the ordering you want them to be in.
     */
    @Override
    @SuppressWarnings("rawtypes")
    public void setPaintOrder(Class... classes) {
        customPaintOrder = classes;
        super.setPaintOrder(combineClasses(UI_CLASSES, classes != null ? classes : new Class[0]));
    }

    @SuppressWarnings("rawtypes")
    private Class[] combineClasses(Class[] a, Class[] b) {
        int twice = 0;
        aLoop: for(Class clsA : a) {
            for(Class clsB : b) {
                if(clsA == clsB) {
                    twice++;
                    continue aLoop;
                }
            }
        }
        Class[] c = new Class[a.length + b.length - twice];
        twice = 0;
        aLoop: for(int i=0; i<a.length; i++) {
            for(Class clsB : b) {
                if(a[i] == clsB) {
                    twice++;
                    continue aLoop;
                }
            }
            c[i - twice] = a[i];
        }
        for(int i=0; i<b.length; i++) {
            c[a.length - twice + i] = b[i];
        }
        return c;
    }

    public void addFps(){
        if(getObjects(FpsDisplay.class).size()==0) add(new FpsDisplay(), .063, .03);
    }

    public void moveTo(Actor element, double x, double y){
        if(element != null && elements.keySet().contains(element)){
            add(element, x, y);
        }
    }

    public void colorBackground(Color color) {
        getBackground().setColor(color);
        getBackground().fill();
    }

    public Time time(){
        return time;
    }
}