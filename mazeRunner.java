import rccookie.game.AdvancedActor;
import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
/**
 * RULES:
 *  - If a runner travels through a wall in the maze, they arepunihsed an killed.
 *  - If a runner travels of the reccomened track and dies, they are punished.
 *  - If a runner dies on the reccomened track they are rewarded.
 *  - If a runner dies on the reccomened track, closer to the goal, they are made the best.
 *  - After a generation, the runners will take the moveset from the current best runner and mutate its move in a set randomly.
 *  - Moves have a higher chance of mutation the futher they are in the set.
 *  - If a dot has reached the goal, they will no longer be punished.
 *  - If many dots reach the goal, their rewards are then based on how many moves it took them, where less are encouraged.
 *  - The best pathway through the maze is determined via a heuristic based algorithm,where cells that are closer to the goal are favoured in a path. 
 *  - The runners do not need to follow the reccomened path as long as they reach the goal and dont die. It is only thier to guide them.
 *  - The best runner is enocuraged to stick to the corners of the current cell, to allow other runners to progress pass it with a larger opening.
 */
public class MazeRunner extends AdvancedActor {
    public Vector location;
    private int collisionOffset;
    private Vector velocity;
    private Vector force;
    private Vector vector;
    private int width, height;
    public boolean hidden = false;
    private GreenfootImage image;
    public boolean dead = false;
    public boolean reachedGoal = false;
    public boolean isBest = false; //true if this MazeRunner is the best MazeRunner from the previous generation
    public float fitness = 1f;
    public DNA brain;
    MyWorld world; // = ((MyWorld)getWorld());
    private int sizeX, sizeY;
    public int steps;
    private Random random = new Random();

    public MazeRunner() {
        world = (MyWorld) MyWorld.world;
        vector = new Vector();
        brain = new DNA(world.bestPath.size() * 100);
        initDimensions();
        initVectors();
    }

    private void initDimensions() {
        height = world.getHeight();
        width = world.getWidth();
        sizeX = world.size / 6; // scale the size based on the size of the world
        sizeY = sizeX;
        collisionOffset = sizeX + 6;
    }

    private void initVectors() {
        vector = new Vector();
        location = new Vector(15, world.size);
        velocity = new Vector(0, 0);
        force = new Vector(0, 0);
    }

    // Use for threads to get outside object only
    @Override
    public int getX() {
        return (int) location.x(); // used for raycast x position
    }

    @Override
    public int getY() {
        return (int) location.y(); // used for raycast y position
    }

    @Override
    public int getRotation() {
        return (int) vector.getRotation(location.x(), location.y()); // used for raycast angle
    }

    //draws the MazeRunner on the screen
    public void show() {
        //if this MazeRunner is the best MazeRunner from the previous generation then draw it as a big green MazeRunner
        if (isBest && !hidden) {
            world.getBackground().setColor(Color.BLUE);
            world.getBackground().drawRect((int) location.x(), (int) location.y(), sizeX, sizeY);

        } else if (!dead && !hidden && !world.showingBest) { //all other MazeRunners are just smaller black MazeRunners
            world.getBackground().setColor(Color.RED);
            world.getBackground().drawRect((int) location.x(), (int) location.y(), sizeX, sizeY);
        }

    }

    public void mutate() { // change some of the direction vector to new ones randomly
        for (int i = 0; i < brain.directions.length; i++) {
            float rand = random.nextFloat();
            if (i > (int) brain.directions.length / 1.38) { // bais favouring later directions to mutate
                rand -= 0.035 + i / 20000;
                if (i > (int) brain.directions.length / 1.1) {
                    rand -= 0.015;
                }
            }
            // increase chances based on position to encourage different movement towards the end
            if (rand < world.mutationRate) {
                //set this direction as a random direction 
                float randomAngle = random.nextFloat() * (float)(2 * Math.PI);
                brain.directions[i] = vector.fromAngle(randomAngle);
            }
        }
    }
    //moves the Maze Runner forceording to the brains directions
    public void search() {
        if (brain.directions.length > brain.step) { //if there are still directions left then set the forceeleration as the next PVector in the direcitons array
            force = brain.directions[brain.step];
            brain.step++;
        } else { //if at the end of the directions array then the MazeRunner is dead
            dead = true;
        }
        //apply the forceeleration and move the MazeRunner
        if (velocity.x() < 2) velocity.add(force); 
        location.add(velocity);
    }

