package rccookie.event.greenfoot;

import java.util.HashSet;
import java.util.Set;

import greenfoot.Greenfoot;
import greenfoot.core.Simulation;

@Deprecated
public abstract class Input {

    private static final String[] KEYS = {
        
    };
    
    private static final Set<String> pressedKeys = new HashSet<>();

    private static final InputThread inputThread = new InputThread();

    static {
        clearKeyStates();
        inputThread.start();
    }

    private static void clearKeyStates() {
        pressedKeys.clear();
    }






    
    private static class InputThread extends Thread {
        @Override
        public void run() {
            Simulation.getInstance().setPriority(5);
            System.out.println("input detection started");
            setPriority(1);
            while(true) {
                for(String key : KEYS) {
                    boolean newState = Greenfoot.isKeyDown(key);
                    if(newState != pressedKeys.contains(key)) {
                        //if(newState) keyPressed(key);
                        //else keyReleased(key);
                    }
                }
                System.out.println("update");
            }
        }
    }

}
