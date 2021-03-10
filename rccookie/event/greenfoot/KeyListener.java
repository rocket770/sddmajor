package rccookie.event.greenfoot;
import java.util.EventListener;

import greenfoot.Greenfoot;

public class KeyListener implements EventListener{

    public final String key;
    private boolean down, pressed, released;
    private int count = 0;

    public KeyListener(String key){
        this.key = key;
        update();
    }

    public void update(){
        boolean temp = down;
        down = Greenfoot.isKeyDown(key);
        if(temp!=down){
            pressed = down;
            released = !down;
            if(down) {
                count++;
                onPress();
            }
            else onRelease();
        }
        else pressed = false;
    }

    public void click(int count){
        for(int i=0; i<count; i++){
            click();
        }
    }
    public void click(){
        count++;
        onPress();
        onRelease();
    }

    public boolean down(){
        return down;
    }
    public boolean pressed(){
        return pressed;
    }
    public boolean released(){
        return released;
    }
    public int count(){
        return count;
    }

    public void onPress(){}
    public void onRelease(){}
}