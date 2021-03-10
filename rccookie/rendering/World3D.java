package rccookie.rendering;

import java.util.ArrayList;
import java.util.List;

import rccookie.geometry.Transform3D;
import rccookie.game.util.Time;

public abstract class World3D {
    

    private final List<GameObject> objects = new ArrayList<>();
    public final Time time = new Time();

    Transform3D camera;

    public World3D() {
        camera = new Transform3D();
    }


    
    
    public List<GameObject> getObjects() {
        return objects;
    }


    public void addObject(GameObject object) {
        objects.add(object);
    }




    /**
     * Updates everything in this world.
     * <p>Called only automatically when at least one camera of this world is in the active world!
     */
    public final void act() {
        time.act();
        executeEarlyUpdate();
        executeUpdate();
        executeLateUpdate();
    }


    private void executeEarlyUpdate() {
        for(GameObject o : objects) o.earlyUpdate(time.deltaTime());
        earlyUpdate(time.deltaTime());
    }
    private void executeUpdate() {
        for(GameObject o : objects) o.update(time.deltaTime());
        update(time.deltaTime());
    }
    private void executeLateUpdate() {
        for(GameObject o : objects) o.lateUpdate(time.deltaTime());
        lateUpdate(time.deltaTime());
    }



    public void earlyUpdate(double deltaTime) {}
    public void update(double deltaTime) {}
    public void lateUpdate(double deltaTime) {}
}