    //calls the move function and check for collisions and stuff if its like reached in the walll
    public void update() {
        updateBackground();
        checkDeath();
        steps = brain.step;
    }

    private void updateBackground() {
        if (isBest) { // only clear the background from 1 maze runner, if we do every one its too slow and will not render the others 
            world.getBackground().setColor(Color.WHITE);
            world.getBackground().fill();
            world.drawCells();
        }
    }

    public void checkDeath() {
        if (!dead && !reachedGoal) {
            search();
            try {
                //float rotation = getRotation();   // look infront of poistion the runner is facing, i pixels ahead and check what color it is
                float rotation = (float) location.direction;
                for (int i = 0; i <= collisionOffset; i++) {
                    Color onTop = world.getBackground().getColorAt((int)(location.x() + i * (Math.cos(Math.toRadians(rotation)))), (int)(location.y() + i * (Math.sin(Math.toRadians(rotation))))); // look forward a few pixels of the direction its facing
                    //world.getBackground().drawLine((int)location.x(),(int)location.y(),(int)(location.x() + i * (Math.cos(Math.toRadians(rotation)))), (int)(location.y() + i * (Math.sin(Math.toRadians(rotation)))));
                    if ((Color.BLACK).equals(onTop)) dead = true; // can be dead and still hit target in the case of corners
                    if ((Color.GREEN).equals(onTop) && !dead) reachedGoal = true;
                }
            } catch (Exception e) {
                dead = true; // locationition out of bounds, shouldnt get here 
            }
        }
    }

    //calculates the fitness based on its position on the reccomened path , consider finding a quicker path if a current path has been found
    public void calculateFitness() {
        if (reachedGoal) { //if the MazeRunner reached the goal then the fitness is based on the amount of steps it took to get there
            fitness = 999999999999999999.0f / (float)(brain.step * brain.step);
        } else { //if the MazeRunner didn't reach the goal then the fitness is based on how close it is to the goal
            //float distanceToGoal = (float)Math.hypot(location.x()- world.gx, location.y()- world.gy);
            //fitness = 1.0f/(distanceToGoal * distanceToGoal)
            for (int i = 0; i < world.bestPath.size(); i++) {
                int gx = world.bestPath.get(i).x;
                int gy = world.bestPath.get(i).y;
                int thisnumber = world.bestPath.get(i).getNumber();
                if ((gx + world.size >= location.x() && location.x() >= gx) && (gy + world.size >= location.y() && location.y() >= gy)) { // aabb hitbox
                    float newFitness = thisnumber * 100 + 1;
                    //System.out.println(world.bestPath.get(i).number);
                    // get distance to the middle of the next best possible location do some cool math
                    float distanceNextPoint = 0.0f;
                    int index = (gx / world.size) + (gy / world.size) * world.cols; // convert the 2 dimensional position to its base value so it can be referended in an array
                    Cell nextCell = world.grid.get(index + 1); // pretty much just some math to get the cell around its current position, this is much mreo efficnet then having another for-loop nested that searches through every possible cell :)
                    int pathX = nextCell.x;
                    int pathY = nextCell.y;
                    distanceNextPoint = (1.0f / (float)(Math.hypot(location.x() - (pathX + (world.size / 2)), location.y() - (pathY + (world.size / 2))))) * 100; // inverse so lower is better
                    fitness = newFitness + (distanceNextPoint * distanceNextPoint); // we sqaure the distance to the next goal to encourge it being closer
                    return;
                }
            }
        }
    }

    //clone its moveset  
    public MazeRunner Breed(boolean crossOver) {
        MazeRunner baby = new MazeRunner();
        try {
            if (!crossOver) baby.brain = brain.transform(); //babies have the same brain as their parents
            else baby.brain = brain.crossOver();
        } catch (Exception e) {
            return null; // Pass on error, shouldn't get here.
        }
        return baby;
    }
}