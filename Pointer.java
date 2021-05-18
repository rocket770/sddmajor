import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Pionter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pointer extends Actor {
    private int mx, my;
    private MouseInfo mouse;
    private int hitboxOffset;
    private LevelEditor world;
    /**
     * Act - do whatever the Pionter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    Pointer() {
        GreenfootImage img = new GreenfootImage(5, 5);
        //img.fill();
        setImage(img);
    }

    protected void addedToWorld(World world) {
        //world = (LevelEditor)getWorld(); // for some reason this doesnt work when the world is a subclass, thanks greenfoot :/
        hitboxOffset = ((LevelEditor) getWorld()).size / 5;
    }

    public void act() {
        mouse = Greenfoot.getMouseInfo();
        followMouse();
        removeWall();
    }

    private void followMouse() {
        if (mouse != null) {
            try {
                mx = mouse.getX();
                my = mouse.getY();
                setLocation(mx, my);
            } catch (Exception e) {
                return;
            }
        }
    }

    private void removeWall() {
        world = (LevelEditor) getWorld();
        if (mouse != null && Greenfoot.mouseClicked(null) &&!world.settings.pause) {
            for (Cell cell: world.grid) {
                int x = cell.x; // current cell's values
                int y = cell.y;
                try {
                    int xleft = world.grid.get(world.grid.indexOf(cell) - 1).x; // get cell to the left's values so the offset works on both left and right of the wall
                    int yleft = world.grid.get(world.grid.indexOf(cell) - 1).y;
                    if ((mx >= x && mx <= x + world.size && my >= y && my <= y + world.size) || (mx >= xleft && mx <= xleft + world.size && my >= yleft && my <= yleft + world.size)) { // check if we are the the left or right of a vertical 
                        if (mx <= x + hitboxOffset && mx >= x - hitboxOffset) {
                            cell.Walls[3] = !cell.Walls[3];
                            world.grid.get(world.grid.indexOf(cell) - 1).Walls[1] = !world.grid.get(world.grid.indexOf(cell) - 1).Walls[1];
                        }
                    }
                    int xup = world.grid.get(world.grid.indexOf(cell) - world.cols).x; // get cell above's values so the offset works on both above and below the wall
                    int yup = world.grid.get(world.grid.indexOf(cell) - world.cols).y;
                    if ((mx >= x && mx <= x + world.size && my >= y && my <= y + world.size) || (mx >= xup && mx <= xup + world.size && my >= yup && my <= yup + world.size)) { // check if we are above or below a horizontal wall
                        if (my <= y + hitboxOffset && my >= y - hitboxOffset) {
                            cell.Walls[0] = !cell.Walls[0];
                            world.grid.get(world.grid.indexOf(cell) - world.cols).Walls[2] = !world.grid.get(world.grid.indexOf(cell) - world.cols).Walls[2];
                        }
                    }
                } catch (Exception e) {
                    // we dont care if we get here
                    // this just ignores errors when there isnt a cell above/bellow/left/right to the current cell
                }
            }
        }

    }
}