package rccookie.learning;

import rccookie.event.greenfoot.KeyListener;

public class KeyDownNeuron extends InputNeuron {

    final KeyListener key;

    public KeyDownNeuron(String key){
        this.key = new KeyListener(key);
    }

    @Override
    public double getInput() {
        key.update();
        return key.down() ? 1 : 0;
    }
}
