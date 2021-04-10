import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
/**
 * Write a description of class DNA here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DNA extends Actor
{
    public Vector[] directions;//array of vectors which get the dot to the goal (hopefully)
    public int step = 0;
    private Vector vector;
    private Random random = new Random();
    MyWorld world = ((MyWorld)getWorld());
    DNA(int size) {
        world = (MyWorld)MyWorld.world;
        directions = new Vector[size];
        vector = new Vector();
        randomize();
    }
    //sets all instructions to a vector pointed to a random angle. Later on these will change as the computer learns. 
    public void randomize() {
        for (int i = 0; i< directions.length; i++) {
            float randomAngle = random.nextFloat()*(float)(2.0f*Math.PI);
            directions[i] = vector.fromAngle(randomAngle);
        }
    }

    //copy directions array to new DNA copy
    public DNA transform(){ // no crossover, rely on mutation. We do this with the best node but still mutate it later on. 
        DNA clone = new DNA(directions.length);
        for (int i = 0; i < directions.length; i++) {
            clone.directions[i] = directions[i].copy();
        }
        return clone;
    }

    public DNA crossOver(){
        DNA clone = new DNA(directions.length);
        mazeRunner best = world.network.genomes[world.network.bestmazeRunner];  // utilize best found runner to breed with candidate
        // peform uniform crossover with a heavy bais towards selected candiadate
        for (int i = 0; i < directions.length; i++) {
            float rand = random.nextFloat();
            if(rand > 0.8f) clone.directions[i] = best.brain.directions[i].copy();
            else clone.directions[i] = directions[i].copy();
        }
        return clone;
    }

}
