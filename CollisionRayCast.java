import rccookie.game.AdvancedActor;
import greenfoot.GreenfootImage; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import rccookie.game.raycast.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Write a description of class CollisionRayCast here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CollisionRayCast extends AdvancedActor implements Runnable {
    MyWorld world;
    MazeRunner runner;
    boolean hasExecuted = false;
    GreenfootImage image = new GreenfootImage(2, 2);
    private List<MazeRunner> genomes;
    public boolean isRunning = true;
    public boolean stopped = false;

    Raycasting raycast = new Raycasting(this);
    {
        raycast.setDebug(false); // dont set to true with this actor! Even if you're marking this game! The thread isnt able to handle this!
        raycast.setMaxLength(12); // set a defeault length, it really doesnt matter
    }

    public CollisionRayCast(List<MazeRunner> genomes) {
        setImage(image);
        getImage().fill();
        this.genomes = genomes;
    }

    void onStart() {
        if (!hasExecuted) {
            hasExecuted = true;
            world = (MyWorld) getWorld(); // get the world object, cast to MyWorld
            //CollisionRayCast[] ignoreActors = ((ArrayList<CollisionRayCast>)getWorld().getObjects(CollisionRayCast.class)).toArray(new CollisionRayCast[0]);  // not need anymore as we are only having 1 object not size of population  
            //raycast.setIgnore(ignoreActors);
            raycast.setMaxLength(world.size / 6 + 2); // set to radius of runner circle, +2
        }
    }

    // DONT EDIT CODE WHILE A THREAD MAY BE RUNNING 
    // Greenfoot API functions are not 100% Thread Compataible, In the rare case an error occours, it may be due to your
    // Version of greenfoot and computer asweell. In some cases, you can just press run again and it may work or restart
    // If it is not fixable on yuor machine, I can prove to you it wokrs on mine!
    @Override
    public void run() {
        onStart(); // this seems very inefficent but since it is running on its own thread, seperate from greenfoot, we can now use it while mainting ~300-700 fps
        while (isRunning) { // dont kill the thread, however we can chose to kill it if we want
            while (!world.network.allMazeRunnersDead()) { // dont update the thread at a null state  -Dangoures even if the object is syncronyzed and wrapped by a boolean field
                try {
                    for (MazeRunner runner: genomes) { // loop through population given
                        //runner = currentRunner;
                        if (!runner.dead) {
                            setRotation(runner.getRotation()); // shoot a ray cast from the actor with thier rotation and look x units infront
                            setLocation(runner.getX() + 4, runner.getY() + 4);
                            raycast.update(); // draw the raycast and get its return
                            if (raycast.getRaycast().hit instanceof Wall || isTouching(Wall.class)) {
                                runner.dead = true; // if the object is on the wall or the raycast returns any object of type "Wall", kill the current runner it is watching
                            }
                        }
                    }
                    genomes = world.network.genomes; // get new state of population
                } catch (Exception e) {
                    //e.printStackTrace();
                    //stopThread();
                }
            }
        }
        stopped = true;
    }

    public synchronized void stopThread() {
        isRunning = false;
        System.out.println("Thread Stopping!, Waiting for next cylce...");
    }

    public synchronized void startThread() {
        isRunning = true;
        System.out.println("Thread Started!");
    }

}