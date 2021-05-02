import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * Write a description of class Population here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Network extends Actor {
    public List<mazeRunner> genomes = new ArrayList<mazeRunner>();
    public float fitnessSum;
    public int gen = 1;
    public int lowest;
    public int bestmazeRunner = 0; //the index of the best dot in the genomes[]
    public float bestFitness = 0.0f;
    private int minStep = 1200;
    public CollisionRayCast colBox;
    public Thread col;
    MyWorld world = ((MyWorld) getWorld());

    Network(int size) {
        world = (MyWorld) MyWorld.world;
        for (int i = 0; i < size; i++) {
            genomes.add(new mazeRunner());
        }
        makeThread();
        genomes.get(0).isBest = true; // Set first runner to best to correctly update frame, not ideal but much more optimazed than overlapping each maze for every runner, rather than only the best. 
    }

    private void makeThread() {
        colBox = new CollisionRayCast(genomes);
        world.addObject(colBox, 0, 0);
        col = new Thread(colBox, "Collision Raycasting thread - greenfoot");
        colBox.startThread(); // allow the thread to run by flagging as true
        col.start(); // tell the thread to start
    }

    //show all genomes
    public void show() { 
        genomes.forEach(g -> g.show()); // lamba expression is basically like a for loop that we can call methods from or acess their values. algorithms cannot be used inside
    }

    // hide a runner if it is ontop of another
    public void checkOverlap() {
        genomes.forEach(current ->        
                genomes.forEach(others -> 
                        current.hidden = (current != others && current.location.equals(others.location) && !current.hidden && !current.isBest)       // check if any genomes are on top of each other by checking if a genome is not current hidden and its location doesnt equal any other genomes location  
                )
        );
    }

    //update all genomes as master function
    @Override
    public void act() {
        for (int i = 0; i < genomes.size(); i++) {
            if (genomes.get(i).steps > minStep) { //encouarge less steps, only works if a runner has reached the goal
                genomes.get(i).dead = true; //kill it
            } else {
                genomes.get(i).update(); // otherwise call its master function
            }
        }
    }

    //calculate all the fitnesses
    public void calculateAllFitnesses() {
         genomes.forEach(g -> g.calculateFitness()); // run the calcualteFitness function for all genomes
    }

    //returns whether all the genomes are either dead or have reached the goal
    public synchronized boolean allmazeRunnersDead() {
        for (int i = 0; i < genomes.size(); i++) {
            if (!genomes.get(i).dead && !genomes.get(i).reachedGoal) {
                return false;
            }
        }
        return true;
    }

    //gets the next generation of genomes
    public void naturalSelection() {
        mazeRunner[] newmazeRunners = new mazeRunner[genomes.size()]; //next gen
        setBest();
        calculateFitnessSum();
        //get baby from best
        newmazeRunners[0] = genomes.get(bestmazeRunner).Breed(false);
        newmazeRunners[0].isBest = true;
        for (int i = 1; i < newmazeRunners.length; i++) {
            //select parent based on fitness
            mazeRunner parent = selectParent();
            //get baby from them
            newmazeRunners[i] = parent.Breed(true);
        }
        getLowestMove();
        genomes = Arrays.asList(newmazeRunners.clone());
        gen++;
    }

    //add all together from stats
    private void calculateFitnessSum() {
        fitnessSum = 0;
        for (int i = 0; i < genomes.size(); i++) {
            fitnessSum += genomes.get(i).fitness;
        }
    }

    //chooses from the population to return random runner in population influenced by fitness)
    private mazeRunner selectParent() {
        Random random = new Random();
        float randOffset = random.nextFloat() * (fitnessSum);
        float fitSum = 0;
        for (int i = 0; i < genomes.size(); i++) {
            fitSum += genomes.get(i).fitness;
            if (fitSum > randOffset) {
                return genomes.get(i);
            }
        }
        return null;
    }

    private void getLowestMove() {
        for (int i = 0; i < genomes.size(); i++) {
            if (lowest == 0 && genomes.get(i).reachedGoal)
                lowest = genomes.get(bestmazeRunner).steps;
            if (genomes.get(i).steps < lowest && genomes.get(i).reachedGoal)
                lowest = genomes.get(i).steps;
        }
    }

    //call mutate method for each runner
    public void mutation() {
        // we dont use the labmda expression here because the greenfoot thread also needs to acess other genomes variables to calculate mutation offsets, doing so will mess up what genome is seen as the best 
        for (int i = 1; i < genomes.size(); i++) {
            genomes.get(i).mutate();
        }

    }

    private int getMaxIndexOfMaxFitness() {
        float max = 0;
        int maxIndex = 0;
        for (int i = 0; i < genomes.size(); i++) {
            if (genomes.get(i).fitness > max) {
                max = genomes.get(i).fitness;
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    //finds the dot with the highest fitness and sets it as the best dot
    private void setBest() {
        int maxIndex = getMaxIndexOfMaxFitness();
        bestmazeRunner = maxIndex;
        bestFitness = genomes.get(bestmazeRunner).fitness;
        //if this dot reached the goal then reset the minimum number of steps it takes to get to the goal
        if (genomes.get(bestmazeRunner).reachedGoal) {
            minStep = genomes.get(bestmazeRunner).steps;
        }
        //System.out.println(genomes[bestmazeRunner].fitness);
    }
}