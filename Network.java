import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
////cgbsdfbrwbgees g
/**
 * Write a description of class Population here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Network extends Actor
{
    public mazeRunner[] genomes;
    public float fitnessSum;
    public int gen = 1;
    public int lowest;
    public int bestmazeRunner = 0;//the index of the best dot in the genomes[]
    public float bestFitness = 0.0f;
    private int minStep = 1200;
    public CollisionRayCast colBox;
    public Thread col;
    MyWorld world = ((MyWorld)getWorld());

    Network(int size) {
        world = (MyWorld)MyWorld.world;
        genomes = new mazeRunner[size];
        for (int i = 0; i< size; i++) {
            genomes[i] = new mazeRunner();
        }
        makeThread();
        genomes[0].isBest = true;   // Set first runner to best to correctly update frame, not ideal but much more optimazed than overlapping each maze for every runner, rather than only the best. 
    }

    private void makeThread(){
        colBox = new CollisionRayCast(genomes);
        world.addObject(colBox ,0,0);
        col = new Thread(colBox,"Collision Raycasting thread - greenfoot"); 
        colBox.startThread(); // allow the thread to run by flagging as true
        col.start();          // tell the thread to start
    }

    //show all genomes
    public void show() {
        for (int i = 1; i< genomes.length; i++) {
            genomes[i].show();
        }
        genomes[0].show();  // show last 
    }

    // hide a runner if it is ontop of another
    public void checkOverlap(){
        for (int i = 0; i< genomes.length; i++) {
            for (int j = 0; j< genomes.length; j++) {
                if(genomes[i] != genomes[j] && genomes[i].location.equals(genomes[j].location) && genomes[i].hide != true){
                    genomes[i].hide = true;
                }else {
                    genomes[i].hide = false;
                }
            }
        }
    }

    //update all genomes as master function
    @Override
    public void act(){
        for (int i = 0; i< genomes.length; i++) {
            if (genomes[i].steps > minStep) {       //encouarge less steps, only works if a runner has reached the goal
                genomes[i].dead = true;             //kill it
            } else {
                genomes[i].update();
            }
        }
    }

    //calculate all the fitnesses
    public void calculateAllFitnesses() {
        for (int i = 0; i< genomes.length; i++) {
            genomes[i].calculateFitness();
        }
    }

    //returns whether all the genomes are either dead or have reached the goal
    public synchronized  boolean allmazeRunnersDead() {
        for (int i = 0; i< genomes.length; i++) {
            if (!genomes[i].dead && !genomes[i].reachedGoal) { 
                return false;
            }
        }
        return true;
    }

    //gets the next generation of genomes
    public void naturalSelection() {
        mazeRunner[] newmazeRunners = new mazeRunner[genomes.length];//next gen
        setBest();
        calculateFitnessSum();
        //get baby from best
        newmazeRunners[0] = genomes[bestmazeRunner].Breed(false);
        newmazeRunners[0].isBest = true;
        for (int i = 1; i< newmazeRunners.length; i++) {
            //select parent based on fitness
            mazeRunner parent = selectParent();
            //get baby from them
            newmazeRunners[i] = parent.Breed(true);
        }
        getLowestMove();
        genomes = newmazeRunners.clone();
        gen++;
    }

    //add all together from stats
    private void calculateFitnessSum() {
        fitnessSum = 0;
        for (int i = 0; i< genomes.length; i++) {
            fitnessSum += genomes[i].fitness;
        }
    }

    //chooses from the population to return random runner in population influenced by fitness)
    private mazeRunner selectParent() {
        Random random = new Random();
        float randOffset = random.nextFloat()*(fitnessSum);
        float fitSum = 0;
        for (int i = 0; i< genomes.length; i++) {
            fitSum+= genomes[i].fitness;
            if (fitSum > randOffset) {
                return genomes[i];
            }

        }
        return null;
    }

    private void getLowestMove() {
        for (int i = 0; i< genomes.length; i++) {
            if(lowest == 0 && genomes[i].reachedGoal) lowest = genomes[bestmazeRunner].steps;
            if(genomes[i].steps < lowest && genomes[i].reachedGoal) lowest = genomes[i].steps;
        }
    }

    //call mutate method for each runner
    public void mutation() {
        for (int i = 1; i< genomes.length; i++) {
            genomes[i].mutate();
        }
    }

    private int getMaxIndexOfMaxFitness(){
        float max = 0;
        int maxIndex = 0;
        for (int i = 0; i< genomes.length; i++) {
            if (genomes[i].fitness > max) {
                max = genomes[i].fitness;
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    //finds the dot with the highest fitness and sets it as the best dot
    private void setBest() {
        int maxIndex = getMaxIndexOfMaxFitness();
        bestmazeRunner = maxIndex;
        bestFitness = genomes[bestmazeRunner].fitness;
        //if this dot reached the goal then reset the minimum number of steps it takes to get to the goal
        if (genomes[bestmazeRunner].reachedGoal) {
            minStep = genomes[bestmazeRunner].steps;
        }
        //System.out.println(genomes[bestmazeRunner].fitness);
    }
}   

