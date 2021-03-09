import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
/**
 * Write a description of class Population here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Network extends Actor
{
    mazeRunner[] runners;
    float fitnessSum;
    int gen = 1;
    int lowest;
    int bestmazeRunner = 0;//the index of the best dot in the runners[]
    float bestFitness = 0.0f;
    int minStep = 1200;
    CollisionRayCast colBox;
    Thread col;
    MyWorld world = ((MyWorld)getWorld());

    Network(int size) {
        world = (MyWorld)MyWorld.world;
        runners = new mazeRunner[size];
        for (int i = 0; i< size; i++) {
            runners[i] = new mazeRunner();
        }
        makeThread();
        runners[0].isBest = true;   // Set first runner to best to correctly update frame, not ideal but much more optimazed than overlapping each maze for every runner, rather than only the best. 
    }

    private void makeThread(){
        colBox = new CollisionRayCast(runners);
        world.addObject(colBox ,0,0);
        col = new Thread(colBox,"Collision Raycasting thread - greenfoot"); 
        colBox.startThread(); // allow the thread to run by flagging as true
        col.start();          // tell the thread to start
    }

    //show all runners
    public void show() {
        for (int i = 1; i< runners.length; i++) {
            runners[i].show();
        }
        runners[0].show();  // show last 
    }

    // hide a runner if it is ontop of another
    public void checkOverlap(){
        for (int i = 0; i< runners.length; i++) {
            for (int j = 0; j< runners.length; j++) {
                if(runners[i] != runners[j] && runners[i].location.equals(runners[j].location) && runners[i].hide != true){
                    runners[i].hide = true;
                }else {
                    runners[i].hide = false;
                }
            }
        }
    }

    //update all runners as master function
    @Override
    public void act(){
        for (int i = 0; i< runners.length; i++) {
            if (runners[i].steps > minStep) {       //encouarge less steps, only works if a runner has reached the goal
                runners[i].dead = true;             //kill it
            } else {
                runners[i].update();
            }
        }
    }

    //calculate all the fitnesses
    public void calculateFitness() {
        for (int i = 0; i< runners.length; i++) {
            runners[i].calculateFitness();
        }
    }

    //returns whether all the runners are either dead or have reached the goal
    public synchronized  boolean allmazeRunnersDead() {
        for (int i = 0; i< runners.length; i++) {
            if (!runners[i].dead && !runners[i].reachedGoal) { 
                return false;
            }
        }
        return true;
    }

    //gets the next generation of runners
    public void naturalSelection() {
        mazeRunner[] newmazeRunners = new mazeRunner[runners.length];//next gen
        setBest();
        calculateFitnessSum();
        //get baby from best
        newmazeRunners[0] = runners[bestmazeRunner].Breed(false);
        newmazeRunners[0].isBest = true;
        for (int i = 1; i< newmazeRunners.length; i++) {
            //select parent based on fitness
            mazeRunner parent = selectParent();
            //get baby from them
            newmazeRunners[i] = parent.Breed(true);
        }
        getLowestMove();
        runners = newmazeRunners.clone();
        gen++;
    }

    //add all together from stats
    private void calculateFitnessSum() {
        fitnessSum = 0;
        for (int i = 0; i< runners.length; i++) {
            fitnessSum += runners[i].fitness;
        }
    }

    //chooses from the population to return random runner in population influenced by fitness)
    private mazeRunner selectParent() {
        Random random = new Random();
        float randOffset = random.nextFloat()*(fitnessSum);
        float fitSum = 0;
        for (int i = 0; i< runners.length; i++) {
            fitSum+= runners[i].fitness;
            if (fitSum > randOffset) {
                return runners[i];
            }

        }
        return null;
    }

    private void getLowestMove() {
        for (int i = 0; i< runners.length; i++) {
            if(lowest == 0 && runners[i].reachedGoal) lowest = runners[bestmazeRunner].steps;
            if(runners[i].steps < lowest && runners[i].reachedGoal) lowest = runners[i].steps;
        }
    }

    //call mutate method for each runner
    public void mutation() {
        for (int i = 1; i< runners.length; i++) {
            runners[i].mutate();
        }
    }

    //finds the dot with the highest fitness and sets it as the best dot
    private void setBest() {
        float max = 0;
        int maxIndex = 0;
        for (int i = 0; i< runners.length; i++) {
            if (runners[i].fitness > max) {
                max = runners[i].fitness;
                maxIndex = i;
            }
        }
        bestmazeRunner = maxIndex;
        bestFitness = runners[bestmazeRunner].fitness;
        //if this dot reached the goal then reset the minimum number of steps it takes to get to the goal
        if (runners[bestmazeRunner].reachedGoal) {
            minStep = runners[bestmazeRunner].steps;
        }
        //System.out.println(runners[bestmazeRunner].fitness);
    }
}   

